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
import java.util.Timer;
import java.util.TimerTask;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.ItemDetalheGarcomActivity;
import br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity.ResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

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

    private String dataAtualizacao;

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
        comanda = (Comanda) intent.getSerializableExtra(HomeGarcomActivity.COMANDA);
        itens = (Item[]) intent.getSerializableExtra(HomeGarcomActivity.PEDIDOS);
        idUsuario = (int[]) intent.getSerializableExtra(HomeGarcomActivity.USUARIO_IDS);
        dataAtualizacao = (String) intent.getSerializableExtra(HomeGarcomActivity.DATA_ATUALIZACAO_COMANDA);

        //Detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        textViewItemPessoaComandaNumero.setText("0");
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        textViewItemDataValor.setText(comanda.getDataEntrada().split(" ")[1].replace(":", "h"));

        if (itens != null) {
            carregarItens();
        }

        textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));
        textViewItemPessoaComandaNumero.setText("" + idUsuario.length);

        setReloadInterval();

    }

    private void carregarItens() {
        try {

            itensFormatados = Util.formatItens(itens);
            getTotalComanda();
            listViewItensComanda.setVisibility(View.VISIBLE);
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

        }catch(NullPointerException e){
            Log.d("TESTES", "carregarItens: Sem Pedidos na Comanda");
            listViewItensComanda.setVisibility(View.INVISIBLE);

        }

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
        } else if (resultCode == RESULT_PEDIDO_REMOVIDO) {
            Toast.makeText(context, "Pedido Cancelado!", Toast.LENGTH_LONG).show();

            Item[] itensRecarregar = (Item[]) data.getSerializableExtra(ItemDetalheGarcomActivity.PEDIDOS_REFRESH);

            itens = itensRecarregar;
            carregarItens();
        }
    }

    private void setReloadInterval(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String novaDataAtualizacao = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());
                            Log.d("TESTES", "ComandaGarcom_novaDataAtualizacao = "+novaDataAtualizacao);
                            //TODO verificar pq data atualizacao vem null
                            if (!(dataAtualizacao==novaDataAtualizacao)){
                                Log.d("TESTES", "ComandaGarcom_novaDataAtualizacao; DataAtualização diferentes, recarregando List Pedidos");
                                dataAtualizacao = novaDataAtualizacao;
                                final Item[] itensNovos = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());

                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                    itens = itensNovos;
                                                    carregarItens();
                                                  }
                                              }
                                );
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };
        timer.schedule( task, 0L ,3000L);
    }


    private void inicializarComponentes() {

        //TextView
        textViewItemCodigoComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemCodigoComanda);
        textViewItemTotalComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComanda);
        textViewItemTotalComandaValor = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComandaValor);
        textViewItemPessoaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComandaNumero);
        textViewItemMesa = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesa);
        textViewItemMesaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesaNumero);
        textViewItemData = (TextView) findViewById(R.id.textView_comandaGarcom_data);
        textViewItemDataValor = (TextView) findViewById(R.id.textView_comandaGarcom_dataValor);
        textViewItensDaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itensNaComanda);

        //ListView
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);

        //Button
        buttonPedido = (Button) findViewById(R.id.button_comandaGarcom_pedido);

    }
}
