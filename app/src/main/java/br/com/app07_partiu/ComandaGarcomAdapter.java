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
import br.com.app07_partiu.Model.ComandaConvertView;

public class ComandaGarcomAdapter extends BaseAdapter implements SectionIndexer {
    private ComandaConvertView[] comandas;
    private Activity activity;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public ComandaGarcomAdapter(ComandaConvertView[] comandas, Activity activity) {
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
            view = inflater.inflate(R.layout.item_comanda_garcom, parent, false);
            TextView textViewCodigoComanda = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_codigo_comanda);
            TextView textViewTotalComanda = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_total_comanda);
            TextView textViewTotalComandaValor = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_tota_comanda_valor);
            TextView textViewPessoasComanda = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_pessoas_comanda);
            TextView textViewPessoasComandaGarcomNumero = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_pessoas_comanda_numero);
            TextView textViewMesa = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_mesa);
            TextView textViewMesaNumero = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_mesa_numero);
            TextView textViewHora = (TextView) view.findViewById(R.id.text_view_garcom_comanda_item_hora);
            ViewHolder viewHolder = new ViewHolder(textViewCodigoComanda, textViewTotalComanda,textViewTotalComandaValor,
                    textViewPessoasComanda, textViewPessoasComandaGarcomNumero, textViewMesa, textViewMesaNumero, textViewHora);
            view.setTag(viewHolder);
        }



        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.getTextViewCodigoComanda().setText(comandas[position].getCodigoComanda());
        viewHolder.getTextViewTotalComandaValor().setText(comandas[position].getValorTotalComanda());
        viewHolder.getTextViewMesaNumero().setText(comandas[position].getMesa());
        viewHolder.getTextViewPessoasComandaGarcomNumero().setText(comandas[position].getPessoasComanda());
        viewHolder.getTextViewHora().setText(comandas[position].getDataEntrada());


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
