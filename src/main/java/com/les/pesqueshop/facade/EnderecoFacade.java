package com.les.pesqueshop.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dao.EnderecoDAO;
import com.les.pesqueshop.dominio.Endereco;
import com.les.pesqueshop.dominio.EntidadeDominio;
import com.les.pesqueshop.dominio.Log;
import com.les.pesqueshop.strategies.GerarLog;
import com.les.pesqueshop.strategies.ValidarEndereco;

import java.sql.SQLException;
import java.util.List;

public class EnderecoFacade implements IFacade {
    private final EnderecoDAO enderecoDAO;
    private final ValidarEndereco validarEndereco;
    private final GerarLog gerarLog;
    public EnderecoFacade() throws SQLException {
        this.enderecoDAO = new EnderecoDAO();
        this.validarEndereco = new ValidarEndereco();
        this.gerarLog = new GerarLog();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        return enderecoDAO.get(id);
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        return enderecoDAO.getAll(limit, offset);
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return enderecoDAO.filter(entidadeDominio, limit, offset);
    }

    public List<EntidadeDominio> getEnderecosCliente(int id) throws SQLException {
        return enderecoDAO.getEnderecosCliente(id);
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        Endereco endereco = (Endereco) entidadeDominio;
        String enderecoResponse = validarEndereco.processar(entidadeDominio);

        if (!enderecoResponse.equals("{}")) {
            throw new SQLException(String.valueOf(enderecoResponse));
        }
        Endereco saveEndereco =  (Endereco) enderecoDAO.save(entidadeDominio);

        endereco.setId(saveEndereco.getId());
        Log log = objLog("insert", "", endereco.toString());
        gerarLog.processar(log);

        return endereco;
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        Endereco enderecoAntigo = (Endereco) enderecoDAO.get(id);
        Endereco endereco = (Endereco) entidadeDominio;
        endereco.setId(id);
        String enderecoResponse = validarEndereco.processar(entidadeDominio);

        if (!enderecoResponse.equals("{}")) {
            throw new SQLException(String.valueOf(enderecoResponse));
        }

        Endereco enderecoAtualizado =  (Endereco) enderecoDAO.update(id, entidadeDominio);
        Log log = objLog("update", enderecoAntigo.toString(), enderecoAtualizado.toString());
        gerarLog.processar(log);

        return enderecoAtualizado;
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        return enderecoDAO.delete(id);
    }

    private Log objLog(String operacao, String dadosAntigos, String dadosNovos) {
        Log log = new Log();
        log.setOperacao(operacao);
        log.setDadosAntigos(dadosAntigos);
        log.setDadosNovos(dadosNovos);
        log.setUsuarioResponsavel("admin");
        log.setTabela("enderecos");
        return log;
    }
}
