package com.medalmanager.util;

import com.medalmanager.config.DatabaseConfig;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    private static final String[] REQUIRED_TABLES = {
            "paises", "modalidades", "etapas", "resultados", "participacoes_resultado"
    };

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (!isDatabaseInitialized(conn)) {
                System.out.println("First-time database initialization starting...");
                initializeDatabase(conn);
                System.out.println("Database initialization completed successfully");
            } else {
                System.out.println("Database is already initialized and ready to use");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage(), e);
        }
    }

    private static boolean isDatabaseInitialized(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            for (String tableName : REQUIRED_TABLES) {
                ResultSet tables = metaData.getTables(null, null, tableName.toUpperCase(),
                        new String[]{"TABLE"});
                if (!tables.next()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error checking database state: " + e.getMessage(), e);
        }
    }

    private static void initializeDatabase(Connection conn) {
        try {
            System.out.println("Creating database schema...");
            executeScript(conn, "/sql/schema.sql");

            System.out.println("Inserting initial data...");
            executeScript(conn, "/sql/initial_data.sql");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database: " + e.getMessage(), e);
        }
    }

    private static void executeScript(Connection conn, String resourcePath) throws Exception {
        try (InputStream is = DatabaseInitializer.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Could not find required SQL file: " + resourcePath);
            }

            String script = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            String[] statements = script.split(";");
            try (Statement stmt = conn.createStatement()) {
                for (String statement : statements) {
                    String trimmedStatement = statement.trim();
                    if (!trimmedStatement.isEmpty()) {
                        stmt.execute(trimmedStatement);
                    }
                }
            }
        }
    }
}