package com.medalmanager.service;

import com.medalmanager.model.dto.RankingDTO;
import com.medalmanager.repository.ResultadoRepository;
import com.medalmanager.model.entity.Resultado;
import com.medalmanager.model.entity.ParticipacaoResultado;

import java.util.*;
import java.util.stream.Collectors;

public class RankingService {
    private final ResultadoRepository resultadoRepository;
    private final ModalityService modalityService;
    private final CountryService countryService;

    public static final String ALL_MODALITIES = "Todas as modalidades";

    public RankingService(
            ResultadoRepository resultadoRepository,
            ModalityService modalityService,
            CountryService countryService) {
        this.resultadoRepository = resultadoRepository;
        this.modalityService = modalityService;
        this.countryService = countryService;
    }

    public List<RankingDTO> getRankingByModality(String modalidadeName) {
        List<Resultado> resultados = resultadoRepository.findAll();

        if (!ALL_MODALITIES.equals(modalidadeName)) {
            Long modalidadeId = modalityService.findIdByName(modalidadeName);
            resultados = resultados.stream()
                    .filter(r -> r.getModalidadeId().equals(modalidadeId))
                    .collect(Collectors.toList());
        }

        Map<Long, int[]> medalhasPorPais = new HashMap<>();

        for (Resultado resultado : resultados) {
            for (ParticipacaoResultado participacao : resultado.getParticipacoes()) {
                if (participacao.getPosicao() != null && participacao.getPosicao() <= 3) {
                    medalhasPorPais.computeIfAbsent(participacao.getPaisId(), k -> new int[3]);
                    int[] medalhas = medalhasPorPais.get(participacao.getPaisId());
                    medalhas[participacao.getPosicao() - 1]++;
                }
            }
        }

        List<RankingDTO> ranking = new ArrayList<>();
        for (Map.Entry<Long, int[]> entry : medalhasPorPais.entrySet()) {
            String paisNome = countryService.findNameById(entry.getKey());
            int[] medalhas = entry.getValue();
            ranking.add(new RankingDTO(
                    paisNome,
                    medalhas[0],
                    medalhas[1],
                    medalhas[2]
            ));
        }

        return ranking;
    }
}