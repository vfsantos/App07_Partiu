package br.com.app07_partiu.Activity.ComandaMesaCliente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
import br.com.app07_partiu.Activity.PerfilClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class ComandaMesaClienteActivity extends AppCompatActivity {

    //TextView
    private TextView textViewTituloPagina;
    private TextView textViewItemCodigoComanda;
    private TextView textViewItemTotalComanda;
    private TextView textViewItemTotalComandaValor;
    private TextView textViewItemPessoaComanda;
    private TextView textViewItemPessoaComandaNumero;
    private TextView textViewItemMesa;
    private TextView textViewItemMesaNumero;
    private TextView textViewItemHora;
    private TextView textViewItensDaComanda;


    public static final String ITEM = "br.com.app07_partiu.ComandaMesaClienteActivity.item";


    //ListView
    private ListView listViewItensComanda;


    //Itent
    private Intent intent;
    private Intent intentItem;


    //Objeto
    private Comanda comanda;
    private ComandaConvertView convertedComanda;
    private ComandaConvertView comandaConvertView;
    private Restaurante restaurante;


    //Array
    public Item[] itens;
    public Item[] itensFormatados;


    private Context context;
    private Double valorTotalComanda = 0.0;


    //Intent
    private Intent intentPedidoSelecaoGarcom;


    //BottomNavigationView
    private BottomNavigationView bottomNavigationView;

    private int[] idUsuario;

    public static final String COMANDA = "ComandaCliente.Comanda";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_mesa_cliente);

        context = this;

        intent = getIntent();
        restaurante = (Restaurante) intent.getSerializableExtra(CodigoComandaClienteActivity.RESTAURANTE);
        comanda = (Comanda) intent.getSerializableExtra(CodigoComandaClienteActivity.COMANDA);
        itens = (Item[]) intent.getSerializableExtra(CodigoComandaClienteActivity.ITENS);
        idUsuario = (int[]) intent.getSerializableExtra(CodigoComandaClienteActivity.USUARIO_IDS);

        implementacaoComponentes();

        Log.d("TESTES", "Comanda = "+ comanda.toString());


        //Carrega os detalhes da comanda
            textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
            textViewItemPessoaComandaNumero.setText(""+idUsuario.length);
            textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
            textViewItemHora.setText(comanda.getDataEntrada());


        //Carraga listView de itens da comanda
        if (itens != null) {
            carregarItens();
        }

        getTotalComanda();
        textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));


    }

    private void carregarItens() {
        formatItens();
        getTotalComanda();

        //Listview com itens da comanda selecionada
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaMesaCliente_itensDaComanda);
        ComandaMesaClienteAdapter adapter = new ComandaMesaClienteAdapter(itensFormatados, this);
        listViewItensComanda.setAdapter(adapter);
        listViewItensComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d("TESTES", "Item Selecionado: idPedido="+itensFormatados[position].getIdPedido());
                Log.d("TESTES", "Item Selecionado: Item="+itensFormatados[position].toString());

                intentItem = new Intent(context, ItemComandaDetalheClienteActivity.class);
                intentItem.putExtra(ITEM, itensFormatados[position]);
                intentItem.putExtra(COMANDA, comanda);
                startActivity(intentItem);
            }
        });
    }

    private void formatItens() {
        Set idPedidos = new HashSet();
        List<Item> itensF = new ArrayList<>();
        for (Item i : itens) {

            //se existir idPedido no set, é necessario adcionar o novo usuario ao usuariosPedido do item correspondente
            if (idPedidos.contains(i.getIdPedido())) {
                //pega o item que tem idPedido ==
                for (Item item : itensF) {
                    if (item.getIdPedido() == i.getIdPedido()) {
                        List<Usuario> us = item.getUsuariosPedido();
                        //adciona usuario e devolve ao item
                        Usuario u = new Usuario();
                        u.setId(i.getIdUsuario());
                        u.setNome(i.getNomeUsuario());
                        u.setEmail(i.getEmailUsuario());
                        u.setPorcPedido(i.getPorcPaga());
                        u.setStatusPedido(i.getStatusPedidoUsuario());
                        us.add(u);
                        item.setUsuariosPedido(us);
                    }
                }
                // Se não existir idPedido, adciona direto na lista
            } else {
                idPedidos.add(i.getIdPedido());
                if (i.getNomeUsuario() != null) {
                    Usuario u = new Usuario();
                    u.setId(i.getIdUsuario());
                    u.setNome(i.getNomeUsuario());
                    u.setEmail(i.getEmailUsuario());
                    u.setPorcPedido(i.getPorcPaga());
                    u.setStatusPedido(i.getStatusPedidoUsuario());
                    List<Usuario> temp = new ArrayList<Usuario>();
                    temp.add(u);
                    i.setUsuariosPedido(temp);

                }else{

                    i.setUsuariosPedido(new ArrayList<Usuario>());
                }
                itensF.add(i);

            }

        }
        //volta a ser Array em vez de List
        Object[] objects = itensF.toArray();
        Item[] itensArray = new Item[objects.length];
        for (int i = 0; i < objects.length; i++) {
            itensArray[i] = (Item) objects[i];
        }
        itensFormatados = itensArray;

    }


    private void getTotalComanda() {
        for (Item i : itensFormatados) {
            valorTotalComanda += i.getValor();
        }
    }


    public void implementacaoComponentes() {

        //TextView
        textViewItemCodigoComanda = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemCodigoComanda);
        textViewItemTotalComanda = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemTotalComanda);
        textViewItemTotalComandaValor = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemTotalComandaValor);
        textViewItemPessoaComanda = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemPessoasComandaNumero);
        textViewItemMesa = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemMesa);
        textViewItemMesaNumero = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemMesaNumero);
        textViewItemHora = (TextView) findViewById(R.id.textView_comandaMesaCliente_dataValor);
        textViewItensDaComanda = (TextView) findViewById(R.id.textView_comandaMesaCliente_itensNaComanda);


        //ListView
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaMesaCliente_itensDaComanda);
    }

}
