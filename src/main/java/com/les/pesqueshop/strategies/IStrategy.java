package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;

public interface IStrategy {
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException;
}
