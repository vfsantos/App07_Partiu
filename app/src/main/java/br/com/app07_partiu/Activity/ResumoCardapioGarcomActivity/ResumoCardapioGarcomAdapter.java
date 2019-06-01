package br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

public class ResumoCardapioGarcomAdapter extends BaseAdapter implements SectionIndexer{

    private Activity activity;
    public Item[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


        public ResumoCardapioGarcomAdapter(Item[] itens, Activity activity) {
            this.itens = itens;
            this.activity = activity;
            sectionHeaders = ResumoCardapioGarcomSectionIndexBuilder.buildSectionHeaders(itens);
            positionForSectionMap = ResumoCardapioGarcomSectionIndexBuilder.buildPositionForSectionMap(itens);
            sectionForPositionMap = ResumoCardapioGarcomSectionIndexBuilder.buildSectionForPositionMap(itens);
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
                TextView textViewNome = (TextView) view.findViewById(R.id.textView_list_item_nome);
                TextView textViewValor = (TextView) view.findViewById(R.id.textView_list_item_valor);
                ResumoCardapioGarcomViewHolder viewHolder = new ResumoCardapioGarcomViewHolder(textViewNome, textViewValor);
                view.setTag(viewHolder);
            }


            ResumoCardapioGarcomViewHolder viewHolder = (ResumoCardapioGarcomViewHolder) view.getTag();
            viewHolder.getTextViewNome().setText(itens[position].getNome());
            viewHolder.getTextViewValor().setText(String.valueOf(itens[position].getValorString()));

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
