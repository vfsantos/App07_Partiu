package br.com.app07_partiu.Activity.CardapioGarcomActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class CardapioGarcomViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewNomeItem;
    public TextView textViewDetalheItem;
    public TextView textViewValor;

    public CardapioGarcomViewHolder(View view) {
        super(view);
        textViewNomeItem = (TextView) view.findViewById(R.id.textView_cardapioGarcom_nomeItem);
        textViewDetalheItem =(TextView) view.findViewById(R.id.textView_cardapioGarcom_descricaoItem);
        textViewValor = (TextView) view.findViewById(R.id.textView_cardapioGarcom_valorItem);
    }
}
