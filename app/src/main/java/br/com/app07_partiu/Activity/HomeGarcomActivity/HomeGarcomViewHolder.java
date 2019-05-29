package br.com.app07_partiu.Activity.HomeGarcomActivity;

import android.widget.ImageView;
import android.widget.TextView;

public class HomeGarcomViewHolder {
    public TextView textViewCodigoComanda;
    public TextView textViewMesa;
    public TextView textViewMesaNumero;
    public TextView textViewHora;


    public HomeGarcomViewHolder(TextView textViewCodigoComanda, TextView textViewMesa, TextView textViewMesaNumero, TextView textViewHora) {
        this.textViewCodigoComanda = textViewCodigoComanda;
        this.textViewMesa = textViewMesa;
        this.textViewMesaNumero = textViewMesaNumero;
        this.textViewHora = textViewHora;
    }

    public TextView getTextViewCodigoComanda() {
        return textViewCodigoComanda;
    }

    public void setTextViewCodigoComanda(TextView textViewCodigoComanda) {
        this.textViewCodigoComanda = textViewCodigoComanda;
    }
    public TextView getTextViewMesa() {
        return textViewMesa;
    }

    public void setTextViewMesa(TextView textViewMesa) {
        this.textViewMesa = textViewMesa;
    }

    public TextView getTextViewMesaNumero() {
        return textViewMesaNumero;
    }

    public void setTextViewMesaNumero(TextView textViewMesaNumero) {
        this.textViewMesaNumero = textViewMesaNumero;
    }

    public TextView getTextViewHora() {
        return textViewHora;
    }

    public void setTextViewHora(TextView textViewHora) {
        this.textViewHora = textViewHora;
    }
}
