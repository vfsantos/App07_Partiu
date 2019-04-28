package br.com.app07_partiu.Activity.PedidoSelecaoGarcomActivity;

import android.widget.TextView;

public class PedidoSelecaoGarcomViewHolder {

    public TextView textViewProduto;
    public TextView textViewValor;


    public PedidoSelecaoGarcomViewHolder(TextView textViewProduto, TextView textViewValor) {
        this.textViewProduto = textViewProduto;
        this.textViewValor = textViewValor;
    }

    public TextView getTextViewProduto() {
        return textViewProduto;
    }

    public void setTextViewProduto(TextView textViewProduto) {
        this.textViewProduto = textViewProduto;
    }

    public TextView getTextViewValor() {
        return textViewValor;
    }

    public void setTextViewValor(TextView textViewValor) {
        this.textViewValor= textViewValor;
    }


}
