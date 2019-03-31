package br.com.app07_partiu;

import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Date;

public class Usuario implements Serializable {

    private String nome;
    private String sobrenome;
    private Date dataDeNascimento;
    private ArrayList<String> genero;
    private String cpf;
    private String email;
    private String senha;
    private String endereco;
    private String complemento;
    private ArrayList<String> cidade;
    private ArrayList<String> estado;
    private ArrayList<String> pais;
    private ArrayList<String> interesse;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public ArrayList<String> getGenero() {
        return genero;
    }

    public void setGenero(ArrayList<String> genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public ArrayList<String> getCidade() {
        return cidade;
    }

    public void setCidade(ArrayList<String> cidade) {
        this.cidade = cidade;
    }

    public ArrayList<String> getEstado() {
        return estado;
    }

    public void setEstado(ArrayList<String> estado) {
        this.estado = estado;
    }

    public ArrayList<String> getPais() {
        return pais;
    }

    public void setPais(ArrayList<String> pais) {
        this.pais = pais;
    }

    public ArrayList<String> getInteresse() {
        return interesse;
    }

    public void setInteresse(ArrayList<String> interesse) {
        this.interesse = interesse;
    }
}
