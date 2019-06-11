package br.com.app07_partiu.Activity.FinalizarComandaGarcom;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Hashtable;

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class FinalizarComandaGarcomAdapter extends BaseAdapter{

    private Activity activity;
    public Item[] itens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


        public FinalizarComandaGarcomAdapter(Item[] itens, Activity activity) {
            this.itens = itens;
            this.activity = activity;
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
                view = inflater.inflate(R.layout.list_item_nome_valor_, parent, false);
                TextView textViewNome = (TextView) view.findViewById(R.id.textView_list_item_nome);
                TextView textViewValor = (TextView) view.findViewById(R.id.textView_list_item_valor);
                FinalizarComandaGarcomViewHolder viewHolder = new FinalizarComandaGarcomViewHolder(textViewNome, textViewValor);
                view.setTag(viewHolder);
            }


            double valor = 0;
            if (itens[position].getUsuariosPedido().size() > 0) {
                double aPagar = 100;
                for (Usuario u : itens[position].getUsuariosPedido()) {
                    if (u.getStatusPedido().equals("P")) {
                        aPagar -= u.getPorcPedido();
                    }
                }

                valor += itens[position].getValor()*aPagar/100;

            } else {
                valor += itens[position].getValor();
            }
            FinalizarComandaGarcomViewHolder viewHolder = (FinalizarComandaGarcomViewHolder) view.getTag();
            viewHolder.getTextViewNome().setText(itens[position].getNome());
            viewHolder.getTextViewValor().setText(doubleToReal(valor));

            return view;
        }

}
