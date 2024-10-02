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
        String query = "SELECT nome FROM modalidades";

        //abre conexão com a banco para buscar o pais
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                modalidades.add(new Modalidade(nome)); // Apenas o nome
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modalidades;
    }
}
