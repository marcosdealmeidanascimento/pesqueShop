package com.les.pesqueshop.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dao.CartaoDAO;
import com.les.pesqueshop.dominio.Cartao;
import com.les.pesqueshop.dominio.EntidadeDominio;
import com.les.pesqueshop.dominio.Log;
import com.les.pesqueshop.strategies.GerarLog;
import com.les.pesqueshop.strategies.ValidarCartao;

import java.sql.SQLException;
import java.util.List;

public class CartaoFacade implements IFacade {
    private final CartaoDAO cartaoDAO;
    private final ValidarCartao validarCartao;
    private final GerarLog gerarLog;
    public CartaoFacade() throws SQLException {
        this.cartaoDAO = new CartaoDAO();
        this.validarCartao = new ValidarCartao();
        this.gerarLog = new GerarLog();
    }

    @Override
    public EntidadeDominio get(int id) throws SQLException {
        return cartaoDAO.get(id);
    }

    @Override
    public List<EntidadeDominio> getAll(int limit, int offset) throws SQLException {
        return cartaoDAO.getAll(limit, offset);
    }

    @Override
    public List<EntidadeDominio> filter(EntidadeDominio entidadeDominio, int limit, int offset) throws SQLException {
        return cartaoDAO.filter(entidadeDominio, limit, offset);
    }

    public List<EntidadeDominio> getCartoesCliente(int idCliente) throws SQLException {
        return cartaoDAO.getCartoesCliente(idCliente);
    }

    @Override
    public EntidadeDominio save(EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        Cartao cartao = (Cartao) entidadeDominio;
        String cartaoResponse = validarCartao.processar(entidadeDominio);

        if (!cartaoResponse.equals("{}")) {
            throw new SQLException(String.valueOf(cartaoResponse));
        }

        Cartao saveCartao =  (Cartao) cartaoDAO.save(entidadeDominio);

        cartao.setId(saveCartao.getId());
        Log log = objLog("INSERT", "", cartao.toString());

        gerarLog.processar(log);

        return cartao;
    }

    @Override
    public EntidadeDominio update(int id, EntidadeDominio entidadeDominio) throws SQLException, JsonProcessingException {
        Cartao cartaoAntigo = (Cartao) cartaoDAO.get(id);
        Cartao cartao = (Cartao) entidadeDominio;
        cartao.setId(id);

        String cartaoResponse = validarCartao.processar(entidadeDominio);

        if (!cartaoResponse.equals("{}")) {
            throw new SQLException(String.valueOf(cartaoResponse));
        }

        Cartao cartaoAtualizado =  (Cartao) cartaoDAO.update(id, entidadeDominio);

        Log log = objLog("UPDATE", cartaoAntigo.toString(), cartaoAtualizado.toString());

        gerarLog.processar(log);

        return cartaoAtualizado;
    }

    @Override
    public EntidadeDominio delete(int id) throws SQLException {
        return cartaoDAO.delete(id);
    }

    private Log objLog(String operacao, String dadosAntigos, String dadosNovos) {
        Log log = new Log();
        log.setOperacao(operacao);
        log.setDadosAntigos(dadosAntigos);
        log.setDadosNovos(dadosNovos);
        log.setUsuarioResponsavel("admin");
        log.setTabela("cartoes");
        return log;
    }
}
