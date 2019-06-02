package br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity;

import android.widget.TextView;

public class ItemComandaDetalheClienteViewHolder {
    //TextView
    private TextView textViewNome;
    private TextView textViewValor;
    private TextView textViewStatus;

    public ItemComandaDetalheClienteViewHolder(TextView textViewNome, TextView textViewValor, TextView textViewStatus) {
        this.textViewNome = textViewNome;
        this.textViewValor = textViewValor;
        this.textViewStatus = textViewStatus;
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

    public TextView getTextViewStatus() {
        return textViewStatus;
    }

    public void setTextViewStatus(TextView textViewStatus) {
        this.textViewStatus = textViewStatus;
    }
}
