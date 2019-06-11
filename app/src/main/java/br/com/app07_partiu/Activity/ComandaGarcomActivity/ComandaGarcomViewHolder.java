package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Model.ComandaConvertView;

public class ComandaGarcomViewHolder {
    public TextView textViewDetalhes;
    public TextView textViewValor;
    public TextView textViewStatus;


    public ComandaGarcomViewHolder(TextView detalhe, TextView valor, TextView status) {
        this.textViewDetalhes = detalhe;
        this.textViewValor = valor;
        this.textViewStatus = status;
    }

    public TextView getTextViewDetalhes() {
        return textViewDetalhes;
    }

    public void setTextViewDetalhes(TextView textViewDetalhes) {
        this.textViewDetalhes = textViewDetalhes;
    }

    public TextView getTextViewValor() {
        return textViewValor;
    }

    public void setTextViewValor(TextView textViewValor) {
        this.textViewValor = textViewValor;
    }

    public TextView getTextViewStatus() {
        return textViewStatus;
    }

    public void setTextViewStatus(TextView textViewStatus) {
        this.textViewStatus = textViewStatus;
    }
}