package com.les.pesqueshop.dao;

import com.les.pesqueshop.database.DatabaseConnection;
import com.les.pesqueshop.dominio.Cartao;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartaoDAO implements IDAO {
    private final Connection conn;
    public CartaoDAO() {
        conn = DatabaseConnection.getConnection();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        String sql = "SELECT * FROM cartoes WHERE car_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getDadosCartao(rs);
            }
            return null;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar cartão", err);
        }
    }


    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM cartoes LIMIT ? OFFSET ?";
        List<EntidadeDominio> cartoes = new ArrayList<>();

        if (limit == 0) {
            limit = 10;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cartoes.add(getDadosCartao(rs));
            }
            return cartoes;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar cartões", err);
        }
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return null;
    }

    public List<EntidadeDominio> getCartoesCliente (int id) throws SQLException {
        String sql = "SELECT * FROM cartoes WHERE car_cli_id = ? ORDER BY car_favorito DESC";
        List<EntidadeDominio> cartoes = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cartoes.add(getDadosCartao(rs));
            }
            return cartoes;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar cartões do cliente", err);
        }
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException {
        var cartao = (Cartao) entidadeDominio;
        String sql = "INSERT INTO cartoes (car_numero, car_nome_impresso, car_cvv, car_bandeira, car_validade, car_favorito, car_apelido_cartao, car_cli_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING car_id";

        try {
            var ps = conn.prepareStatement(sql);
            setDadosCartao(ps, cartao);

            var rs = ps.executeQuery();
            if (rs.next()) {
                cartao.setId(rs.getInt("car_id"));
            }
            return cartao;
        } catch (SQLException err) {
            throw new SQLException(err);
        }
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException {
        Cartao cartao = (Cartao) entidadeDominio;
        String sql = "UPDATE cartoes SET car_numero = ?, car_nome_impresso = ?, car_cvv = ?, car_bandeira = ?, car_validade = ?, car_favorito = ?, car_apelido_cartao = ? WHERE car_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            setDadosCartao(ps, cartao);
            ps.setInt(8, id);
            ps.executeUpdate();
            cartao.setId(id);
            return cartao;
        } catch (SQLException err) {
            throw new SQLException(err);
        }
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        String sql = "DELETE FROM cartoes WHERE car_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            if (ps.getUpdateCount() > 0) {
                return new Cartao();
            }

            return null;
        } catch (SQLException err) {
            throw new SQLException("Erro ao deletar cartão", err);
        }
    }

    private Cartao getDadosCartao(ResultSet rs) throws SQLException {
        Cartao cartao = new Cartao();
        cartao.setId(rs.getInt("car_id"));
        cartao.setNumero(rs.getString("car_numero"));
        cartao.setNomeImpresso(rs.getString("car_nome_impresso"));
        cartao.setCvv(rs.getString("car_cvv"));
        cartao.setBandeira(rs.getString("car_bandeira"));
        cartao.setValidade(rs.getDate("car_validade").toLocalDate());
        cartao.setFavorito(rs.getBoolean("car_favorito"));
        cartao.setApelidoCartao(rs.getString("car_apelido_cartao"));

        return cartao;
    }

    private void setDadosCartao(PreparedStatement ps, Cartao cartao) throws SQLException {
        ps.setString(1, cartao.getNumero());
        ps.setString(2, cartao.getNomeImpresso());
        ps.setString(3, cartao.getCvv());
        ps.setString(4, cartao.getBandeira());
        ps.setDate(5, java.sql.Date.valueOf(cartao.getValidade()));
        ps.setBoolean(6, cartao.getFavorito());
        ps.setString(7, cartao.getApelidoCartao()); // Adicionando o parâmetro car_apelido_cartao
        ps.setInt(8, cartao.getCliente().getId()); // Adicionando o parâmetro car_cli_id
    }
}
