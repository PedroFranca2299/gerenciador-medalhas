package com.medalmanager.service;

import com.medalmanager.model.dto.CountryDTO;
import com.medalmanager.model.entity.Country;
import com.medalmanager.repository.CountryRepository;
import java.util.List;
import java.util.stream.Collectors;

public class CountryService {
    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<CountryDTO> getAllCountries() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updateParticipatingCountries(List<Long> selectedIds) {
        if (selectedIds.size() != 16) {
            throw new IllegalArgumentException("Exatamente 16 países precisam ser selecionados");
        }

        repository.resetAllParticipationStatus();
        selectedIds.forEach(id -> repository.updateParticipationStatus(id, true));
    }

    private CountryDTO convertToDTO(Country country) {
        return new CountryDTO(
                country.getId(),
                country.getName(),
                country.isParticipating()
        );
    }
}