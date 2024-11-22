package com.medalmanager.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    private static final String[] REQUIRED_TABLES = {
            "paises", "modalidades", "etapas", "resultados", "participacoes_resultado"
    };

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (!isDatabaseInitialized(conn)) {
                System.out.println("Database not initialized. Starting initialization...");
                initializeDatabase(conn);
            } else {
                System.out.println("Database already initialized.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static boolean isDatabaseInitialized(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            for (String tableName : REQUIRED_TABLES) {
                ResultSet tables = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
                if (!tables.next()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error checking database initialization", e);
        }
    }

    private static void initializeDatabase(Connection conn) {
        try {
            // Execute schema script
            executeScript(conn, "/sql/schema.sql");
            System.out.println("Schema created successfully");

            // Execute initial data script
            executeScript(conn, "/sql/initial_data.sql");
            System.out.println("Initial data inserted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }

    private static void executeScript(Connection conn, String resourcePath) throws Exception {
        try (InputStream is = DatabaseInitializer.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Could not find resource: " + resourcePath);
            }

            String script = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Split script into individual statements
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