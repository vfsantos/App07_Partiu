package br.com.app07_partiu.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int id;
    private String tipo;
    private long cpf;
    private String nome;
    private String dta_nascimento;
    private String email;
    private int ddd;
    private int telefone;
    private char genero;
    private String senha;
    private Endereco endereco;
    private String statusPedido;
    private double porcPedido;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public long getCpf() {
        return cpf;
    }
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDta_nascimento() {
        return dta_nascimento;
    }
    public void setDta_nascimento(String dta_nascimento) {
        this.dta_nascimento = dta_nascimento;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getDdd() {
        return ddd;
    }
    public void setDdd(int ddd) {
        this.ddd = ddd;
    }
    public int getTelefone() {
        return telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    public char getGenero() {
        return genero;
    }
    public void setGenero(char genero) {
        this.genero = genero;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public double getPorcPedido() {
        return porcPedido;
    }

    public void setPorcPedido(double porcPedido) {
        this.porcPedido = porcPedido;
    }

    public Usuario(int id, String tipo, long cpf, String nome, String dta_nascimento, String email, int ddd,
                   int telefone, char genero, String senha, Endereco endereco) {
        super();
        this.id = id;
        this.tipo = tipo;
        this.cpf = cpf;
        this.nome = nome;
        this.dta_nascimento = dta_nascimento;
        this.email = email;
        this.ddd = ddd;
        this.telefone = telefone;
        this.genero = genero;
        this.senha = senha;
        this.endereco = endereco;
    }
    public Usuario() {
        super();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", cpf=" + cpf +
                ", nome='" + nome + '\'' +
                ", dta_nascimento='" + dta_nascimento + '\'' +
                ", email='" + email + '\'' +
                ", ddd=" + ddd +
                ", telefone=" + telefone +
                ", genero=" + genero +
                ", senha='" + senha + '\'' +
                ", endereco=" + endereco +
                ", statusPedido='" + statusPedido + '\'' +
                ", porcPedido=" + porcPedido +
                '}';
    }
}
