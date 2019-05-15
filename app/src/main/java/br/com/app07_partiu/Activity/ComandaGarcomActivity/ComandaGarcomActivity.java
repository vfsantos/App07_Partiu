package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.Model.ItemConvertView;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class ComandaGarcomActivity extends AppCompatActivity {
    public static final String URL = LoginActivity.URL;
    public static final String ITEM = "br.com.app07_partiu.ComandaGarcomActivity.item";


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

    public static final String COMANDA = "br.com.app07_partiu.ComandaGarcomActivity.comanda";
    public static final String ITENS_RESTAURANTE = "br.com.app07_partiu.ComandaGarcomActivity.itensRestaurante";



    //ListView
    private ListView listViewItensComanda;


    //Itent
    private Intent intent;
    private Intent intentItem;


    //Objeto
    private Comanda comanda;
    private ComandaConvertView convertedComanda;
    public ItemComandaGarcomConvertView[] itens;
    public ItemComandaGarcomConvertView[] itensFormatados;
    private Context context;

    private Double valorTotalComanda = 0.0;

    private ComandaConvertView comandaConvertView;

    private Restaurante restaurante;

    private Intent intentPedidoSelecaoGarcom;


    private ItemConvertView[] itensRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                visualizarItensRestaurante();
            }
        });

        inicializarComponentes();
        context = this;

        intent = getIntent();
        restaurante = (Restaurante) intent.getSerializableExtra(HomeGarcomActivity.RESTAURANTE);
        comanda = (Comanda) intent.getSerializableExtra(HomeGarcomActivity.COMANDA);
        itens = (ItemComandaGarcomConvertView[]) intent.getSerializableExtra(HomeGarcomActivity.PEDIDOS);


        //Detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        // TODO carregar qtd pessoas na comanda
        //   Talvez colocar junto com o metodo escroto de formatar itens, já tá no meio do processo msm
        textViewItemPessoaComandaNumero.setText("00");
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        // TODO decidir entre total total ou total a pagar
        // TODO arrumar, esta vindo 0
        textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));
        textViewItemHora.setText(comanda.getDataEntrada());

        if (itens != null) {
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
                    startActivity(intentItem);
                }
            });
        }
    }

    private void inicializarComponentes(){
        textViewTituloPagina = (TextView) findViewById(R.id.textView_itemDetalhes_tituloPage);
        textViewItemCodigoComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemCodigoComanda);
        textViewItemTotalComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComanda);
        textViewItemTotalComandaValor = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComandaValor);
        textViewItemPessoaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComandaNumero);
        textViewItemMesa = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesa);
        textViewItemMesaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesaNumero);
        textViewItemHora = (TextView) findViewById(R.id.textView_comandaGarcom_itemHora);
        textViewItensDaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itensNaComanda);
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);

    }

    private void visualizarItensRestaurante() {
        intentPedidoSelecaoGarcom = new Intent(context, CardapioGarcomActivity.class);
        if (ComandaNetwork.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itensRestaurante = ItemNetwork.getItensCardapio(URL, restaurante.getCnpj());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              //Comanda necessaria para saber em que id inserir pedidos
                                              intentPedidoSelecaoGarcom.putExtra(COMANDA, comanda);
                                              intentPedidoSelecaoGarcom.putExtra(ITENS_RESTAURANTE, itensRestaurante);
                                              startActivity(intentPedidoSelecaoGarcom);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast.makeText(this, "Rede inativa", Toast.LENGTH_SHORT).show();
        }
    }


    //TODO fazer saporra dificil pra krl
    // Não só tem que ver como vaif azer para itens já pagos; entram ou não?
    // Tem que levar em conta 3 níveis:
    // id item, idPedido e idUsuario
    // para cada id item, pode ter varios id pedido
    // para cada id pedido, pode ter varios id usuario
    // mas depende, acho que dá pra excluir item; mostrar um item na lista para cada pedido, e não 1 item falando "3x pedido"
    // socorro
    // Dia 1: tentando fazer com 2 níveis
    // Dia 1: socorro
    // Da forma atual, está carregando itens; e esses itens serão uma List<Usuario>
    // espero q funcione
    // Dia1.2: funfo parece, testar com pedidos partihados por + de 1 usuario

    private void formatItens() {
        Set idPedidos = new HashSet();
        List<ItemComandaGarcomConvertView> itensF = new ArrayList<>();
        for (ItemComandaGarcomConvertView i : itens) {

            //se existir idPedido no set, é necessario adcionar o novo usuario ao usuariosPedido do item correspondente
            if (idPedidos.contains(i.getIdPedido())) {
                //pega o item que tem idPedido ==
                for (ItemComandaGarcomConvertView item : itensF) {
                    if (item.getIdPedido() == i.getIdPedido()) {
                        List<Usuario> us = item.getUsuariosPedido();
                        //adciona usuario e devolve ao item
                        Usuario u = new Usuario();
                        u.setId(i.getIdUsuario());
                        u.setNome(i.getNomeUsuario());
                        u.setEmail(i.getEmailUsuario());
                        us.add(u);
                        item.setUsuariosPedido(us);
                    }
                }
                // Se não existir idPedido, adciona direto na lista
            } else {
                idPedidos.add(i.getIdPedido());
                itensF.add(i);

            }

        }
        //volta a ser Array em vez de List
        Object[] objects = itensF.toArray();
        ItemComandaGarcomConvertView[] itensArray = new ItemComandaGarcomConvertView[objects.length];
        for (int i = 0; i < objects.length; i++) {
            itensArray[i] = (ItemComandaGarcomConvertView) objects[i];
        }
        itensFormatados = itensArray;

    }

    private void getTotalComanda() {
        for (ItemComandaGarcomConvertView i : itensFormatados) {
            valorTotalComanda += i.getValor();
        }
    }

}
