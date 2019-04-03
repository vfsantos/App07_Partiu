package br.com.app07_partiu;

import java.util.Date;

public class ComandaModel {
    private Date dataHoraEntrada;
    private int numeroDaComanda;
    private double valorTotalComanda;

    public ComandaModel(Date dataHoraEntrada, int numeroDaComanda, double valorTotalComanda) {
        this.dataHoraEntrada = dataHoraEntrada;
        this.numeroDaComanda = numeroDaComanda;
        this.valorTotalComanda = valorTotalComanda;
    }

    public Date getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(Date dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public int getNumeroDaComanda() {
        return numeroDaComanda;
    }

    public void setNumeroDaComanda(int numeroDaComanda) {
        this.numeroDaComanda = numeroDaComanda;
    }

    public double getValorTotalComanda() {
        return valorTotalComanda;
    }

    public void setValorTotalComanda(double valorTotalComanda) {
        this.valorTotalComanda = valorTotalComanda;
    }
}
