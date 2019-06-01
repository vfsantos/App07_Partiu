package br.com.app07_partiu.Activity.HomeGarcomActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;

import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.R;

public class HomeGarcomAdapter extends BaseAdapter implements SectionIndexer {
    private ComandaConvertView[] comandas;
    private Activity activity;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public HomeGarcomAdapter(ComandaConvertView[] comandas, Activity activity) {
        this.comandas = comandas;
        this.activity = activity;
        sectionHeaders = HomeGarcomSectionIndexBuilder.buildSectionHeaders(comandas);
        positionForSectionMap = HomeGarcomSectionIndexBuilder.buildPositionForSectionMap(comandas);
        sectionForPositionMap = HomeGarcomSectionIndexBuilder.buildSectionForPositionMap(comandas);
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
            view = inflater.inflate(R.layout.list_item_codigo_horario, parent, false);
            TextView textViewCodigoComanda = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_codigo_comanda);
            TextView textViewMesa          = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_mesa);
            TextView textViewMesaNumero    = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_mesa_numero);
            TextView textViewHora          = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_hora);
            HomeGarcomViewHolder viewHolder = new HomeGarcomViewHolder(textViewCodigoComanda, textViewMesa, textViewMesaNumero, textViewHora);

            view.setTag(viewHolder);
        }



        HomeGarcomViewHolder viewHolder = (HomeGarcomViewHolder)view.getTag();
        viewHolder.getTextViewCodigoComanda().setText(comandas[position].getCodigoComanda());
        viewHolder.getTextViewMesaNumero().setText(comandas[position].getMesa());
        //viewHolder.getTextViewHora().setText(comandas[position].getDataEntrada().split(" ")[1].replace(":","h"));


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
