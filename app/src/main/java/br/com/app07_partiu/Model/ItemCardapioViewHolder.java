package br.com.app07_partiu.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemCardapioViewHolder extends RecyclerView.ViewHolder{

    public TextView item;
    public TextView preco;
    public TextView quantidade;
    public TextView status;

    public ItemCardapioViewHolder(View view) {
        super(view);
        //TODO R.id.item_cardapio_*
        item = (TextView) view.findViewById(R.id.item_item);
        preco = (TextView) view.findViewById(R.id.item_preco);
        quantidade = (TextView) view.findViewById(R.id.item_quantidade);
        status = (TextView) view.findViewById(R.id.item_status);
    }
}