package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.pesqueshop.dao.ClienteDAO;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificarExistenciaCliente implements IStrategy {
    private final ClienteDAO clienteDAO;

    public VerificarExistenciaCliente() throws SQLException {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        Cliente cliente = (Cliente) entidade;
        List<EntidadeDominio> clientes = clienteDAO.filter(cliente, 1, 0);

        Map<String, String> response = new HashMap<>();

        if (clientes.size() > 0) {
            response.put("id", String.valueOf(clientes.get(0).getId()));
            response.put("nome", ((Cliente) clientes.get(0)).getNomeCompleto());
            response.put("email", ((Cliente) clientes.get(0)).getEmail());
            response.put("cpf", ((Cliente) clientes.get(0)).getCpf());
            response.put("genero", ((Cliente) clientes.get(0)).getGenero());
            response.put("dataNascimento", String.valueOf(((Cliente) clientes.get(0)).getDataNascimento()));
            response.put("telefone", ((Cliente) clientes.get(0)).getTelefone());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }
}