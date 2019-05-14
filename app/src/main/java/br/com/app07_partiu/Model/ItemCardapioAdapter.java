package br.com.app07_partiu.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ItemCardapioAdapter extends RecyclerView.Adapter {

    private List<ItemCardapio> items;
    private Context context;

    public ItemCardapioAdapter(List<ItemCardapio> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comanda, parent, false);
        ItemCardapioViewHolder holder = new ItemCardapioViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position) {
        ItemCardapioViewHolder holder = (ItemCardapioViewHolder) viewHolder;
        ItemCardapio ItemCardapio  = items.get(position) ;
        holder.item.setText(ItemCardapio.getItem());
        holder.preco.setText(ItemCardapio.getPreco());
        holder.quantidade.setText(ItemCardapio.getQuantidade());
        holder.status.setText(ItemCardapio.getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
