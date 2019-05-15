package br.com.app07_partiu.Activity.CardapioGarcomActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.app07_partiu.ItemComandaGarcomViewHolder;
import br.com.app07_partiu.Model.ItemCardapio;
import br.com.app07_partiu.Model.ItemCardapioGarcomConvertView;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.R;

public class CardapioGarcomAdapter extends RecyclerView.Adapter{

    private List<ItemCardapioGarcomConvertView> itens;
    private Context context;

    public CardapioGarcomAdapter(List<ItemCardapioGarcomConvertView> itens, Context context) {
        this.itens = itens;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cardapio_garcom, parent, false);
        CardapioGarcomViewHolder holder = new CardapioGarcomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CardapioGarcomViewHolder holder = (CardapioGarcomViewHolder) viewHolder;
        ItemCardapioGarcomConvertView itemCardapioGarcomConvertView = itens.get(position);
        holder.textViewNomeItem.setText(itemCardapioGarcomConvertView.getNomeItem());
        holder.textViewDetalheItem.setText(itemCardapioGarcomConvertView.getDetalheItem());
        holder.textViewValor.setText(itemCardapioGarcomConvertView.getValorItem());

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
