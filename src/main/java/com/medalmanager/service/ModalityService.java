package com.medalmanager.service;

import com.medalmanager.model.dto.ModalityDTO;
import com.medalmanager.model.entity.Modality;
import com.medalmanager.repository.ModalityRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ModalityService {
    private final ModalityRepository repository;

    public ModalityService(ModalityRepository repository) {
        this.repository = repository;
    }

    public List<ModalityDTO> getAllModalities() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updateActiveModalities(List<Long> selectedIds) {
        repository.resetAllActiveStatus();
        selectedIds.forEach(id -> repository.updateActiveStatus(id, true));
    }

    private ModalityDTO convertToDTO(Modality modality) {
        return new ModalityDTO(
                modality.getId(),
                modality.getName(),
                modality.isActive()
        );
    }
}