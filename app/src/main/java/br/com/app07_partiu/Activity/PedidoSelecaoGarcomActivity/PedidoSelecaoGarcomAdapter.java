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

import br.com.app07_partiu.Model.ItemConvertView;
import br.com.app07_partiu.R;

public class PedidoSelecaoGarcomAdapter extends BaseAdapter implements SectionIndexer {
    private ItemConvertView[] itens;
    private Activity activity;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public PedidoSelecaoGarcomAdapter(ItemConvertView[] itens, Activity activity) {
        this.itens = itens;
        this.activity = activity;
        sectionHeaders = PedidoSelecaoGarcomSectionIndexBuilder.buildSectionHeaders(itens);
        positionForSectionMap = PedidoSelecaoGarcomSectionIndexBuilder.buildPositionForSectionMap(itens);
        sectionForPositionMap = PedidoSelecaoGarcomSectionIndexBuilder.buildSectionForPositionMap(itens);
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
            view = inflater.inflate(R.layout.itemlistview_pedidoselecao, parent, false);
            TextView textViewProduto = (TextView) view.findViewById(R.id.textView_produto_pedidoSelecao);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_valor_pedidoSelecao);
            PedidoSelecaoGarcomViewHolder viewHolder = new PedidoSelecaoGarcomViewHolder(textViewProduto, textViewValor);
            view.setTag(viewHolder);
        }

        PedidoSelecaoGarcomViewHolder viewHolder = (PedidoSelecaoGarcomViewHolder)view.getTag();
        viewHolder.getTextViewProduto().setText(itens[position].getNome());
        viewHolder.getTextViewValor().setText(itens[position].getValor());


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
