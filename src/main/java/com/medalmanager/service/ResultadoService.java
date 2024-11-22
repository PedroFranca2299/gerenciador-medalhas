package com.medalmanager.service;

import com.medalmanager.model.dto.ResultadoDTO;
import com.medalmanager.model.dto.ParticipacaoResultadoDTO;
import com.medalmanager.model.entity.Resultado;
import com.medalmanager.model.entity.ParticipacaoResultado;
import com.medalmanager.model.entity.Country;
import com.medalmanager.repository.ResultadoRepository;
import com.medalmanager.repository.CountryRepository;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class ResultadoService {
    private final ResultadoRepository repository;
    private final ModalityService modalityService;
    private final CountryService countryService;
    private final EtapaService etapaService;
    private final CountryRepository countryRepository;

    public ResultadoService(ResultadoRepository repository,
                            ModalityService modalityService,
                            CountryService countryService,
                            EtapaService etapaService,
                            CountryRepository countryRepository) {
        this.repository = repository;
        this.modalityService = modalityService;
        this.countryService = countryService;
        this.etapaService = etapaService;
        this.countryRepository = countryRepository;
    }

    public void saveResultado(ResultadoDTO resultadoDTO) {
        // Validação de participantes
        if (resultadoDTO.getParticipacoes().isEmpty()) {
            throw new IllegalArgumentException("Deve haver pelo menos um participante");
        }

        // Verifica se há posições duplicadas no pódio
        Set<Integer> posicoes = new HashSet<>();
        for (ParticipacaoResultadoDTO part : resultadoDTO.getParticipacoes()) {
            if (part.getPosicao() != null) {
                if (!posicoes.add(part.getPosicao())) {
                    throw new IllegalArgumentException("Posições no pódio não podem ser duplicadas");
                }
            }
        }

        // Verifica se todos os países estão marcados como participantes
        List<String> paisesNaoParticipantes = resultadoDTO.getParticipacoes().stream()
                .map(ParticipacaoResultadoDTO::getPaisNome)
                .filter(paisNome -> {
                    Country country = countryRepository.findById(countryService.findIdByName(paisNome))
                            .orElseThrow(() -> new IllegalArgumentException("País não encontrado: " + paisNome));
                    return !country.isParticipating();
                })
                .collect(Collectors.toList());

        if (!paisesNaoParticipantes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Os seguintes países não estão marcados como participantes: " +
                            String.join(", ", paisesNaoParticipantes)
            );
        }

        // Converte DTO para entity e salva
        Resultado resultado = convertToEntity(resultadoDTO);
        repository.save(resultado);
    }

    private Resultado convertToEntity(ResultadoDTO dto) {
        Resultado resultado = new Resultado();

        // Busca IDs baseado nos nomes
        resultado.setModalidadeId(modalityService.findIdByName(dto.getModalidadeNome()));
        resultado.setEtapaId(etapaService.findIdByName(dto.getEtapaNome()));
        resultado.setDataResultado(dto.getDataResultado());

        // Converte participações
        for (ParticipacaoResultadoDTO partDTO : dto.getParticipacoes()) {
            ParticipacaoResultado part = new ParticipacaoResultado();
            part.setPaisId(countryService.findIdByName(partDTO.getPaisNome()));
            part.setPosicao(partDTO.getPosicao());
            resultado.addParticipacao(part);
        }

        return resultado;
    }

    public List<ResultadoDTO> getAllResultados() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ResultadoDTO convertToDTO(Resultado resultado) {
        ResultadoDTO dto = new ResultadoDTO();
        dto.setId(resultado.getId());
        dto.setModalidadeNome(modalityService.findNameById(resultado.getModalidadeId()));
        dto.setEtapaNome(etapaService.findNameById(resultado.getEtapaId()));
        dto.setDataResultado(resultado.getDataResultado());

        List<ParticipacaoResultadoDTO> participacoes = resultado.getParticipacoes()
                .stream()
                .map(part -> new ParticipacaoResultadoDTO(
                        part.getId(),
                        countryService.findNameById(part.getPaisId()),
                        part.getPosicao()
                ))
                .collect(Collectors.toList());

        dto.setParticipacoes(participacoes);
        return dto;
    }
}