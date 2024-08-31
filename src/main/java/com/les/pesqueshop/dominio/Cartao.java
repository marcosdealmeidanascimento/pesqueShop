package com.les.pesqueshop.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cartao extends EntidadeDominio{
    private String numero, nomeImpresso, cvv, bandeira, apelidoCartao;
    private Cliente cliente;
    private LocalDate validade;
    private boolean favorito;

    public Cartao() {
    }

    public Cartao(String numero, String nomeImpresso, String cvv, String bandeira, Cliente cliente, LocalDate validade, boolean favorito, String apelidoCartao) {
        setNumero(numero);
        setNomeImpresso(nomeImpresso);
        setCvv(cvv);
        setBandeira(bandeira);
        setCliente(cliente);
        setValidade(validade);
        setFavorito(favorito);
        setApelidoCartao(apelidoCartao);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomeImpresso() {
        return nomeImpresso;
    }

    public void setNomeImpresso(String nomeImpresso) {
        this.nomeImpresso = nomeImpresso;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getApelidoCartao() {
        return apelidoCartao;
    }

    public void setApelidoCartao(String apelidoCartao) {
        this.apelidoCartao = apelidoCartao;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id=" + getId() + '\'' +
                ", numero='" + numero + '\'' +
                ", nomeImpresso='" + nomeImpresso + '\'' +
                ", cvv='" + cvv + '\'' +
                ", bandeira='" + bandeira + '\'' +
                ", apelidoCartao='" + apelidoCartao + '\'' +
                ", cliente=" + cliente +
                ", validade=" + validade +
                ", favorito=" + favorito +
                '}';
    }
}
