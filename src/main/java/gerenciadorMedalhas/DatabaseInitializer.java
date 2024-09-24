package gerenciadorMedalhas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Roda o script de criar a tabela de países
            String createTableSql = new String(Files.readAllBytes(Paths.get("src/main/resources/sql/create_paises_table.sql")));
            stmt.execute(createTableSql);

            // Roda a o script de inserção dos dados dos países
            String insertDataSql = new String(Files.readAllBytes(Paths.get("src/main/resources/sql/insert_paises.sql")));
            stmt.execute(insertDataSql);

            System.out.println("Database initialized successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
