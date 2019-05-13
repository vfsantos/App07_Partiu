package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;


import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.R;

public class ComandaGarcomAdapter extends BaseAdapter implements SectionIndexer{
    private Activity activity;
    public ItemComandaGarcomConvertView[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public ComandaGarcomAdapter(ItemComandaGarcomConvertView[] itens, Activity activity) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_comanda_garcom, parent, false);
            TextView textViewDescricao = (TextView) view.findViewById(R.id.textView_itemComandaGarcom_descricao);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_itemComandaGarcom_valor);
            ImageView imageViewIcon = (ImageView) view.findViewById(R.id.imageView_itemComandaGarcom_icon);
            ComandaGarcomViewHolder viewHolder = new ComandaGarcomViewHolder(textViewDescricao, textViewValor, imageViewIcon);
            view.setTag(viewHolder);
        }


        ComandaGarcomViewHolder viewHolder = (ComandaGarcomViewHolder) view.getTag();
        viewHolder.getTextViewDetalhes().setText(String.format(itens[position].getQuantidade()+"x ", itens[position].getDescricao()));
        viewHolder.getTextViewValor().setText(String.valueOf(itens[position].getValor()));
        Drawable drawable = Util.getDrawable(activity, itens[position].getIcone().toLowerCase());
        if(drawable == null){
            drawable = activity.getDrawable(R.drawable.ic_action_detalhe);
        }
        viewHolder.getImageViewIcon().setImageDrawable(drawable);
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
