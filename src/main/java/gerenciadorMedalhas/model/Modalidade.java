package gerenciadorMedalhas.model;

public class Modalidade {
    private String nome;
    //private String descricao;

    public Modalidade(String nome) {
        this.nome = nome;
        //this.descricao = descricao;
    }

    //retorna nome
    public String getNome() {

        return nome;

    }
}
