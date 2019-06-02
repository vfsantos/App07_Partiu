package br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity;

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
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class ItemComandaDetalheClienteAdapter extends BaseAdapter implements SectionIndexer {

    private Activity activity;
    private Usuario[] usuarios;
    private double valorItem;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;


    public ItemComandaDetalheClienteAdapter(Usuario[] usuarios, double valorItem, Activity activity) {
        this.usuarios = usuarios;
        this.activity = activity;
        this.valorItem = valorItem;
        sectionHeaders = ItemComandaDetalheClienteSectionIndexBuilder.buildSectionHeaders(usuarios);
        positionForSectionMap = ItemComandaDetalheClienteSectionIndexBuilder.buildPositionForSectionMap(usuarios);
        sectionForPositionMap = ItemComandaDetalheClienteSectionIndexBuilder.buildSectionForPositionMap(usuarios);
    }

    @Override
    public int getCount() {
        return usuarios.length;
    }

    @Override
    public Object getItem(int position) {
        return usuarios[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_nome_valor, parent, false);
            TextView textViewNome = (TextView) view.findViewById(R.id.textView_itemComandaCliente_nome);
            TextView textViewValor = (TextView) view.findViewById(R.id.textView_itemComandaCliente_valor);
            TextView textViewStatus = (TextView) view.findViewById(R.id.textView_itemComandaCliente_status);

            ItemComandaDetalheClienteViewHolder viewHolder = new ItemComandaDetalheClienteViewHolder(textViewNome, textViewValor, textViewStatus);
            view.setTag(viewHolder);
        }


        ItemComandaDetalheClienteViewHolder viewHolder = (ItemComandaDetalheClienteViewHolder) view.getTag();
        viewHolder.getTextViewNome().setText(usuarios[position].getNome());

        //TODO atualizar para aceitar porcentagem
        viewHolder.getTextViewValor().setText(""+(doubleToReal(valorItem*usuarios[position].getPorcPedido()/100)));
//        viewHolder.getTextViewValor().setText("" + (doubleToReal(valorItem/usuarios.length)));
        try {
            if (usuarios[position].getStatusPedido().equals("N")) {
                viewHolder.getTextViewStatus().setText("Não Pago");
            } else {
                viewHolder.getTextViewStatus().setText("Pago");
            }

        } catch (NullPointerException e) {
            viewHolder.getTextViewStatus().setText("NO STATUS");
            Log.e("TESTES", "Usuário id:" + usuarios[position].getId() + " nome:" + usuarios[position].getNome() + " ; Não possui status em usuario_pedido");
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
