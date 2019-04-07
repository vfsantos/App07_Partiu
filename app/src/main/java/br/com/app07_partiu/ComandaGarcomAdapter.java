package br.com.app07_partiu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;

import br.com.app07_partiu.Model.Comanda;

public class ComandaGarcomAdapter extends BaseAdapter implements SectionIndexer {
    private Comanda[] comandas;
    private Activity activity;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public ComandaGarcomAdapter(Comanda[] comandas, Activity activity) {
        this.comandas = comandas;
        this.activity = activity;
        sectionHeaders = SectionIndexBuilder.buildSectionHeaders(comandas);
        positionForSectionMap = SectionIndexBuilder.buildPositionForSectionMap(comandas);
        sectionForPositionMap = SectionIndexBuilder.buildSectionForPositionMap(comandas);
    }

    @Override
    public int getCount() {
        return comandas.length;
    }

    @Override
    public Object getItem(int position) {
        return comandas[position];
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
            view = inflater.inflate(R.layout.linha_comanda_garcom, parent, false);
            TextView textViewDataEntrada = (TextView) view.findViewById(R.id.text_view_data_entrada);
            TextView textViewCodigoComanda = (TextView) view.findViewById(R.id.text_view_codigo_comanda);
            TextView textViewValorTotalComanda = (TextView) view.findViewById(R.id.text_view_valor_total_comanda);
            ViewHolder viewHolder = new ViewHolder(textViewDataEntrada, textViewCodigoComanda, textViewValorTotalComanda);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.getDataEntrada().setText(String.format("",comandas[position].getDataEntrada()));
        viewHolder.getCodigoComanda().setText(comandas[position].getCodigoComanda());
        viewHolder.getValorTotalComanda().setText(String.format("Total da comanda R$ ",comandas[position].getValorTotalComanda()));


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
