package gerenciadorMedalhas.model;

import gerenciadorMedalhas.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModalidadeService {
    public List<Modalidade> fetchModalidades() {
        List<Modalidade> modalidades = new ArrayList<>();
        String query = "SELECT id, nome FROM modalidades"; // Inclui o id na consulta

        // Abre conexão com o banco para buscar as modalidades
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id"); // Obtém o id
                String nome = rs.getString("nome");
                modalidades.add(new Modalidade(id, nome)); // Cria o objeto com id e nome
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modalidades;
    }
}
