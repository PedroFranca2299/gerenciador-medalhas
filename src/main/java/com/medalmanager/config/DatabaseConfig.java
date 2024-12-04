package com.medalmanager.config;

import java.io.File;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String APP_FOLDER = ".medalmanager";
    private static final String DB_FILE = "medalmanager";

    public static void initialize() {
        File appDir = new File(USER_HOME, APP_FOLDER);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        String dbPath = new File(appDir, DB_FILE).getAbsolutePath();

        properties.setProperty("db.url", "jdbc:h2:" + dbPath);
        properties.setProperty("db.username", "sa");
        properties.setProperty("db.password", "");
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}