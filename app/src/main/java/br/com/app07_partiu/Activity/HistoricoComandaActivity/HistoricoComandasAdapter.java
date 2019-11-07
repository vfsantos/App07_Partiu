package br.com.app07_partiu.Activity.HistoricoComandaActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Hashtable;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomSectionIndexBuilder;
import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomViewHolder;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteSectionIndexBuilder;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteViewHolder;
import br.com.app07_partiu.Activity.FinalizarComandaGarcom.FinalizarComandaGarcomViewHolder;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomViewHolder;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Estabelecimento;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class HistoricoComandasAdapter extends BaseAdapter implements SectionIndexer {

    private Activity activity;
    private Comanda[] comandas;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public HistoricoComandasAdapter(Comanda[] comandas, Activity activity) {
        this.comandas         = comandas;
        this.activity         = activity;
        sectionHeaders        = HistoricoComandasSectionIndexBuilder.buildSectionHeaders(comandas);
        positionForSectionMap = HistoricoComandasSectionIndexBuilder.buildPositionForSectionMap(comandas);
        sectionForPositionMap = HistoricoComandasSectionIndexBuilder.buildSectionForPositionMap(comandas);
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
    public int getItemViewType(int position) {
        Comanda comanda  = (Comanda) getItem(position);
        switch (comanda.getStatus()){
            case "F":
                return 1;
            case "A":
                return 2;

        }
        return comanda.getStatus().equals("F") ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return comandas.length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null) {
            int type = getItemViewType(position);
            view = getInflatedLayoutForType(type, parent);
            TextView textVieCodigoComanda           = (TextView) view.findViewById(R.id.textView_list_item_historico_comanda_codigo);
            TextView textViewNomeEstabelecimento    = (TextView) view.findViewById(R.id.textView_list_item_historico_comanda_nome_estabelecimento);
            TextView textViewStatus                 = (TextView) view.findViewById(R.id.textView_list_item_historico_comanda_status);
            HistoricoComandasViewHolder viewHolder  = new HistoricoComandasViewHolder(textVieCodigoComanda, textViewNomeEstabelecimento, textViewStatus);
            view.setTag(viewHolder);
        }


        HistoricoComandasViewHolder viewHolder = (HistoricoComandasViewHolder) view.getTag();
        viewHolder.getTextViewCodigoDaComanda().setText("Comanda: " + comandas[position].getCodigoComanda());
        viewHolder.getTextViewNomeEstabelecimento().setText(comandas[position].getNomeEstabelecimento());
        try {
            if(comandas[position].getStatus().equals("F")){
                viewHolder.getTextViewStatusComanda().setText("Status: Fechada");
            } else {
                viewHolder.getTextViewStatusComanda().setText("Status: Aberta");
            }
        } catch (NullPointerException e) {
            Log.e("TESTES", "Erro status comanda de id=" + comandas[position].getId());
        }
        // ---- item não tem img
        //Drawable drawable = Util.getDrawable(activity, itens[position].getIcone().toLowerCase());
        //if(drawable == null){
        //    drawable = activity.getDrawable(R.drawable.ic_action_detalhe);
        //}
        //viewHolder.getImageViewIcon().setImageDrawable(drawable);

        return view;
    }

/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_codigo_nome_valor_icone_default, parent, false);
            TextView textVieCodigoComanda           = (TextView) view.findViewById(R.id.textView_list_item_historico_comanda_codigo);
            TextView textViewNomeEstabelecimento    = (TextView) view.findViewById(R.id.textView_list_item_historico_comanda_nome_estabelecimento);
            TextView textViewStatus                 = (TextView) view.findViewById(R.id.textView_list_item_historico_comanda_status);
            HistoricoComandasViewHolder viewHolder = new HistoricoComandasViewHolder(textVieCodigoComanda, textViewNomeEstabelecimento, textViewStatus);

            view.setTag(viewHolder);
        }


        HistoricoComandasViewHolder viewHolder = (HistoricoComandasViewHolder) view.getTag();
        viewHolder.getTextViewCodigoDaComanda().setText("Comanda: " + comandas[position].getCodigoComanda());
        viewHolder.getTextViewNomeEstabelecimento().setText(comandas[position].getNomeEstabelecimento());
        if(comandas[position].getStatus().equals("F")){
            viewHolder.getTextViewStatusComanda().setText("Status: Fechada");
        } else {
            viewHolder.getTextViewStatusComanda().setText("Status: Aberta");
        }

        return view;
    }*/



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



    private View getInflatedLayoutForType(int type, ViewGroup parent){
        if (type == 1){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.list_item_codigo_nome_valor_icone_default, parent, false);
        }else {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.list_item_codigo_nome_valor_icone, parent, false);
        }
    }

}



