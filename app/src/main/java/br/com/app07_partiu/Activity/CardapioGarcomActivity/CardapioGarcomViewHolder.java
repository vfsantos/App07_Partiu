package br.com.app07_partiu.Activity.CardapioGarcomActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class CardapioGarcomViewHolder {
    public TextView textViewNomeItem;
    public TextView textViewDetalheItem;
    public TextView textViewValor;


    public CardapioGarcomViewHolder(TextView textViewNomeItem, TextView textViewDetalheItem, TextView textViewValor) {
        this.textViewNomeItem = textViewNomeItem;
        this.textViewDetalheItem = textViewDetalheItem;
        this.textViewValor = textViewValor;
    }


    public TextView getTextViewNomeItem() {
        return textViewNomeItem;
    }

    public void setTextViewNomeItem(TextView textViewNomeItem) {
        this.textViewNomeItem = textViewNomeItem;
    }

    public TextView getTextViewDetalheItem() {
        return textViewDetalheItem;
    }

    public void setTextViewDetalheItem(TextView textViewDetalheItem) {
        this.textViewDetalheItem = textViewDetalheItem;
    }

    public TextView getTextViewValor() {
        return textViewValor;
    }

    public void setTextViewValor(TextView textViewValor) {
        this.textViewValor = textViewValor;
    }
}
