package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.pesqueshop.dao.EnderecoDAO;
import com.les.pesqueshop.dominio.Endereco;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ValidarEndereco implements IStrategy{
    private final EnderecoDAO enderecoDAO;

    public ValidarEndereco() throws SQLException {
        this.enderecoDAO = new EnderecoDAO();
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        Map<String, String> errorMessages = new HashMap<>();
        Endereco endereco = (Endereco) entidade;

        if (endereco.getCep() == null || endereco.getCep().equals("")) {
            errorMessages.put("cep", "CEP é obrigatório");
        }

        if (endereco.getLogradouro() == null || endereco.getLogradouro().equals("")) {
            errorMessages.put("logradouro", "Logradouro é obrigatório");
        }

        if (endereco.getNumero() == null || endereco.getNumero().equals("")) {
            errorMessages.put("numero", "Número é obrigatório");
        }

        if (endereco.getBairro() == null || endereco.getBairro().equals("")) {
            errorMessages.put("bairro", "Bairro é obrigatório");
        }

        if (endereco.getCidade() == null || endereco.getCidade().equals("")) {
            errorMessages.put("cidade", "Cidade é obrigatória");
        }

        if (endereco.getEstado() == null || endereco.getEstado().equals("")) {
            errorMessages.put("estado", "Estado é obrigatório");
        }

        if (endereco.getPais() == null || endereco.getPais().equals("")) {
            errorMessages.put("pais", "País é obrigatório");
        }

        if (endereco.getTipoResidencia() == null || endereco.getTipoResidencia().equals("")) {
            errorMessages.put("tipoResidencia", "Tipo de residência é obrigatório");
        }

        if (endereco.getTipoLogradouro() == null || endereco.getTipoLogradouro().equals("")) {
            errorMessages.put("tipoLogradouro", "Tipo de logradouro é obrigatório");
        }

        if (endereco.getApelidoEndereco() == null || endereco.getApelidoEndereco().equals("")) {
            errorMessages.put("apelidoEndereco", "Apelido do endereço é obrigatório");
        }

        if (endereco.getCliente() == null) {
            errorMessages.put("cliente", "Verificando dados do cliente");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMessages);
    }
}
