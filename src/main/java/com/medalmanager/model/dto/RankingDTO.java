package com.medalmanager.model.dto;

public class RankingDTO {
    private String paisNome;
    private int ouro;
    private int prata;
    private int bronze;
    private int total;

    public RankingDTO(String paisNome, int ouro, int prata, int bronze) {
        this.paisNome = paisNome;
        this.ouro = ouro;
        this.prata = prata;
        this.bronze = bronze;
        this.total = ouro + prata + bronze;
    }

    public String getPaisNome() { return paisNome; }
    public void setPaisNome(String paisNome) { this.paisNome = paisNome; }

    public int getOuro() { return ouro; }
    public void setOuro(int ouro) {
        this.ouro = ouro;
        updateTotal();
    }

    public int getPrata() { return prata; }
    public void setPrata(int prata) {
        this.prata = prata;
        updateTotal();
    }

    public int getBronze() { return bronze; }
    public void setBronze(int bronze) {
        this.bronze = bronze;
        updateTotal();
    }

    public int getTotal() { return total; }

    private void updateTotal() {
        this.total = this.ouro + this.prata + this.bronze;
    }
}