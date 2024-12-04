package com.medalmanager.model.entity;

public class ParticipacaoResultado {
    private Long id;
    private Long resultadoId;
    private Long paisId;
    private Integer posicao;

    public ParticipacaoResultado() {}

    public ParticipacaoResultado(Long id, Long resultadoId, Long paisId, Integer posicao) {
        this.id = id;
        this.resultadoId = resultadoId;
        this.paisId = paisId;
        this.posicao = posicao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResultadoId() {
        return resultadoId;
    }

    public void setResultadoId(Long resultadoId) {
        this.resultadoId = resultadoId;
    }

    public Long getPaisId() {
        return paisId;
    }

    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}