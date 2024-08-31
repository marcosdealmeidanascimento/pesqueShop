package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.pesqueshop.dao.ClienteDAO;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ValidarSenha implements IStrategy {
    private final ClienteDAO clienteDAO;

    public ValidarSenha() throws SQLException {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {

        Map<String, String> errorMessages = new HashMap<>();
        Cliente cliente = (Cliente) entidade;

        if (cliente.getSenha().length() < 8) {
            errorMessages.put("tamanho", "Senha deve ter no mínimo 8 caracteres");
        }

        if (!cliente.getSenha().matches(".*[A-Z].*")) {
            errorMessages.put("maiuscula", "Senha deve conter ao menos uma letra maiúscula");
        }

        if (!cliente.getSenha().matches(".*[a-z].*")) {
            errorMessages.put("minuscula", "Senha deve conter ao menos uma letra minúscula");
        }

        if (!cliente.getSenha().matches(".*[0-9].*")) {
            errorMessages.put("numero", "Senha deve conter ao menos um número");
        }

        if (!cliente.getSenha().matches(".*[!@#$%^&*].*")) {
            errorMessages.put("especial", "Senha deve conter ao menos um caractere especial");
        }

        if (cliente.getSenha().matches(".*[ ]+.*")) {
            errorMessages.put("espacos", "Senha não deve conter espaços em branco");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMessages);
    }
}
