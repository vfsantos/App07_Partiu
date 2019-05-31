package br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity;

import android.widget.TextView;

public class ResumoCardapioGarcomViewHolder {
    public TextView textViewNome;
    public TextView textViewValor;


    public ResumoCardapioGarcomViewHolder(TextView textViewNome, TextView textViewValor) {
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
