package com.medalmanager.service;

import com.medalmanager.model.dto.EtapaDTO;
import com.medalmanager.model.entity.Etapa;
import com.medalmanager.repository.EtapaRepository;
import java.util.List;
import java.util.stream.Collectors;

public class EtapaService {
    private final EtapaRepository repository;

    public EtapaService(EtapaRepository repository) {
        this.repository = repository;
    }

    public List<EtapaDTO> getAllEtapas() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EtapaDTO convertToDTO(Etapa etapa) {
        return new EtapaDTO(
                etapa.getId(),
                etapa.getNome()
        );
    }
}