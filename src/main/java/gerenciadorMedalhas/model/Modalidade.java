package gerenciadorMedalhas.model;

public class Modalidade {
    private String nome;
    private int id; // Atributo id

    public Modalidade(int id, String nome) {
        this.id = id; // Inicializa o id
        this.nome = nome;
    }

    // Retorna nome
    public String getNome() {
        return nome;
    }

    // Retorna o id
    public int getId() {
        return id;
    }
}
