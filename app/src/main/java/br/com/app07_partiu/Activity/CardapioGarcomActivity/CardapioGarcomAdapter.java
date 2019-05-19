package br.com.app07_partiu.Activity.CardapioGarcomActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import java.util.Hashtable;
import br.com.app07_partiu.Model.ItemCardapioGarcomConvertView;
import br.com.app07_partiu.R;

public class CardapioGarcomAdapter extends BaseAdapter implements SectionIndexer {

    private Activity activity;
    public ItemCardapioGarcomConvertView[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public CardapioGarcomAdapter(ItemCardapioGarcomConvertView[] itens, Activity activity) {
        this.itens = itens;
        this.activity = activity;
        sectionHeaders = CardapioGarcomSectionIndexBuilder.buildSectionHeaders(itens);
        positionForSectionMap = CardapioGarcomSectionIndexBuilder.buildPositionForSectionMap(itens);
        sectionForPositionMap = CardapioGarcomSectionIndexBuilder.buildSectionForPositionMap(itens);
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
            view = inflater.inflate(R.layout.item_cardapio_garcom, parent, false);
            TextView textViewNomeItem = (TextView) view.findViewById(R.id.textView_cardapioGarcom_nomeItem);
            TextView textViewDescricaoItem = (TextView) view.findViewById(R.id.textView_cardapioGarcom_descricaoItem);
            TextView textViewValorItem = (TextView) view.findViewById(R.id.textView_cardapioGarcom_valorItem);

            CardapioGarcomViewHolder viewHolder = new CardapioGarcomViewHolder(textViewNomeItem, textViewDescricaoItem, textViewValorItem);
            view.setTag(viewHolder);
        }


        CardapioGarcomViewHolder viewHolder = (CardapioGarcomViewHolder) view.getTag();
        viewHolder.getTextViewNomeItem().setText(itens[position].getNomeItem());
        viewHolder.getTextViewValor().setText(String.valueOf(itens[position].getValorItem()));
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
