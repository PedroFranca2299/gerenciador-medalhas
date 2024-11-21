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

    public Long findIdByName(String nome) {
        return repository.findAll()
                .stream()
                .filter(etapa -> etapa.getNome().equals(nome))
                .map(Etapa::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Etapa não encontrada: " + nome));
    }

    public String findNameById(Long id) {
        return repository.findById(id)
                .map(Etapa::getNome)
                .orElseThrow(() -> new IllegalArgumentException("Etapa não encontrada com ID: " + id));
    }

    private EtapaDTO convertToDTO(Etapa etapa) {
        return new EtapaDTO(
                etapa.getId(),
                etapa.getNome()
        );
    }
}