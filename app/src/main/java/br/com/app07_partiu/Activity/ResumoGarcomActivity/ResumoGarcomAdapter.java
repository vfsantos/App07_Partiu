package br.com.app07_partiu.Activity.ResumoGarcomActivity;

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

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

public class ResumoGarcomAdapter extends BaseAdapter implements SectionIndexer{

    private Activity activity;
    public Item[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


        public ResumoGarcomAdapter(Item[] itens, Activity activity) {
            this.itens = itens;
            this.activity = activity;
            sectionHeaders = ResumoGarcomSectionIndexBuilder.buildSectionHeaders(itens);
            positionForSectionMap = ResumoGarcomSectionIndexBuilder.buildPositionForSectionMap(itens);
            sectionForPositionMap = ResumoGarcomSectionIndexBuilder.buildSectionForPositionMap(itens);
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
                ResumoGarcomViewHolder viewHolder = new ResumoGarcomViewHolder(textViewDescricao, textViewValor, imageViewIcon);
                view.setTag(viewHolder);
            }


            ResumoGarcomViewHolder viewHolder = (ResumoGarcomViewHolder) view.getTag();
            viewHolder.getTextViewDetalhes().setText(itens[position].getNome());
            viewHolder.getTextViewValor().setText(String.valueOf(itens[position].getValor()));

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
