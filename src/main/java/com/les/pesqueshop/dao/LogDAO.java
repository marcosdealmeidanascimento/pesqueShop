package com.les.pesqueshop.dao;

import com.les.pesqueshop.database.DatabaseConnection;
import com.les.pesqueshop.dominio.EntidadeDominio;
import com.les.pesqueshop.dominio.Log;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDAO implements IDAO {
    private final Connection conn;

    public LogDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }
    @Override
    public EntidadeDominio get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        List<EntidadeDominio> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs ORDER BY log_data_hora DESC LIMIT ? OFFSET ?";

        if (limit == 0) {
            limit = 10;
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, limit);
        ps.setInt(2, offset);
        ps.execute();

        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            logs.add(getLog(rs));
        }

        return logs;
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return List.of();
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException {
        Log log = (Log) entidadeDominio;
        String sql = "INSERT INTO logs (log_data_hora, log_usuario_responsavel, log_operacao, log_tabela, log_dados_antigos, log_dados_novos) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setDadosLog(ps, log);
        ps.executeUpdate();
        return log;
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException {
        return null;
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        return null;
    }

    private Log getLog(ResultSet rs) throws SQLException {
        Log log = new Log();
        log.setId(rs.getInt("log_id"));
        log.setDataHora(rs.getTimestamp("log_data_hora").toLocalDateTime());
        log.setUsuarioResponsavel(rs.getString("log_usuario_responsavel"));
        log.setOperacao(rs.getString("log_operacao"));
        log.setTabela(rs.getString("log_tabela"));
        log.setDadosAntigos(rs.getString("log_dados_antigos"));
        log.setDadosNovos(rs.getString("log_dados_novos"));
        return log;
    }

    private void setDadosLog(PreparedStatement ps, Log log) throws SQLException {
        ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(2, log.getUsuarioResponsavel());
        ps.setString(3, log.getOperacao());
        ps.setString(4, log.getTabela());
        ps.setString(5, log.getDadosAntigos());
        ps.setString(6, log.getDadosNovos());
    }
}
