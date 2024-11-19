package com.medalmanager.service;

import com.medalmanager.model.dto.ParticipacaoResultadoDTO;
import com.medalmanager.model.dto.ResultadoDTO;
import com.medalmanager.model.entity.ParticipacaoResultado;
import com.medalmanager.model.entity.Resultado;
import com.medalmanager.repository.ResultadoRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ResultadoService {
    private final ResultadoRepository repository;
    private final ModalityService modalityService;
    private final CountryService countryService;
    private final EtapaService etapaService;

    public ResultadoService(ResultadoRepository repository,
                            ModalityService modalityService,
                            CountryService countryService,
                            EtapaService etapaService) {
        this.repository = repository;
        this.modalityService = modalityService;
        this.countryService = countryService;
        this.etapaService = etapaService;
    }

    public void saveResultado(ResultadoDTO resultadoDTO) {
        // Validações
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
}