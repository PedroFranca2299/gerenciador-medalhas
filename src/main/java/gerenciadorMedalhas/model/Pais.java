package gerenciadorMedalhas.model;

public class Pais {
    private String nome;
    private int id; // Atributo id

    // Construtor
    public Pais(int id, String nome) {
        this.id = id; // Inicializa o id
        this.nome = nome;
    }

    // Retorna o nome dos países
    public String getNome() {
        return nome;
    }

    // Retorna o id
    public int getId() {
        return id;
    }
}
