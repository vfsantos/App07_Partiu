package br.com.app07_partiu.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

import static br.com.app07_partiu.Util.Util.capitalize;
import static br.com.app07_partiu.Util.Util.doubleToReal;
import static br.com.app07_partiu.Util.Util.nomeToNomeUltimoAbrev;

public class Item implements Serializable {

    //Item
    private int id;
    private String cnpjRestaurante;
    private String categoria;
    private String nome;
    private String tipo;
    private double valor;
    private String status;
    private String detalhe;

    //Pedido
    private Usuario usuario;
    private int idPedido;
    private double porc_desconto;
    private String idComanda;
    private String data;
    private String statusPedido;
    private String obsPedido;

    //Usuario
    private int idUsuario;
    private String nomeUsuario;
    private String emailUsuario;

    //Lista de Usuarios
    private List<Usuario> usuariosPedido;

    private double porcPaga;
    private String statusPedidoUsuario;


    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getObsPedido() {
        return obsPedido;
    }

    public void setObsPedido(String obsPedido) {
        this.obsPedido = obsPedido;
    }

    public double getPorcPaga() {
        return porcPaga;
    }

    public void setPorcPaga(double porcPaga) {
        this.porcPaga = porcPaga;
    }

    public String getStatusPedidoUsuario() {
        return statusPedidoUsuario;
    }

    public void setStatusPedidoUsuario(String statusPedidoUsuario) {
        this.statusPedidoUsuario = statusPedidoUsuario;
    }

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

    public String getCnpjRestaurante() {
        return cnpjRestaurante;
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

    public void setCnpjRestaurante(String cnpjRestaurante) {
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
                status ="Não Selecionado";
                break;
            case "S":
                status = "Selecionado: ";
                for (Usuario u : this.usuariosPedido){
                    status.replace(";",",");
                    status += nomeToNomeUltimoAbrev(u.getNome()) + ";";
                }
                status.replace(";","");

                break;
            case "P":
                status = "Pago";
                break;
            default:
                Log.e("TESTES", "Status Diff = "+statusPedido);
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
                ", detalhe='" + detalhe + '\'' +
                ", usuario=" + usuario +
                ", idPedido=" + idPedido +
                ", porc_desconto=" + porc_desconto +
                ", idComanda='" + idComanda + '\'' +
                ", data='" + data + '\'' +
                ", statusPedido='" + statusPedido + '\'' +
                ", obsPedido='" + obsPedido + '\'' +
                ", idUsuario=" + idUsuario +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", usuariosPedido=" + usuariosPedido +
                ", porcPaga=" + porcPaga +
                ", statusPedidoUsuario='" + statusPedidoUsuario + '\'' +
                '}';
    }
}
