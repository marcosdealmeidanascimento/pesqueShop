package com.les.pesqueshop;

import com.les.pesqueshop.database.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class PesqueshopApplication {

    public static void main(String[] args) throws SQLException {
        try {
            DatabaseConnection.getConnection();
        } catch (Exception err) {
            throw new RuntimeException(err.getMessage());
        }
        SpringApplication.run(PesqueshopApplication.class, args);
    }
}
