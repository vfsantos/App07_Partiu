package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.ItemDetalheGarcomActivity;
import br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity.ResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class ComandaGarcomActivity extends AppCompatActivity {

    //TextView
    private TextView textViewTituloPagina;
    private TextView textViewItemCodigoComanda;
    private TextView textViewItemTotalComanda;
    private TextView textViewItemTotalComandaValor;
    private TextView textViewItemPessoaComanda;
    private TextView textViewItemPessoaComandaNumero;
    private TextView textViewItemMesa;
    private TextView textViewItemMesaNumero;
    private TextView textViewItemData;
    private TextView textViewItemDataValor;
    private TextView textViewItensDaComanda;

    public static final String ITEM = "br.com.app07_partiu.ComandaGarcomActivity.item";
    public static final String COMANDA = "br.com.app07_partiu.ComandaGarcomActivity.comanda";
    public static final String ITENS_RESTAURANTE = "br.com.app07_partiu.ComandaGarcomActivity.itensRestaurante";

    public static final String ID_COMANDA = "CardapioGarcom.idComanda";

    public static final int RESULT_PEDIDOS_CRIADOS = 1000;
    public static final int RESULT_PEDIDO_REMOVIDO = 2000;

    //ListView
    private ListView listViewItensComanda;


    //Button
    private Button buttonPedido;


    //Itent
    private Intent intent;
    private Intent intentItem;


    //Objeto
    private Comanda comanda;
    private ComandaConvertView convertedComanda;
    public Item[] itens;
    public Item[] itensFormatados;
    private Context context;

    private Double valorTotalComanda = 0.0;
    private ComandaConvertView comandaConvertView;
    private Restaurante restaurante;
    private Intent intentPedidoSelecaoGarcom;
    private Item[] itensRestaurante;
    private int[] idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        inicializarComponentes();

        buttonPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizarItensRestaurante();
            }
        });


        intent = getIntent();


        restaurante = (Restaurante) intent.getSerializableExtra(HomeGarcomActivity.RESTAURANTE);
        comanda     = (Comanda) intent.getSerializableExtra(HomeGarcomActivity.COMANDA);
        itens       = (Item[]) intent.getSerializableExtra(HomeGarcomActivity.PEDIDOS);
        idUsuario   = (int[]) intent.getSerializableExtra(HomeGarcomActivity.USUARIO_IDS);

        //Detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        textViewItemPessoaComandaNumero.setText("0");
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        textViewItemDataValor.setText(comanda.getDataEntrada().split(" ")[1].replace(":", "h"));



        if (itens != null) {
            carregarItens();
        }

        textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));
        textViewItemPessoaComandaNumero.setText(""+idUsuario.length);



    }

    private void carregarItens() {
        formatItens();
        getTotalComanda();

        //Listview com itens da comanda selecionada
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);
        ComandaGarcomAdapter adapter = new ComandaGarcomAdapter(itens, this);
        listViewItensComanda.setAdapter(adapter);
        listViewItensComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                intentItem = new Intent(context, ItemDetalheGarcomActivity.class);
                intentItem.putExtra(ITEM, itens[position]);
                intentItem.putExtra(ID_COMANDA, comanda.getId());
                startActivityForResult(intentItem, RESULT_PEDIDO_REMOVIDO);
            }
        });
    }

    private void visualizarItensRestaurante() {
        intentPedidoSelecaoGarcom = new Intent(context, CardapioGarcomActivity.class);
        if (Connection.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itensRestaurante = ItemNetwork.getItensCardapioGarcom(Connection.URL, restaurante.getCnpj());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {

                                              //Comanda necessaria para saber em que id inserir pedidos
                                              intentPedidoSelecaoGarcom.putExtra(COMANDA, comanda);
                                              intentPedidoSelecaoGarcom.putExtra(ITENS_RESTAURANTE, itensRestaurante);
                                              startActivityForResult(intentPedidoSelecaoGarcom, RESULT_PEDIDOS_CRIADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
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
                if (i.getNomeUsuario() != null || i.getNomeUsuario() != "") {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_PEDIDOS_CRIADOS) {
            Item[] itensRecarregar = (Item[]) data.getSerializableExtra(ResumoCardapioGarcomActivity.RETORNO_ITENS_COMANDA);
            itens = itensRecarregar;
            carregarItens();
        }else if (resultCode == RESULT_PEDIDO_REMOVIDO) {
            Toast.makeText(context, "Pedido Cancelado!", Toast.LENGTH_LONG).show();

            Item[] itensRecarregar = (Item[]) data.getSerializableExtra(ItemDetalheGarcomActivity.PEDIDOS_REFRESH);
            itens = itensRecarregar;
            carregarItens();
        }
    }


    private void inicializarComponentes() {

        //TextView
//        textViewTituloPagina          = (TextView) findViewById(R.id.textView_itemDetalhes_tituloPage);
        textViewItemCodigoComanda       = (TextView) findViewById(R.id.textView_comandaGarcom_itemCodigoComanda);
        textViewItemTotalComanda        = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComanda);
        textViewItemTotalComandaValor   = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComandaValor);
        textViewItemPessoaComanda       = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComandaNumero);
        textViewItemMesa                = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesa);
        textViewItemMesaNumero          = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesaNumero);
        textViewItemData                = (TextView) findViewById(R.id.textView_comandaGarcom_data);
        textViewItemDataValor           = (TextView) findViewById(R.id.textView_comandaGarcom_dataValor);
        textViewItensDaComanda          = (TextView) findViewById(R.id.textView_comandaGarcom_itensNaComanda);


        //ListView
        listViewItensComanda            = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);


        //Button
        buttonPedido                    = (Button) findViewById(R.id.button_comandaGarcom_pedido);

    }
}
