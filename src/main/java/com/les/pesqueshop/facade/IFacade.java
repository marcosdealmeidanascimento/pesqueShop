package com.les.pesqueshop.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.List;

public interface IFacade {
    EntidadeDominio get(int id) throws SQLException;
    List<EntidadeDominio> getAll(int limit, int offset) throws SQLException;
    List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException;
    EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException;
    EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException;
    EntidadeDominio delete(int id) throws SQLException;
}
