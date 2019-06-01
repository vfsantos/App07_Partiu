package br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity;

import android.widget.TextView;

public class ItemComandaDetalheClienteViewHolder {
    //TextView
    private TextView textViewNome;
    private TextView textViewValor;

    public ItemComandaDetalheClienteViewHolder(TextView textViewNome, TextView textViewValor) {
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
