package com.les.pesqueshop.dao;

import com.les.pesqueshop.database.DatabaseConnection;
import com.les.pesqueshop.dominio.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IDAO {
    private final Connection conn;
    public ClienteDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        String sql = "SELECT cli_id, cli_cpf, cli_nome_completo, cli_genero, cli_email, cli_telefone_ddd, cli_telefone, cli_tipo_telefone, cli_data_nascimento, cli_status FROM clientes c WHERE cli_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getDadosCliente(rs);
            }
            return null;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar cliente", err);
        }
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        String sqlCliente = "SELECT cli_id, cli_cpf, cli_nome_completo, cli_genero, cli_email, cli_telefone_ddd, cli_telefone, cli_tipo_telefone, cli_data_nascimento, cli_status FROM clientes WHERE cli_status = true LIMIT ? OFFSET ?";
        List<EntidadeDominio> clientes = new ArrayList<>();

        if (limit == 0) {
            limit = 10;
        }

        try {
            PreparedStatement psCliente = conn.prepareStatement(sqlCliente);
            psCliente.setInt(1, limit);
            psCliente.setInt(2, offset);
            ResultSet rsCliente = psCliente.executeQuery();

            while (rsCliente.next()) {
                Cliente cliente = getDadosCliente(rsCliente);
                cliente.setEnderecos(getEnderecos(cliente.getId()));
                cliente.setCartoes(getCartoes(cliente.getId()));
                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException err) {
            throw new SQLException("Erro ao buscar clientes", err);
        }
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        Cliente cliente = (Cliente) entidadeDominio;
        List<EntidadeDominio> clientes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT cli_id, cli_cpf, cli_nome_completo, cli_genero, cli_email, cli_telefone_ddd, cli_telefone, cli_tipo_telefone, cli_data_nascimento, cli_status FROM clientes c WHERE 1 = 1");

        List<Object> params = new ArrayList<>();

        if (limit == 0) {
            limit = 10;
        }

        if (cliente.getCpf() != null) {
            sql.append(" AND cli_cpf = ?");
            params.add(cliente.getCpf());
        }

        if (cliente.getNomeCompleto() != null) {
            sql.append(" AND cli_nome_completo LIKE ?");
            params.add("%" + cliente.getNomeCompleto() + "%");
        }

        if (cliente.getGenero() != null) {
            sql.append(" AND cli_genero = ?");
            params.add(cliente.getGenero());
        }

        if (cliente.getEmail() != null) {
            sql.append(" OR cli_email = ?");
            params.add(cliente.getEmail());
        }

        if (cliente.getTelefoneDDD() != null) {
            sql.append(" AND cli_telefone_ddd = ?");
            params.add(cliente.getTelefoneDDD());
        }

        if (cliente.getTelefone() != null) {
            sql.append(" AND cli_telefone LIKE ?");
            params.add("%" + cliente.getTelefone() + "%");
        }

        if (cliente.getTipoTelefone() != null) {
            sql.append(" AND cli_tipo_telefone = ?");
            params.add(cliente.getTipoTelefone());
        }

        if (cliente.getDataNascimento() != null) {
            sql.append(" AND cli_data_nascimento = ?");
            params.add(Date.valueOf(cliente.getDataNascimento()));
        }

        sql.append(" LIMIT ? OFFSET ?");

        PreparedStatement ps = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ps.setInt(params.size() + 1, limit);
        ps.setInt(params.size() + 2, offset);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Cliente clienteFiltrado = getDadosCliente(rs);
            clienteFiltrado.setEnderecos(getEnderecos(clienteFiltrado.getId()));
            clienteFiltrado.setCartoes(getCartoes(clienteFiltrado.getId()));
            clientes.add(clienteFiltrado);
        }

        return clientes;
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException {
        Cliente cliente = (Cliente) entidadeDominio;
        String sql = "INSERT INTO clientes (cli_nome_completo, cli_genero, cli_cpf, cli_email, cli_telefone_ddd, cli_telefone, cli_tipo_telefone, cli_data_nascimento, cli_senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING cli_id";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            setDadosCliente(ps, cliente);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("cli_id"));
            }

            return cliente;
        } catch (SQLException err) {
            throw new SQLException("Erro ao salvar cliente", err);
        }

    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException {
        Cliente cliente = (Cliente) entidadeDominio;
        String sqlDadosPessoais = "UPDATE clientes SET cli_nome_completo = ?, cli_genero = ?, cli_cpf = ?, cli_email = ?, cli_telefone_ddd = ?, cli_telefone = ?, cli_tipo_telefone = ?, cli_data_nascimento = ?, cli_status = ? WHERE cli_id = ?";
        String sqlSenha = "UPDATE clientes SET cli_senha = ? WHERE cli_id = ?";

        try {
            PreparedStatement psDadosPessoais = conn.prepareStatement(sqlDadosPessoais);
            updateDadosCliente(psDadosPessoais, cliente);
            psDadosPessoais.setInt(10, id);
            psDadosPessoais.execute();

            if (cliente.getSenha() != null) {
                PreparedStatement psSenha = conn.prepareStatement(sqlSenha);
                psSenha.setString(1, cliente.getSenha());
                psSenha.setInt(2, id);
                psSenha.execute();
            }

            cliente.setId(id);

            return cliente;

        } catch (SQLException err) {
            throw new SQLException("Erro ao atualizar cliente", err);
        }

    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        Cliente cliente = (Cliente) get(id);

        String sql = "UPDATE clientes SET cli_status = false WHERE cli_id = ?";

        String sqlDeleteEnderecos = "DELETE FROM enderecos WHERE end_cli_id = ?";
        String sqlDeleteCartoes = "DELETE FROM cartoes WHERE car_cli_id = ?";
        String sqlDeleteCliente = "DELETE FROM clientes WHERE cli_id = ?";

        try {
            PreparedStatement psEnderecos = conn.prepareStatement(sqlDeleteEnderecos);
            psEnderecos.setInt(1, id);
            psEnderecos.execute();

            PreparedStatement psCartoes = conn.prepareStatement(sqlDeleteCartoes);
            psCartoes.setInt(1, id);
            psCartoes.execute();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            return cliente;


        } catch (SQLException err) {
            throw new SQLException("Erro ao deletar cliente", err);
        }
    }

    private Cliente getDadosCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getString("cli_nome_completo"),
                rs.getString("cli_genero"),
                rs.getString("cli_cpf"),
                rs.getString("cli_email"),
                rs.getString("cli_telefone"),
                rs.getString("cli_tipo_telefone"),
                rs.getDate("cli_data_nascimento").toLocalDate(),
                new ArrayList<Endereco>(),
                new ArrayList<Cartao>(),
                rs.getBoolean("cli_status"),
                rs.getString("cli_telefone_ddd")
                );
        cliente.setId(rs.getInt("cli_id"));

        Endereco endereco = new Endereco();
        String sqlEndereco = "SELECT * FROM enderecos WHERE end_cli_id = ?";
        PreparedStatement psEndereco = conn.prepareStatement(sqlEndereco);
        psEndereco.setInt(1, cliente.getId());
        ResultSet rsEndereco = psEndereco.executeQuery();
        while (rsEndereco.next()) {
            endereco.setId(rsEndereco.getInt("end_id"));
            endereco.setCep(rsEndereco.getString("end_cep"));
            endereco.setLogradouro(rsEndereco.getString("end_logradouro"));
            endereco.setTipoLogradouro(rsEndereco.getString("end_tipo_logradouro"));
            endereco.setNumero(rsEndereco.getString("end_numero"));
            endereco.setComplemento(rsEndereco.getString("end_complemento"));
            endereco.setBairro(rsEndereco.getString("end_bairro"));
            endereco.setCidade(rsEndereco.getString("end_cidade"));
            endereco.setEstado(rsEndereco.getString("end_estado"));
            endereco.setPais(rsEndereco.getString("end_pais"));
            cliente.getEnderecos().add(endereco);
        }

        Cartao cartao = new Cartao();
        String sqlCartao = "SELECT * FROM cartoes WHERE car_cli_id = ?";
        PreparedStatement psCartao = conn.prepareStatement(sqlCartao);
        psCartao.setInt(1, cliente.getId());
        ResultSet rsCartao = psCartao.executeQuery();
        while (rsCartao.next()) {
            cartao.setId(rsCartao.getInt("car_id"));
            cartao.setNumero(rsCartao.getString("car_numero"));
            cartao.setNomeImpresso(rsCartao.getString("car_nome_impresso"));
            cartao.setBandeira(rsCartao.getString("car_bandeira"));
            cartao.setCvv(rsCartao.getString("car_cvv"));
            cartao.setValidade(rsCartao.getDate("car_validade").toLocalDate());
            cliente.getCartoes().add(cartao);
        }

        return cliente;
    }

    private void setDadosCliente(PreparedStatement ps, Cliente cliente) throws SQLException {
        ps.setString(1, cliente.getNomeCompleto());
        ps.setString(2, cliente.getGenero());
        ps.setString(3, cliente.getCpf());
        ps.setString(4, cliente.getEmail());
        ps.setString(5, cliente.getTelefoneDDD());
        ps.setString(6, cliente.getTelefone());
        ps.setString(7, cliente.getTipoTelefone());
        ps.setDate(8, Date.valueOf(cliente.getDataNascimento()));
        ps.setString(9, cliente.getSenha());
    }

    private void updateDadosCliente(PreparedStatement ps, Cliente cliente) throws SQLException {
        ps.setString(1, cliente.getNomeCompleto());
        ps.setString(2, cliente.getGenero());
        ps.setString(3, cliente.getCpf());
        ps.setString(4, cliente.getEmail());
        ps.setString(5, cliente.getTelefoneDDD());
        ps.setString(6, cliente.getTelefone());
        ps.setString(7, cliente.getTipoTelefone());
        ps.setDate(8, Date.valueOf(cliente.getDataNascimento()));
        ps.setBoolean(9, cliente.getStatus());
    }

    private List<Endereco> getEnderecos(int clienteId) throws SQLException {
        String sqlEndereco = "SELECT * FROM enderecos WHERE end_cli_id = ?";
        List<Endereco> enderecos = new ArrayList<>();

        PreparedStatement psEndereco = conn.prepareStatement(sqlEndereco);
        psEndereco.setInt(1, clienteId);
        ResultSet rsEndereco = psEndereco.executeQuery();
        while (rsEndereco.next()) {
            Endereco endereco = new Endereco();
            endereco.setId(rsEndereco.getInt("end_id"));
            endereco.setCep(rsEndereco.getString("end_cep"));
            endereco.setLogradouro(rsEndereco.getString("end_logradouro"));
            endereco.setTipoLogradouro(rsEndereco.getString("end_tipo_logradouro"));
            endereco.setNumero(rsEndereco.getString("end_numero"));
            endereco.setComplemento(rsEndereco.getString("end_complemento"));
            endereco.setBairro(rsEndereco.getString("end_bairro"));
            endereco.setCidade(rsEndereco.getString("end_cidade"));
            endereco.setEstado(rsEndereco.getString("end_estado"));
            endereco.setPais(rsEndereco.getString("end_pais"));
            enderecos.add(endereco);
        }
        return enderecos;
    }

    private List<Cartao> getCartoes(int clienteId) throws SQLException {
        String sqlCartao = "SELECT * FROM cartoes WHERE car_cli_id = ?";
        List<Cartao> cartoes = new ArrayList<>();

        PreparedStatement psCartao = conn.prepareStatement(sqlCartao);
        psCartao.setInt(1, clienteId);
        ResultSet rsCartao = psCartao.executeQuery();
        while (rsCartao.next()) {
            Cartao cartao = new Cartao();
            cartao.setId(rsCartao.getInt("car_id"));
            cartao.setNumero(rsCartao.getString("car_numero"));
            cartao.setNomeImpresso(rsCartao.getString("car_nome_impresso"));
            cartao.setBandeira(rsCartao.getString("car_bandeira"));
            cartao.setCvv(rsCartao.getString("car_cvv"));
            cartao.setValidade(rsCartao.getDate("car_validade").toLocalDate());
            cartoes.add(cartao);
        }
        return cartoes;
    }

}