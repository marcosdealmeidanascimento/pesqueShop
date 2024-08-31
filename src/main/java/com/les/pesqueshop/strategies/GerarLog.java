package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dao.LogDAO;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;

public class GerarLog implements IStrategy {
    private final LogDAO logDAO;

    public GerarLog() throws SQLException {
        this.logDAO = new LogDAO();
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        logDAO.save(entidade);
        return "Log gerado com sucesso";
    }
}
