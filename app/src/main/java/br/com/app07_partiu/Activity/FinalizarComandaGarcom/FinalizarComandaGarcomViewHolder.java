package br.com.app07_partiu.Activity.FinalizarComandaGarcom;

import android.widget.TextView;

public class FinalizarComandaGarcomViewHolder {
    public TextView textViewNome;
    public TextView textViewValor;


    public FinalizarComandaGarcomViewHolder(TextView textViewNome, TextView textViewValor) {
        this.textViewNome = textViewNome;
        this.textViewValor = textViewValor;
    }

    public TextView getTextViewNome() {
        return textViewNome;
    }

    public void setTextViewNome(TextView textViewNome) {
        this.textViewNome = textViewNome;
    }

    public TextView getTextViewValor() {
        return textViewValor;
    }

    public void setTextViewValor(TextView textViewValor) {
        this.textViewValor = textViewValor;
    }


}
