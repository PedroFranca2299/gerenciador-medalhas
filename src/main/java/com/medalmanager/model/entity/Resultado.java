package com.medalmanager.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Resultado {
    private Long id;
    private Long modalidadeId;
    private Long etapaId;
    private LocalDateTime dataResultado;
    private List<ParticipacaoResultado> participacoes;

    public Resultado() {
        this.participacoes = new ArrayList<>();
    }

    public Resultado(Long id, Long modalidadeId, Long etapaId, LocalDateTime dataResultado) {
        this.id = id;
        this.modalidadeId = modalidadeId;
        this.etapaId = etapaId;
        this.dataResultado = dataResultado;
        this.participacoes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModalidadeId() {
        return modalidadeId;
    }

    public void setModalidadeId(Long modalidadeId) {
        this.modalidadeId = modalidadeId;
    }

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public LocalDateTime getDataResultado() {
        return dataResultado;
    }

    public void setDataResultado(LocalDateTime dataResultado) {
        this.dataResultado = dataResultado;
    }

    public List<ParticipacaoResultado> getParticipacoes() {
        return participacoes;
    }

    public void setParticipacoes(List<ParticipacaoResultado> participacoes) {
        this.participacoes = participacoes;
    }

    public void addParticipacao(ParticipacaoResultado participacao) {
        if (this.participacoes == null) {
            this.participacoes = new ArrayList<>();
        }
        this.participacoes.add(participacao);
    }
}