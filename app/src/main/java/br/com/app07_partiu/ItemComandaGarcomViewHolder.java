package br.com.app07_partiu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class ItemComandaGarcomViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewCodigoComanda;
    public TextView textViewTotalComanda;
    public TextView textViewTotalComandaValor;
    public TextView textViewPessoasComanda;
    public TextView textViewPessoasComandaGarcomNumero;
    public TextView textViewMesa;
    public TextView textViewMesaNumero;
    public TextView textViewHora;


    public ItemComandaGarcomViewHolder(View itemView) {
        super(itemView);

        this.textViewCodigoComanda = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_codigo_comanda);
//        this.textViewTotalComanda = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_total_comanda);
//        this.textViewTotalComandaValor = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_tota_comanda_valor);
//        this.textViewPessoasComanda = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_pessoas_comanda);
//        this.textViewPessoasComandaGarcomNumero = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_pessoas_comanda_numero);
        this.textViewMesa = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_mesa);
        this.textViewMesaNumero = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_mesa_numero);
        this.textViewHora = (TextView) itemView.findViewById(R.id.text_view_garcom_comanda_item_hora);
    }
}
