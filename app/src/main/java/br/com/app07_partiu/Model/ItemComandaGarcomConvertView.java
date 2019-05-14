package br.com.app07_partiu.Model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import br.com.app07_partiu.Util.Util;


public class ItemComandaGarcomConvertView extends Util implements Serializable {
    private int id;
    private long cnpjRestaurante;
    private String categoria;
    private String nome;
    private String tipo;
    private double valor;
    private String status;

    //Pedido
    private int idPedido;
    private double porc_desconto;
    private String idComanda;
    private String data;
    private String statusPedido;

    //Usuario / cliente
    private int idUsuario;
    private String nomeUsuario;
    private String emailUsuario;

    private List<Usuario> usuariosPedido;

    //Dados permanecem intactos; É retornada String já modificada nos gets
    //Util para fazer, por exmeplo, calculo de custo total (ainda existe o Double intacto)
    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getCnpjRestaurante() {
        return Long.toString(cnpjRestaurante);
    }

    public void setCnpjRestaurante(long cnpjRestaurante) {
        this.cnpjRestaurante = cnpjRestaurante;
    }

    public String getCategoria() {
        return capitalize(categoria);
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return capitalize(tipo);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValorString() {
        return doubleToReal(this.valor);
    }

    public double getValor(){
        return this.valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return capitalize(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getPorc_desconto() {
        return porc_desconto;
    }

    public void setPorc_desconto(double porc_desconto) {
        this.porc_desconto = porc_desconto;
    }

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public List<Usuario> getUsuariosPedido() {
        return usuariosPedido;
    }

    public void setUsuariosPedido(List<Usuario>  usuariosPedido) {
        this.usuariosPedido = usuariosPedido;
    }

    // retorna String[] com Data e Hora
    // string[0] -> data em dd/mm (25/05)
    // string[1] -> hora em hh:mm (23:12)
    //TODO Retornar há qnt tempo foi pedido em vez da hora, se necessário
    public String[] getDataHorario() {
        String[] temp = data.split(" ");
        String tempData = temp[0].split("-")[2]+"/"+temp[0].split("-")[1];
        String tempHora = temp[1].split(":")[0]+":"+temp[1].split(":")[1];
        String[] dataHora = {tempData, tempHora};
        return dataHora ;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
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
        return emailUsuario.toLowerCase();
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    @Override
    public String toString() {
        return "ItemComandaGarcomConvertView{" +
                "id=" + id +
                ", cnpjRestaurante=" + cnpjRestaurante +
                ", categoria='" + categoria + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", status='" + status + '\'' +
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

/*{
        "id": 2,
        "cnpjRestaurante": 3128939128,
        "categoria": "Porção",
        "nome": "Aussie Cheese Chicken Fries",
        "tipo": "1",
        "valor": 51.25,
        "status": "A",
        "idPedido": 4,
        "porc_desconto": 0,
        "data": "2019-05-12 12:24:41.0",
        "statusPedido": "P",
        "idUsuario": 1
        "nomeUsuario": "Nome Ha"
        "emailUsuario": "email@hehe.com"
    },*/

}
