package com.les.pesqueshop.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dao.LogDAO;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.List;

public class LogFacade implements IFacade {
    private final LogDAO logDAO;

    public LogFacade() throws SQLException {
        this.logDAO = new LogDAO();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        return logDAO.getAll(limit, offset);
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return List.of();
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        return null;
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        return null;
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        return null;
    }
}
