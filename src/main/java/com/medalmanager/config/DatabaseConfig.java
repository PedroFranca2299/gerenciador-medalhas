package com.medalmanager.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();
    private static Dotenv dotenv;

    public static void initialize() {
        System.out.println("Initializing database configuration...");

        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            String dbUrl = dotenv.get("DB_URL");
            String dbUsername = dotenv.get("DB_USERNAME");
            // Handle empty password specially
            String dbPassword = dotenv.get("DB_PASSWORD");
            dbPassword = (dbPassword != null) ? dbPassword : "";

            if (dbUrl != null && dbUsername != null) {
                System.out.println("Successfully loaded configuration from .env file");
                System.out.println("Database URL: " + dbUrl);
                System.out.println("Database Username: " + dbUsername);

                properties.setProperty("db.url", dbUrl);
                properties.setProperty("db.username", dbUsername);
                properties.setProperty("db.password", dbPassword);
                return;
            }
        } catch (Exception e) {
            System.out.println("Could not load .env file: " + e.getMessage());
        }

        // If .env failed, try loading from properties file
        System.out.println("Attempting to load configuration from config.properties...");
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Cannot find config.properties in classpath");
            }
            properties.load(input);
            System.out.println("Successfully loaded configuration from config.properties");
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }

        validateConfiguration();
    }

    private static void validateConfiguration() {
        if (!properties.containsKey("db.url")) {
            throw new RuntimeException("Missing database URL configuration");
        }
        if (!properties.containsKey("db.username")) {
            throw new RuntimeException("Missing database username configuration");
        }
        // Password can be empty
        if (!properties.containsKey("db.password")) {
            properties.setProperty("db.password", "");
        }
        System.out.println("Configuration validation successful");
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password", "");
    }
}