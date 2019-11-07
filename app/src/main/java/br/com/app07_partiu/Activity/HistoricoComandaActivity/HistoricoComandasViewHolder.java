package br.com.app07_partiu.Activity.HistoricoComandaActivity;

import android.widget.TextView;

public class HistoricoComandasViewHolder {

    public TextView textViewCodigoDaComanda;
    public TextView textViewNomeEstabelecimento;
    public TextView textViewStatusComanda;


    public HistoricoComandasViewHolder(TextView codigoDaComanda, TextView nomeEstabelecimento, TextView statusComanda) {
        this.textViewCodigoDaComanda = codigoDaComanda;
        this.textViewNomeEstabelecimento = nomeEstabelecimento;
        this.textViewStatusComanda = statusComanda;
    }

    public TextView getTextViewCodigoDaComanda() {
        return textViewCodigoDaComanda;
    }

    public void setTextViewCodigoDaComanda(TextView textViewCodigoDaComanda) {
        this.textViewCodigoDaComanda = textViewCodigoDaComanda;
    }

    public TextView getTextViewNomeEstabelecimento() {
        return textViewNomeEstabelecimento;
    }

    public void setTextViewNomeEstabelecimento(TextView textViewNomeEstabelecimento) {
        this.textViewNomeEstabelecimento = textViewNomeEstabelecimento;
    }

    public TextView getTextViewStatusComanda() {
        return textViewStatusComanda;
    }

    public void setTextViewStatusComanda(TextView textViewStatusComanda) {
        this.textViewStatusComanda = textViewStatusComanda;
    }
}
