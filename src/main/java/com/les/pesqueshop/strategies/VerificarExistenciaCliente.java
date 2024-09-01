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

        Cliente clienteEmail = (Cliente) clienteDAO.getClienteByEmail(cliente);
        Cliente clienteCpf = (Cliente) clienteDAO.getClienteByCpf(cliente);

        int idClienteEmail = clienteEmail != null ? clienteEmail.getId() : 0;
        int idClienteCpf = clienteCpf != null ? clienteCpf.getId() : 0;

        Map<String, String> response = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if (clienteEmail == null && clienteCpf == null) {
            return objectMapper.writeValueAsString(response);
        }

        if (clienteEmail != null && clienteEmail.getId() != cliente.getId()) {
            response.put("email", "E-mail já cadastrado");
        }

        if (clienteCpf != null && clienteCpf.getId() != cliente.getId()) {
            response.put("cpf", "CPF já cadastrado");
        }

        if (cliente.getId() != idClienteEmail && cliente.getId() != idClienteCpf) {
            response.put("id", String.valueOf(idClienteEmail));
        }

        if (cliente.getId() == idClienteEmail && cliente.getId() != idClienteCpf) {
            response.put("id", String.valueOf(idClienteCpf));
        }

        if (cliente.getId() != idClienteEmail && cliente.getId() == idClienteCpf) {
            response.put("id", String.valueOf(idClienteEmail));
        }

        return objectMapper.writeValueAsString(response);

    }
}