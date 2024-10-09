package gerenciadorMedalhas.model;

import gerenciadorMedalhas.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaisService {
    public List<Pais> fetchPaises() {
        List<Pais> paises = new ArrayList<>();
        String query = "SELECT id, nome FROM paises"; // Inclui o id na consulta

        // Abre conexão com o banco
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id"); // Obtém o id
                String nome = rs.getString("nome");
                paises.add(new Pais(id, nome)); // Cria o objeto com id e nome
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paises;
    }
}
