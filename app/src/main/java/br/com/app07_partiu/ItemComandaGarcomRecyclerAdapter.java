package br.com.app07_partiu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.app07_partiu.Model.Comanda;

public class ItemComandaGarcomRecyclerAdapter extends RecyclerView.Adapter<ItemComandaGarcomViewHolder> {

    private List<Comanda> list;

    public ItemComandaGarcomRecyclerAdapter(List<Comanda> list){
        this.list = list;
    }

    @Override
    public ItemComandaGarcomViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_codigo_horario, parent, false);

        return new ItemComandaGarcomViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ItemComandaGarcomViewHolder holder, int position) {

        Comanda comanda = list.get(position);
        holder.textViewCodigoComanda.setText(comanda.getCodigoComanda());
        holder.textViewTotalComandaValor.setText((int) comanda.getValorTotalComanda());
        holder.textViewMesaNumero.setText(comanda.getMesa());
        //holder.textViewPessoasComandaGarcomNumero.setText(comanda);

    }
}
