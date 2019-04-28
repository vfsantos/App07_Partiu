package br.com.app07_partiu.Activity.PedidoSelecaoGarcomActivity;

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

public class PedidoSelecaoGarcomAdapter extends BaseAdapter implements SectionIndexer {
    private ComandaConvertView[] comandas;
    private Activity activity;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public PedidoSelecaoGarcomAdapter(ComandaConvertView[] comandas, Activity activity) {
        this.comandas = comandas;
        this.activity = activity;
        sectionHeaders = PedidoSelecaoGarcomSectionIndexBuilder.buildSectionHeaders(comandas);
        positionForSectionMap = PedidoSelecaoGarcomSectionIndexBuilder.buildPositionForSectionMap(comandas);
        sectionForPositionMap = PedidoSelecaoSectionIndexBuilder.buildSectionForPositionMap(comandas);
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
            view = inflater.inflate(R.layout.itemlistview_pedidoselecao, parent, false);
            TextView textViewProduto = (TextView) view.findViewById(R.id.textView_produto_pedidoSelecao);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_valor_pedidoSelecao);
            HomeGarcomViewHolder viewHolder = new HomeGarcomViewHolder(textViewProduto, textViewValor);
            view.setTag(viewHolder);
        }

        PedidoSelecaoViewHolder viewHolder = (PedidoSlecaoViewHolder)view.getTag();
        viewHolder.getTextViewCodigoComanda().setText(comandas[position].getCodigoComanda());
        viewHolder.getTextViewTotalComandaValor().setText(comandas[position].getValorTotalComanda());
        viewHolder.getTextViewMesaNumero().setText(comandas[position].getMesa());


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
