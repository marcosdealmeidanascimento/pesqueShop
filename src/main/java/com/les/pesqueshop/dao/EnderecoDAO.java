package com.les.pesqueshop.dao;

import com.les.pesqueshop.database.DatabaseConnection;
import com.les.pesqueshop.dominio.Endereco;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements IDAO {
    private final Connection conn;
    public EnderecoDAO() throws SQLException {
        conn = DatabaseConnection.getConnection();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        String sql = "SELECT * FROM enderecos WHERE end_id = ?";

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return getDadosEndereco(rs);
            }
            return null;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar endereço", err);
        }
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM enderecos LIMIT ? OFFSET ?";
        List<EntidadeDominio> enderecos = new ArrayList<>();

        if (limit == 0) {
            limit = 10;
        }

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            var rs = ps.executeQuery();

            while (rs.next()) {
                enderecos.add(getDadosEndereco(rs));
            }
            return enderecos;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar endereços", err);
        }

    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return null;
    }

    public List<EntidadeDominio> getEnderecosCliente(int id) throws SQLException {
        String sql = "SELECT * FROM enderecos WHERE end_cli_id = ?";
        List<EntidadeDominio> enderecos = new ArrayList<>();

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            var rs = ps.executeQuery();

            while (rs.next()) {
                enderecos.add(getDadosEndereco(rs));
            }
            return enderecos;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar endereços", err);
        }
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException {
        var endereco = (Endereco) entidadeDominio;
        String sql = "INSERT INTO enderecos (end_cep, end_tipo_residencia, end_logradouro, end_tipo_logradouro, end_numero, end_bairro, end_cidade, end_estado, end_pais, end_complemento, end_favorito, end_apelido_endereco, end_cli_id, end_cobranca, end_observacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING end_id";

        try {
            var ps = conn.prepareStatement(sql);
            setDadosEndereco(endereco, ps);

            var rs = ps.executeQuery();
            if (rs.next()) {
                endereco.setId(rs.getInt("end_id"));
            }
            return endereco;
        } catch (SQLException err) {
            throw new SQLException(err);
        }
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException {
        var endereco = (Endereco) entidadeDominio;
        String sql = "UPDATE enderecos SET end_cep = ?, end_tipo_residencia = ?, end_logradouro = ?, end_tipo_logradouro = ?, end_numero = ?, end_bairro = ?, end_cidade = ?, end_estado = ?, end_pais = ?, end_complemento = ?, end_favorito = ?, end_apelido_endereco = ?, end_cli_id = ?, end_cobranca = ?, end_observacao = ? WHERE end_id = ?";

        try {
            var ps = conn.prepareStatement(sql);
            setDadosEndereco(endereco, ps);
            ps.setInt(16, id);
            ps.executeUpdate();
            endereco.setId(id);
            return endereco;
        } catch (SQLException err) {
            throw new SQLException(err);
        }
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        String sql = "DELETE FROM enderecos WHERE end_id = ?";

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            if (ps.getUpdateCount() > 0) {
                return new Endereco();
            }

            return null;
        } catch (SQLException err) {
            throw new SQLException("Erro ao deletar endereço", err);
        }
    }

    private Endereco getDadosEndereco(java.sql.ResultSet rs) throws SQLException {
        var endereco = new Endereco();
        endereco.setId(rs.getInt("end_id"));
        endereco.setCep(rs.getString("end_cep"));
        endereco.setTipoResidencia(rs.getString("end_tipo_residencia"));
        endereco.setLogradouro(rs.getString("end_logradouro"));
        endereco.setTipoLogradouro(rs.getString("end_tipo_logradouro"));
        endereco.setNumero(rs.getString("end_numero"));
        endereco.setComplemento(rs.getString("end_complemento"));
        endereco.setBairro(rs.getString("end_bairro"));
        endereco.setCidade(rs.getString("end_cidade"));
        endereco.setEstado(rs.getString("end_estado"));
        endereco.setPais(rs.getString("end_pais"));
        endereco.setFavorito(rs.getBoolean("end_favorito"));
        endereco.setApelidoEndereco(rs.getString("end_apelido_endereco"));
        endereco.setCobranca(rs.getBoolean("end_cobranca"));
        endereco.setObservacao(rs.getString("end_observacao"));
        return endereco;
    }

    private void setDadosEndereco(Endereco endereco, java.sql.PreparedStatement ps) throws SQLException {
        ps.setString(1, endereco.getCep());
        ps.setString(2, endereco.getTipoResidencia());
        ps.setString(3, endereco.getLogradouro());
        ps.setString(4, endereco.getTipoLogradouro());
        ps.setString(5, endereco.getNumero());
        ps.setString(6, endereco.getBairro());
        ps.setString(7, endereco.getCidade());
        ps.setString(8, endereco.getEstado());
        ps.setString(9, endereco.getPais());
        ps.setString(10, endereco.getComplemento());
        ps.setBoolean(11, endereco.getFavorito());
        ps.setString(12, endereco.getApelidoEndereco());
        ps.setInt(13, endereco.getCliente().getId());
        ps.setBoolean(14, endereco.getCobranca());
        ps.setString(15, endereco.getObservacao());
    }
}
