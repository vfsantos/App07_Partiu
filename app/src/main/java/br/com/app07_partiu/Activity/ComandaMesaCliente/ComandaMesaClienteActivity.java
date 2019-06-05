package br.com.app07_partiu.Activity.ComandaMesaCliente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity.FinalizarPedidoClienteActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
import br.com.app07_partiu.Activity.PerfilClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

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
    public static final String COMANDA = "ComandaCliente.Comanda";
    public static final String CLIENTE_LOGADO = "ComandaCliente.ClienteLogado";
    public static final String ITENS_FINALIZAR ="ComandaCliente.ItensFInalizar";

    public static final int RESULT_PEDIDOSFINALIZADOS = 3333;

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

    private int[] idUsuario;
    private Item itemDetalhe;

    private Usuario clienteLogado;

    private Item[] pedidosFinalizarCliente;

    private Button btnFinalizarPedidos;

    String dataAtualizacao;
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
        clienteLogado = (Usuario) intent.getSerializableExtra(CodigoComandaClienteActivity.CLIENTE);
        dataAtualizacao = (String) intent.getSerializableExtra(CodigoComandaClienteActivity.DATA_ATUALIZACAO_COMANDA);




        implementacaoComponentes();

        Log.d("TESTES", "Comanda = " + comanda.toString());

        //Carrega os detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        textViewItemPessoaComandaNumero.setText("" + idUsuario.length);
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        textViewItemHora.setText(comanda.getDataEntrada());

        //Carraga listView de itens da comanda
        if (itens != null) {
            carregarItens();
        }else{
            textViewItemTotalComandaValor.setText(doubleToReal(0));
        }

        setReloadInterval();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadPedidos();
        carregarItens();
    }

    private void carregarItens() {
        itensFormatados = Util.formatItens(itens);
        getTotalComanda();
        textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));

        //Listview com itens da comanda selecionada
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaMesaCliente_itensDaComanda);
        ComandaMesaClienteAdapter adapter = new ComandaMesaClienteAdapter(itensFormatados, this);
        listViewItensComanda.setAdapter(adapter);
        listViewItensComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!itensFormatados[position].getStatusPedido().equals("P"))
                    getItemComandaDetalhe(itensFormatados[position].getIdPedido());
            }
        });
    }

    private void reloadPedidos(){
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      carregarItens();
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                Log.e("TESTES", "IOException reloadPedidos'");
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }



    }

    private void getTotalComanda() {
        valorTotalComanda = 0.0;
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
        btnFinalizarPedidos = findViewById(R.id.button_cardapioResumoGarcom_finalizar);

    }

    private void getItemComandaDetalhe(final int idPedido) {
        if (Connection.isConnected(context)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Item[] pedidoNaoFormatado = ComandaNetwork.getPedidoUsuarioBydId(Connection.URL, idPedido);
                        itemDetalhe = Util.formatItens(pedidoNaoFormatado)[0];
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Log.d("TESTES", "Item Selecionado: idPedido=" + idPedido);
                                              intentItem = new Intent(context, ItemComandaDetalheClienteActivity.class);
                                              intentItem.putExtra(COMANDA, comanda);
                                              intentItem.putExtra(ITEM, itemDetalhe);
                                              intentItem.putExtra(CLIENTE_LOGADO, clienteLogado);
                                              startActivity(intentItem);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        Log.d("TESTES", "getItemComandaDetalhe - Erro ao pegar pedido de id=" + idPedido);
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public void onClickIrAoCarrinho(View view) {
//        btnFinalizarPedidos.setEnabled(false);

        if (Connection.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pedidosFinalizarCliente = ComandaNetwork.getPedidosByUsuarioComanda(Connection.URL, comanda.getId(), clienteLogado.getId());

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Intent intentFinalizarPedidos = new Intent(context, FinalizarPedidoClienteActivity.class);
                                              intentFinalizarPedidos.putExtra(CLIENTE_LOGADO, clienteLogado);
                                              intentFinalizarPedidos.putExtra(COMANDA, comanda);
                                              intentFinalizarPedidos.putExtra(ITENS_FINALIZAR, pedidosFinalizarCliente);
                                              startActivityForResult(intentFinalizarPedidos, RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );

                    } catch (IOException e) {
                        Log.d("TESTES", "onClickIrAoCarrinho: IOException");
//                        e.printStackTrace();
                    }
                    
//                    btnFinalizarPedidos.setEnabled(true);
                }
            }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_PEDIDOSFINALIZADOS){
            finish();
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
//                            Log.d("TESTES", "ComandaCliente_novaDataAtualizacao = "+novaDataAtualizacao);
                            if (!dataAtualizacao.equals(novaDataAtualizacao)){
//                                Log.d("TESTES", "ComandaCliente_novaDataAtualizacao; DataAtualização diferentes, recarregando List Pedidos");
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
}
