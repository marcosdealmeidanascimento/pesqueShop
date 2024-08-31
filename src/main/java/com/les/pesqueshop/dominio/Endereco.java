package com.les.pesqueshop.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Endereco extends EntidadeDominio{
    private String cep, tipoResidencia, logradouro, tipoLogradouro, numero, bairro, cidade, estado, pais, complemento, apelidoEndereco;
    private Cliente cliente;
    private boolean favorito;

    public Endereco() {
    }

    public Endereco(String cep, String tipoResidencia, String logradouro, String tipoLogradouro, String numero, String bairro, String cidade, String estado, String pais, String complemento, Cliente cliente, boolean favorito, String apelidoEndereco) {
        setCep(cep);
        setTipoResidencia(tipoResidencia);
        setLogradouro(logradouro);
        setTipoLogradouro(tipoLogradouro);
        setNumero(numero);
        setBairro(bairro);
        setCidade(cidade);
        setEstado(estado);
        setPais(pais);
        setComplemento(complemento);
        setFavorito(favorito);
        setCliente(cliente);
        setApelidoEndereco(apelidoEndereco);
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoResidencia() {
        return tipoResidencia;
    }

    public void setTipoResidencia(String tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    public String getApelidoEndereco() {
        return apelidoEndereco;
    }

    public void setApelidoEndereco(String apelidoEndereco) {
        this.apelidoEndereco = apelidoEndereco;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + getId() + '\'' +
                ", cep='" + cep + '\'' +
                ", tipoResidencia='" + tipoResidencia + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", tipoLogradouro='" + tipoLogradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                ", complemento='" + complemento + '\'' +
                ", apelidoEndereco='" + apelidoEndereco + '\'' +
                ", cliente=" + cliente +
                ", favorito=" + favorito +
                '}';
    }
}
