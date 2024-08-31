package com.les.pesqueshop.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente extends EntidadeDominio {
    private String nomeCompleto, genero, cpf, email, telefone, tipoTelefone, senha, telefoneDDD;
    private LocalDate dataNascimento;
    private List<Endereco> enderecos;
    private List<Cartao> cartoes;
    private Long ranking;
    private boolean status;

    public Cliente() {
    }

    public Cliente(String nomeCompleto, String genero, String cpf, String email, String telefone, String tipoTelefone, LocalDate dataNascimento, List<Endereco> enderecos, List<Cartao> cartoes, boolean status, String telefoneDDD) {
        setNomeCompleto(nomeCompleto);
        setGenero(genero);
        setCpf(cpf);
        setEmail(email);
        setTelefone(telefone);
        setTipoTelefone(tipoTelefone);
        setDataNascimento(dataNascimento);
        setEnderecos(enderecos);
        setCartoes(cartoes);
        setStatus(status);
        setTelefoneDDD(telefoneDDD);
    }

    public Cliente(String nomeCompleto, String genero, String cpf, String email, String telefone, String tipoTelefone, String senha, LocalDate dataNascimento, List<Endereco> enderecos, List<Cartao> cartoes, Long ranking, boolean status, String telefoneDDD) {
        setNomeCompleto(nomeCompleto);
        setGenero(genero);
        setCpf(cpf);
        setEmail(email);
        setTelefone(telefone);
        setTipoTelefone(tipoTelefone);
        setSenha(senha);
        setDataNascimento(dataNascimento);
        setEnderecos(enderecos);
        setCartoes(cartoes);
        setRanking(ranking);
        setStatus(status);
        setTelefoneDDD(telefoneDDD);
    }

    public Cliente(Long ranking, List<Cartao> cartoes, List<Endereco> enderecos, LocalDate dataNascimento, String tipoTelefone, String telefone, String email, String cpf, String genero, String nomeCompleto, boolean status, String telefoneDDD) {
        setNomeCompleto(nomeCompleto);
        setGenero(genero);
        setCpf(cpf);
        setEmail(email);
        setTelefone(telefone);
        setTipoTelefone(tipoTelefone);
        setDataNascimento(dataNascimento);
        setEnderecos(enderecos);
        setCartoes(cartoes);
        setRanking(ranking);
        setStatus(status);
        setTelefoneDDD(telefoneDDD);
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTelefoneDDD() {
        return telefoneDDD;
    }

    public void setTelefoneDDD(String telefoneDDD) {
        this.telefoneDDD = telefoneDDD;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", genero='" + genero + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefoneDDD='" + telefoneDDD + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipoTelefone='" + tipoTelefone + '\'' +
                ", senha='" + senha + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", ranking=" + ranking +
                ", status=" + status +
                '}';
    }
}
