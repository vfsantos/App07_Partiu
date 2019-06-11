package br.com.app07_partiu.Activity.ComandaMesaCliente;

import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity.FinalizarPedidoClienteActivity;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
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
    //Toolbar
    private Toolbar toolbar;


    //TextView
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
    public static final String ITENS_FINALIZAR = "ComandaCliente.ItensFInalizar";

    public static final int RESULT_PEDIDOSFINALIZADOS = 3333;
    public static final int RESULT_PEDIDOSELECIONADO = 4000;
    public static final int RESULT_PEDIDODESELECIONADO = 5000;

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
    private double valorTotalComanda = 0.0;
    private double valorPagoComanda = 0.0;

    private int[] idUsuario;
    private Item itemDetalhe;

    private Usuario clienteLogado;

    private Item[] pedidosFinalizarCliente;

    private Button btnFinalizarPedidos;

    private Timer timerAtualizacao;
    String dataAtualizacao;

    private View viewSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_mesa_cliente);
        implementarComponentes();
        setSupportActionBar(toolbar);

        context = this;
        intent = getIntent();
        viewSnackbar = findViewById(R.id.comandaMesaClienteActivityView);

        restaurante = (Restaurante) intent.getSerializableExtra(CodigoComandaClienteActivity.RESTAURANTE);
        comanda = (Comanda) intent.getSerializableExtra(CodigoComandaClienteActivity.COMANDA);
        itens = (Item[]) intent.getSerializableExtra(CodigoComandaClienteActivity.ITENS);
        idUsuario = (int[]) intent.getSerializableExtra(CodigoComandaClienteActivity.USUARIO_IDS);
        clienteLogado = (Usuario) intent.getSerializableExtra(CodigoComandaClienteActivity.CLIENTE);
        dataAtualizacao = (String) intent.getSerializableExtra(CodigoComandaClienteActivity.DATA_ATUALIZACAO_COMANDA);

        Log.d("TESTES", "Comanda = " + comanda.toString());

        //Carrega os detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        textViewItemPessoaComandaNumero.setText("" + idUsuario.length);
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        textViewItemHora.setText(comanda.getHoraEntrada());

        //Carraga listView de itens da comanda
        if (itens != null) {
            carregarItens();
        } else {
            textViewItemTotalComandaValor.setText(doubleToReal(0));
        }

        setReloadInterval();


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        reloadPedidos();
        carregarItens();
        setReloadInterval();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerAtualizacao.cancel();
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


    private void reloadPedidos() {
        if (Connection.isConnected(this, viewSnackbar)) {
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
                                Log.e("TESTES", "ComandaMesaCliente: IOException reloadPedidos'");
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }


    }

    private void getTotalComanda() {
        valorTotalComanda = 0.0;
        valorPagoComanda = 0.0;
        for (Item i : itensFormatados) {
            valorTotalComanda += i.getValor();
            if (i.getStatusPedido().equals("P")) {
                valorPagoComanda += i.getValor();
            } else if (i.getStatusPedido().equals("S")) {

                for (Usuario u : i.getUsuariosPedido()) {
                    if (u.getStatusPedido().equals("P")) {
                        valorPagoComanda += u.getPorcPedido() * i.getValor() / 100;
                    }
                }
            }

        }
        Log.d("TESTES", "ComandaValor Total: " + valorTotalComanda + "; Pago: " + valorPagoComanda);
    }



    private void getItemComandaDetalhe(final int idPedido) {
        if (Connection.isConnected(context, viewSnackbar)) {
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
                                              startActivityForResult(intentItem, RESULT_PEDIDOSELECIONADO);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        Log.d("TESTES", "ComandaMesaCliente: getItemComandaDetalhe - Erro ao pegar pedido de id=" + idPedido);
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
    private boolean verifyTodosPagos(Item[] pedidosFinalizarCliente){
        int countPagos = 0;
        for (Item i : pedidosFinalizarCliente){
            Log.d("TESTES", i.getStatusPedidoUsuario());
            if (i.getStatusPedidoUsuario().equals("P")){
                countPagos++;
            }
        }
        if (countPagos == pedidosFinalizarCliente.length){
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  alertPedidos();

                              }
                          }
            );
            return true;
        }else return false;
    }

    public void onClickIrAoCarrinho(View view) {
//        btnFinalizarPedidos.setEnabled(false);

        if (Connection.isConnected(this, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pedidosFinalizarCliente = ComandaNetwork.getPedidosByUsuarioComanda(Connection.URL, comanda.getId(), clienteLogado.getId());

                        if (pedidosFinalizarCliente == null) {

                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {

                                                  alertPedidos();


                                              }
                                          }
                            );

                        } else if(!verifyTodosPagos(pedidosFinalizarCliente)){
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
                        }


                    } catch (IOException e) {
                        Log.e("TESTES", "ComandaMesaCliente: IOException ronClickIrAoCArrionho'");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                        e.printStackTrace();
                    }

//                    btnFinalizarPedidos.setEnabled(true);
                }
            }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_PEDIDOSFINALIZADOS:
                finish();
                break;
            case RESULT_PEDIDOSELECIONADO:
                Util.showSnackbar(viewSnackbar, "Pedido Selecionado!");
                break;
            case RESULT_PEDIDODESELECIONADO:
                Util.showSnackbar(viewSnackbar, "Pedido Deselecionado!");
                break;
            default:
                break;
        }
    }

    private void alertPedidos() {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Nenhum pedido a pagar!");
        builder.setMessage("Deseja sair da comanda? :(");

        // setCancelable = se o usuario pode clicar fora da caixa para feixar tbm
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TESTES", "DialogClicked: Yes");
            }
        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TESTES", "DialogClicked: No");
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void setReloadInterval() {
        timerAtualizacao = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String novaDataAtualizacao = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());
//                            Log.d("TESTES", "ComandaCliente_novaDataAtualizacao = "+novaDataAtualizacao);
                            if (!dataAtualizacao.equals(novaDataAtualizacao)) {
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
//                            Log.e("TESTES", "ComandaMesaCliente: IOException onClickIrAoCArrionho'");
//                            Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };
        timerAtualizacao.schedule(task, 0L, 3000L);
    }

    private void implementarComponentes() {
        //Toolbar
        toolbar                         = (Toolbar) findViewById(R.id.toolbar);

        //TextView
        textViewItemCodigoComanda       = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemCodigoComanda);
        textViewItemTotalComanda        = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemTotalComanda);
        textViewItemTotalComandaValor   = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemTotalComandaValor);
        textViewItemPessoaComanda       = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemPessoasComandaNumero);
        textViewItemMesa                = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemMesa);
        textViewItemMesaNumero          = (TextView) findViewById(R.id.textView_comandaMesaCliente_itemMesaNumero);
        textViewItemHora                = (TextView) findViewById(R.id.textView_comandaMesaCliente_dataValor);
        textViewItensDaComanda          = (TextView) findViewById(R.id.textView_comandaMesaCliente_itensNaComanda);

        //ListView
        listViewItensComanda            = (ListView) findViewById(R.id.listView_comandaMesaCliente_itensDaComanda);
        btnFinalizarPedidos             = findViewById(R.id.button_cardapioResumoGarcom_finalizar);

    }
}
