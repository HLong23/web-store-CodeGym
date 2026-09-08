package com.codegym.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file db.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Không đọc được file db.properties: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName(properties.getProperty("db.driver"));
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Không kết nối được database: " + e.getMessage(), e);
        }
    }
}
