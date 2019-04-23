package br.com.app07_partiu;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    public TextView textViewCodigoComanda;
    public TextView textViewTotalComanda;
    public TextView textViewTotalComandaValor;
    public TextView textViewPessoasComanda;
    public TextView textViewPessoasComandaGarcomNumero;
    public TextView textViewMesa;
    public TextView textViewMesaNumero;
    public TextView textViewHora;


    public ViewHolder(TextView textViewCodigoComanda, TextView textViewTotalComanda, TextView textViewTotalComandaValor,
                      TextView textViewPessoasComanda, TextView textViewPessoasComandaGarcomNumero,
                      TextView textViewMesa, TextView textViewMesaNumero, TextView textViewHora) {
        this.textViewCodigoComanda = textViewCodigoComanda;
        this.textViewTotalComanda = textViewTotalComanda;
        this.textViewTotalComandaValor = textViewTotalComandaValor;
        this.textViewPessoasComanda = textViewPessoasComanda;
        this.textViewPessoasComandaGarcomNumero = textViewPessoasComandaGarcomNumero;
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

    public TextView getTextViewTotalComanda() {
        return textViewTotalComanda;
    }

    public void setTextViewTotalComanda(TextView textViewTotalComanda) {
        this.textViewTotalComanda= textViewTotalComanda;
    }

    public TextView getTextViewTotalComandaValor() {
        return textViewTotalComandaValor;
    }

    public void setTextViewTotalComandaValor(TextView textViewTotalComandaValor) {
        this.textViewCodigoComanda = textViewTotalComandaValor;
    }

    public TextView getTextViewPessoasComanda() {
        return textViewPessoasComanda;
    }

    public void setTextViewPessoasComanda(TextView textViewPessoasComanda) {
        this.textViewPessoasComanda = textViewPessoasComanda;
    }

    public TextView getTextViewPessoasComandaGarcomNumero() {
        return textViewPessoasComandaGarcomNumero;
    }

    public void setTextViewPessoasComandaGarcomNumero(TextView textViewPessoasComandaGarcomNumero) {
        this.textViewPessoasComandaGarcomNumero = textViewPessoasComandaGarcomNumero;
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
