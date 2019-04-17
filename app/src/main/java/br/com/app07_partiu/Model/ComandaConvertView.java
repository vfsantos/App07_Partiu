package br.com.app07_partiu.Model;

import java.util.Date;

public class ComandaConvertView {

    private String codigoComanda;
    private String mesa;
    private String status;
    private String dataEntrada;
    private String dataSaida;
    private String valorTotalComanda;

    public String getCodigoComanda() {
        return codigoComanda;
    }

    public void setCodigoComanda(String codigoComanda) {
        this.codigoComanda = codigoComanda;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getValorTotalComanda() {
        return valorTotalComanda;
    }

    public void setValorTotalComanda(String valorTotalComanda) {
        this.valorTotalComanda = valorTotalComanda;
    }
}
