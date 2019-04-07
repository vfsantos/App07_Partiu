package br.com.app07_partiu;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    TextView dataEntrada, codigoComanda, valorTotalComanda;

    public ViewHolder(TextView dataEntrada, TextView codigoComanda, TextView valorTotalComanda) {
        this.dataEntrada = dataEntrada;
        this.codigoComanda = codigoComanda;
        this.valorTotalComanda = valorTotalComanda;
    }

    public TextView getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(TextView dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public TextView getCodigoComanda() {
        return codigoComanda;
    }

    public void setCodigoComanda(TextView codigoComanda) {
        this.codigoComanda = codigoComanda;
    }

    public TextView getValorTotalComanda() {
        return valorTotalComanda;
    }

    public void setValorTotalComanda(TextView valorTotalComanda) {
        this.valorTotalComanda = valorTotalComanda;
    }
}
