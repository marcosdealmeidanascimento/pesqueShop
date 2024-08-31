package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.pesqueshop.dao.CartaoDAO;
import com.les.pesqueshop.dominio.Cartao;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ValidarCartao implements IStrategy {
    private final CartaoDAO cartaoDAO;

    public ValidarCartao() throws SQLException {
        this.cartaoDAO = new CartaoDAO();
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        Map<String, String> errorMessages = new HashMap<>();
        Cartao cartao = (Cartao) entidade;

        if (cartao.getNumero() == null || cartao.getNumero().equals("")) {
            errorMessages.put("numero", "Número do cartão é obrigatório");
        }

        if (cartao.getNomeImpresso() == null || cartao.getNomeImpresso().equals("")) {
            errorMessages.put("nomeImpresso", "Nome impresso no cartão é obrigatório");
        }

        if (cartao.getCvv() == null || cartao.getCvv().equals("")) {
            errorMessages.put("cvv", "CVV é obrigatório");
        }

        if (cartao.getValidade() == null) {
            errorMessages.put("validade", "Validade é obrigatória");
        }

        if (cartao.getCliente() == null) {
            errorMessages.put("cliente", "Cliente é obrigatório");
        }

        if (cartao.getBandeira() == null || cartao.getBandeira().equals("")) {
            errorMessages.put("bandeira", "Bandeira é obrigatória");
        }

        if (cartao.getApelidoCartao() == null || cartao.getApelidoCartao().equals("")) {
            errorMessages.put("apelidoCartao", "Apelido do cartão é obrigatório");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMessages);
    }
}
