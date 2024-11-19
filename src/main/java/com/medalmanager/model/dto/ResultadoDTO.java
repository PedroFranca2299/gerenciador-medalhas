package com.medalmanager.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResultadoDTO {
    private Long id;
    private String modalidadeNome;
    private String etapaNome;
    private LocalDateTime dataResultado;
    private List<ParticipacaoResultadoDTO> participacoes;

    public ResultadoDTO() {
        this.participacoes = new ArrayList<>();
    }

    public ResultadoDTO(Long id, String modalidadeNome, String etapaNome,
                        LocalDateTime dataResultado) {
        this.id = id;
        this.modalidadeNome = modalidadeNome;
        this.etapaNome = etapaNome;
        this.dataResultado = dataResultado;
        this.participacoes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModalidadeNome() {
        return modalidadeNome;
    }

    public void setModalidadeNome(String modalidadeNome) {
        this.modalidadeNome = modalidadeNome;
    }

    public String getEtapaNome() {
        return etapaNome;
    }

    public void setEtapaNome(String etapaNome) {
        this.etapaNome = etapaNome;
    }

    public LocalDateTime getDataResultado() {
        return dataResultado;
    }

    public void setDataResultado(LocalDateTime dataResultado) {
        this.dataResultado = dataResultado;
    }

    public List<ParticipacaoResultadoDTO> getParticipacoes() {
        return participacoes;
    }

    public void setParticipacoes(List<ParticipacaoResultadoDTO> participacoes) {
        this.participacoes = participacoes;
    }

    public void addParticipacao(ParticipacaoResultadoDTO participacao) {
        if (this.participacoes == null) {
            this.participacoes = new ArrayList<>();
        }
        this.participacoes.add(participacao);
    }
}