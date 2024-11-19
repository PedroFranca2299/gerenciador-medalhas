package com.medalmanager.model.dto;

public class ParticipacaoResultadoDTO {
    private Long id;
    private String paisNome;
    private Integer posicao;

    public ParticipacaoResultadoDTO() {}

    public ParticipacaoResultadoDTO(Long id, String paisNome, Integer posicao) {
        this.id = id;
        this.paisNome = paisNome;
        this.posicao = posicao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaisNome() {
        return paisNome;
    }

    public void setPaisNome(String paisNome) {
        this.paisNome = paisNome;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}