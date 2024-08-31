package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ValidarDadosCliente implements IStrategy {
    private final VerificarExistenciaCliente verificarExistenciaCliente;
    public ValidarDadosCliente() throws SQLException {
        this.verificarExistenciaCliente = new VerificarExistenciaCliente();
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        Map<String, String> errorMessages = new HashMap<>();
        Cliente cliente = (Cliente) entidade;

        String existenciaResponse = verificarExistenciaCliente.processar(cliente);

        if (cliente.getNomeCompleto() == null || cliente.getNomeCompleto().equals("")) {
            errorMessages.put("nomeCompleto", "Nome completo é obrigatório");
        }

        if (cliente.getEmail() == null || cliente.getEmail().equals("")) {
            errorMessages.put("email", "Email é obrigatório");
        }

        if (cliente.getCpf() == null || cliente.getCpf().equals("")) {
            errorMessages.put("cpf", "CPF é obrigatório");
        }

        if (cliente.getGenero() == null || cliente.getGenero().equals("")) {
            errorMessages.put("genero", "Gênero é obrigatório");
        }

        if (cliente.getDataNascimento() == null) {
            errorMessages.put("dataNascimento", "Data de nascimento é obrigatória");
        }

        if (cliente.getTelefoneDDD() == null || cliente.getTelefoneDDD().equals("")) {
            errorMessages.put("telefoneDDD", "DDD é obrigatório");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().equals("")) {
            errorMessages.put("telefone", "Telefone é obrigatório");
        }

        if (cliente.getTipoTelefone() == null || cliente.getTipoTelefone().equals("")) {
            errorMessages.put("tipoTelefone", "Tipo de telefone é obrigatório");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMessages);

    }
}
