package br.com.app07_partiu.Model;

import java.io.Serializable;
import java.util.List;

import static br.com.app07_partiu.Util.Util.capitalize;
import static br.com.app07_partiu.Util.Util.doubleToReal;

public class Item implements Serializable {

    //Item
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

    //Usuario
    private int idUsuario;
    private String nomeUsuario;
    private String emailUsuario;

    //Lista de Usuarios
    private List<Usuario> usuariosPedido;



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

    public int getId() {
        return id;
    }

    public long getCnpjRestaurante() {
        return cnpjRestaurante;
    }
    public String getCnpjRestauranteString() {
        return Long.toString(cnpjRestaurante);
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

    public void setId(int id) {
        this.id = id;
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
        return capitalize(categoria);
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusString() {
        String status;
        switch(statusPedido){
            case "N":
                status ="Não Selecionado por Usuário";
                break;
            case "S":
                status = "Selecionado por Usuário";
                break;
            case "P":
                status = "Item Pago";
                break;
            default:
                status = "Status Inválido - diff de N, S ou P";
                break;
        }
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

    public String getValorString() {
        return doubleToReal(this.valor);
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
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

    public List<Usuario> getUsuariosPedido() {
        return usuariosPedido;
    }

    public void setUsuariosPedido(List<Usuario> usuariosPedido) {
        this.usuariosPedido = usuariosPedido;
    }

    public String[] getDataHorario() {
        String[] temp = data.split(" ");
        String tempData = temp[0].split("-")[2]+"/"+temp[0].split("-")[1];
        String tempHora = temp[1].split(":")[0]+":"+temp[1].split(":")[1];
        String[] dataHora = {tempData, tempHora};
        return dataHora ;
    }

    public static Item[] itemListToArray(List<Item> itens){
        Object[] objects = itens.toArray();
        Item[] itensArray = new Item[objects.length];
        for (int i = 0; i < objects.length; i++) {
            itensArray[i] = (Item) objects[i];
        }
        return itensArray;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", cnpjRestaurante=" + cnpjRestaurante +
                ", categoria='" + categoria + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", status='" + status + '\'' +
                ", usuario=" + usuario +
                ", idPedido=" + idPedido +
                ", porc_desconto=" + porc_desconto +
                ", idComanda='" + idComanda + '\'' +
                ", data='" + data + '\'' +
                ", statusPedido='" + statusPedido + '\'' +
                ", idUsuario=" + idUsuario +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                '}';
    }
}
