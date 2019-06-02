package br.com.app07_partiu.Activity.ComandaMesaCliente;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;


import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

public class ComandaMesaClienteAdapter extends BaseAdapter implements SectionIndexer {
    private Activity activity;
    public Item[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public ComandaMesaClienteAdapter(Item[] itens, Activity activity) {
        this.itens = itens;
        this.activity = activity;
        sectionHeaders = ComandaMesaClienteSectionIndexBuilder.buildSectionHeaders(itens);
        positionForSectionMap = ComandaMesaClienteSectionIndexBuilder.buildPositionForSectionMap(itens);
        sectionForPositionMap = ComandaMesaClienteSectionIndexBuilder.buildSectionForPositionMap(itens);
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
            view = inflater.inflate(R.layout.list_item_nome_valor_status, parent, false);
            TextView textViewDescricao = (TextView) view.findViewById(R.id.textView_itemComandaMesaCliente_descricao);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_itemComandaMesaCliente_valor);
            TextView textViewStatus = (TextView) view.findViewById(R.id.textView_itemComandaMesaCliente_status);
            ComandaMesaClienteViewHolder viewHolder = new ComandaMesaClienteViewHolder(textViewDescricao, textViewValor, textViewStatus);
            view.setTag(viewHolder);
        }


        ComandaMesaClienteViewHolder viewHolder = (ComandaMesaClienteViewHolder) view.getTag();
        viewHolder.getTextViewDetalhes().setText(itens[position].getNome());
        viewHolder.getTextViewValor().setText(itens[position].getValorString());
        try{

            viewHolder.getTextViewStatus().setText(itens[position].getStatusString());
        }catch(NullPointerException e){
            Log.e("TESTES", "Erro status pedido de id="+itens[position].getIdPedido());
            viewHolder.getTextViewStatus().setText("ERRO");
        }
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
