package com.les.pesqueshop.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dao.ClienteDAO;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;
import com.les.pesqueshop.dominio.Log;
import com.les.pesqueshop.strategies.GerarLog;
import com.les.pesqueshop.strategies.ValidarDadosCliente;
import com.les.pesqueshop.strategies.ValidarSenha;
import com.les.pesqueshop.strategies.VerificarExistenciaCliente;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ClienteFacade implements IFacade {
    private final ClienteDAO clienteDAO;
    private final VerificarExistenciaCliente verificarExistenciaCliente;
    private final ValidarDadosCliente validarDadosCliente;
    private final ValidarSenha validarSenha;
    private final GerarLog gerarLog;

    public ClienteFacade() throws SQLException {
        this.clienteDAO = new ClienteDAO();
        this.verificarExistenciaCliente = new VerificarExistenciaCliente();
        this.validarDadosCliente = new ValidarDadosCliente();
        this.validarSenha = new ValidarSenha();
        this.gerarLog = new GerarLog();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        return clienteDAO.get(id);
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        return clienteDAO.getAll(limit, offset);
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return clienteDAO.filter(entidadeDominio, limit, offset);
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        Cliente cliente = (Cliente) entidadeDominio;
        String existenciaResponse = verificarExistenciaCliente.processar(entidadeDominio);
        String dadosResponse = validarDadosCliente.processar(entidadeDominio);
        String senhaResponse = validarSenha.processar(entidadeDominio);

        if (!existenciaResponse.equals("{}")) {
            throw new SQLException(String.valueOf(existenciaResponse));
        }

        if (cliente.getSenha() == null || cliente.getSenha().equals("")) {
            throw new SQLException("Senha é obrigatória");
        }

        if (!senhaResponse.equals("{}")) {
            throw new SQLException(String.valueOf(senhaResponse));
        }

        if (!dadosResponse.equals("{}")) {
            throw new SQLException(String.valueOf(dadosResponse));
        }

        Cliente saveCliente =  (Cliente) clienteDAO.save(entidadeDominio);

        cliente.setId(saveCliente.getId());

        Log log = objLog("INSERT", "", cliente.toString());
        gerarLog.processar(log);

        return cliente;
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        Cliente clienteAntigo = (Cliente) clienteDAO.get(id);
        Cliente cliente = (Cliente) entidadeDominio;
        cliente.setId(id);

        String existenciaResponse = verificarExistenciaCliente.processar(entidadeDominio);

        if (!existenciaResponse.equals("{}")) {
            Map<String, String> response = new com.fasterxml.jackson.databind.ObjectMapper().readValue(existenciaResponse, Map.class);
            if (!response.get("id").equals(String.valueOf(id))) {
                throw new SQLException("Cliente já cadastrado");
            }
        }

        if (cliente.getSenha() != null) {
            String senhaResponse = validarSenha.processar(entidadeDominio);

            if (!senhaResponse.equals("{}")) {
                throw new SQLException(String.valueOf(senhaResponse));
            }
        }

        String dadosResponse = validarDadosCliente.processar(entidadeDominio);

        if (!dadosResponse.equals("{}")) {
            throw new SQLException(String.valueOf(dadosResponse));
        }

        Cliente clienteAtualizado = (Cliente) clienteDAO.update(id, entidadeDominio);
        Log log = objLog("UPDATE", clienteAntigo.toString(), clienteAtualizado.toString());
        gerarLog.processar(log);

        return clienteAtualizado;
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        return clienteDAO.delete(id);
    }

    private Log objLog(String operacao, String dadosAntigos, String dadosNovos) {
        Log log = new Log();
        log.setOperacao(operacao);
        log.setDadosAntigos(dadosAntigos);
        log.setDadosNovos(dadosNovos);
        log.setUsuarioResponsavel("admin");
        log.setTabela("clientes");
        return log;
    }
}
