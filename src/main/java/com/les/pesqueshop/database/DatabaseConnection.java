package com.les.pesqueshop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_POSTGRESQL = "jdbc:postgresql://localhost:5432/meu_banco";
    private static final String USER = "meu_usuario";
    private static final String PASSWORD = "minha_senha";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            return DriverManager.getConnection(URL_POSTGRESQL, USER, PASSWORD);
        } catch (SQLException err) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", err);
        }
    }
}
