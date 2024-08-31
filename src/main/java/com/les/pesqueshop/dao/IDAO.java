package com.les.pesqueshop.dao;

import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.List;

public interface IDAO {
    EntidadeDominio get(int id) throws SQLException;
    List<EntidadeDominio> getAll(int limit, int offset) throws SQLException;
    List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException;
    EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException;
    EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException;
    EntidadeDominio delete(int id) throws SQLException;
}
