package gerenciadorMedalhas.model;

import gerenciadorMedalhas.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaisService {
    public List<String> fetchPaises() {
        List<String> paises = new ArrayList<>();
        String query = "SELECT nome FROM paises";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                paises.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paises;
    }
}
