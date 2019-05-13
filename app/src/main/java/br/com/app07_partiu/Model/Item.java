package br.com.app07_partiu.Model;

import java.io.Serializable;

public class Item implements Serializable {

    private int id;
    private long cnpjRestaurante;
    private String categoria;
    private String nome;
    private String tipo;
    private double valor;
    private String status;

    //Pedido
    private Usuario usuario;
    private int idPedido;
    private double porc_desconto;
    private String idComanda;
    private String data;
    private String statusPedido;

    public Usuario getUsuario() {
        return usuario;
    }
    public double getPorc_desconto() {
        return porc_desconto;
    }
    public String getIdComanda() {
        return idComanda;
    }
    public String getData() {
        return data;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public void setPorc_desconto(double porc_desconto) {
        this.porc_desconto = porc_desconto;
    }
    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }
    public void setData(String data) {
        this.data = data;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getCnpjRestaurante() {
        return cnpjRestaurante;
    }
    public void setCnpjRestaurante(long cnpjRestaurante) {
        this.cnpjRestaurante = cnpjRestaurante;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public Item() {
        super();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }
}