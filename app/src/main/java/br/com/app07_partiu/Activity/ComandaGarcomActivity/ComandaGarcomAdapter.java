package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;


import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

public class ComandaGarcomAdapter extends BaseAdapter implements SectionIndexer{
    private Activity activity;
    public Item[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public ComandaGarcomAdapter(Item[] itens, Activity activity) {
        this.itens = itens;
        this.activity = activity;
        sectionHeaders = ComandaGarcomSectionIndexBuilder.buildSectionHeaders(itens);
        positionForSectionMap = ComandaGarcomSectionIndexBuilder.buildPositionForSectionMap(itens);
        sectionForPositionMap = ComandaGarcomSectionIndexBuilder.buildSectionForPositionMap(itens);
    }

    @Override
    public int getCount() {
        return itens.length;
    }

    @Override
    public Object getItem(int position) {
        return itens[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Item item  = (Item) getItem(position);
        switch (item.getStatusPedido()){
            case "P":
                return 1;
            case "N":
                return 2;
            case "S":
                return 3;

        }
        return item.getStatusPedido().equals("P") ? 1:0;
    }

    @Override
    public int getViewTypeCount() {
        return itens.length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null) {
            int type = getItemViewType(position);
            view = getInflatedLayoutForType(type, parent);
            TextView textViewDescricao = (TextView) view.findViewById(R.id.textView_itemComandaMesaCliente_descricao);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_itemComandaMesaCliente_valor);
            TextView textViewStatus = (TextView) view.findViewById(R.id.textView_itemComandaMesaCliente_status);
            ComandaGarcomViewHolder viewHolder = new ComandaGarcomViewHolder(textViewDescricao, textViewValor, textViewStatus);
            view.setTag(viewHolder);
        }


        ComandaGarcomViewHolder viewHolder = (ComandaGarcomViewHolder) view.getTag();
        viewHolder.getTextViewDetalhes().setText(itens[position].getNome());
        viewHolder.getTextViewValor().setText(itens[position].getValorString());
        try {

            viewHolder.getTextViewStatus().setText(itens[position].getStatusString());
        } catch (NullPointerException e) {
            Log.e("TESTES", "Erro status pedido de id=" + itens[position].getIdPedido());
            viewHolder.getTextViewStatus().setText("ERRO");
        }
        // ---- item não tem img
        /*Drawable drawable = Util.getDrawable(activity, itens[position].getIcone().toLowerCase());
        if(drawable == null){
            drawable = activity.getDrawable(R.drawable.ic_action_detalhe);
        }
        viewHolder.getImageViewIcon().setImageDrawable(drawable);
        */
        return view;
    }



    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionForSectionMap.get(sectionIndex).intValue();
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionForPositionMap.get(position).intValue();
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent){
        if (type == 1){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.list_item_nome_valor_status_verde, parent, false);
        }else if(type==2){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.list_item_nome_valor_status, parent, false);
        }else{
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.list_item_nome_valor_status_amarelo, parent, false);
        }
    }



}
