package com.les.pesqueshop.dominio;

import java.time.LocalDateTime;

public class Log extends EntidadeDominio {
    private String usuarioResponsavel, operacao, tabela, dadosAntigos, dadosNovos;
    private LocalDateTime dataHora;

    public Log() {
    }

    public Log(String usuarioResponsavel, String operacao, String tabela, String dadosAntigos, String dadosNovos, LocalDateTime dataHora) {
        setUsuarioResponsavel(usuarioResponsavel);
        setOperacao(operacao);
        setTabela(tabela);
        setDadosAntigos(dadosAntigos);
        setDadosNovos(dadosNovos);
        setDataHora(dataHora);
    }

    public String getUsuarioResponsavel() {
        return usuarioResponsavel;
    }

    public void setUsuarioResponsavel(String usuarioResponsavel) {
        this.usuarioResponsavel = usuarioResponsavel;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getDadosAntigos() {
        return dadosAntigos;
    }

    public void setDadosAntigos(String dadosAntigos) {
        this.dadosAntigos = dadosAntigos;
    }

    public String getDadosNovos() {
        return dadosNovos;
    }

    public void setDadosNovos(String dadosNovos) {
        this.dadosNovos = dadosNovos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
