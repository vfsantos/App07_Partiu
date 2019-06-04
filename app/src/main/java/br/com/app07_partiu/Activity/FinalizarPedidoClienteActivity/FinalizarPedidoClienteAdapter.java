package br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomSectionIndexBuilder;
import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomViewHolder;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

public class FinalizarPedidoClienteAdapter extends BaseAdapter implements SectionIndexer {

    private Activity activity;
    public Item[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public FinalizarPedidoClienteAdapter(Item[] itens, Activity activity) {
        this.itens = itens;
        this.activity = activity;
        sectionHeaders = FinalizarPedidoClienteSectionIndexBuilder.buildSectionHeaders(itens);
        positionForSectionMap = FinalizarPedidoClienteSectionIndexBuilder.buildPositionForSectionMap(itens);
        sectionForPositionMap = FinalizarPedidoClienteSectionIndexBuilder.buildSectionForPositionMap(itens);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_nome_valor_icone, parent, false);
            TextView textViewDescricao = (TextView) view.findViewById(R.id.textView_list_item_nome);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_list_item_valor);
            ImageView imageViewIcon = (ImageView) view.findViewById(R.id.imageView_list_item_icon);
            FinalizarPedidoClienteViewHolder viewHolder = new FinalizarPedidoClienteViewHolder(textViewDescricao, textViewValor, imageViewIcon);
            view.setTag(viewHolder);
        }


        FinalizarPedidoClienteViewHolder viewHolder = (FinalizarPedidoClienteViewHolder) view.getTag();
        viewHolder.getTextViewDetalhes().setText(itens[position].getNome());
        viewHolder.getTextViewValor().setText(itens[position].getValorString());
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

}
