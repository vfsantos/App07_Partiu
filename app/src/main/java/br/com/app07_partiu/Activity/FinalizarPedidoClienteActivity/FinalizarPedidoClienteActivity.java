package br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteAdapter;
import br.com.app07_partiu.Activity.FormasPagamentoActivity;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class FinalizarPedidoClienteActivity extends AppCompatActivity {


    public static final String  USUARIO_LOGADO ="FinalizarPedido.Usuario";
    public static final String COMANDA = "FinalizarPEdido.Comadna";
    //ListView
    private ListView listViewResumoPedido;


    //Button
    private Button buttonConfirmar;


    //TextView
    private TextView textViewTotal;
    private TextView textViewTotalValor;
    private TextView textViewItensSelecionado;

    private Usuario clienteLogado;
    private Comanda comanda;
    private Item[] itens;

    private Context context;
    private Intent intent;

    private double valorTotalComanda = 0.0;

    Intent intentItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        intent = getIntent();

        clienteLogado = (Usuario) intent.getSerializableExtra(ComandaMesaClienteActivity.CLIENTE_LOGADO);
        comanda = (Comanda) intent.getSerializableExtra(ComandaMesaClienteActivity.COMANDA);
        itens = (Item[]) intent.getSerializableExtra(ComandaMesaClienteActivity.ITENS_FINALIZAR);

        implementarComponentes();

        carregarItens();
        verifyPagos();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadPedidos();
    }

    private void verifyPagos(){
        int countPagos = 0;
        for (Item i : itens){
            Log.d("TESTES", i.getStatusPedidoUsuario());
            if (i.getStatusPedidoUsuario().equals("P")){

                countPagos++;
            }
        }
        if (countPagos == itens.length){
            Toast.makeText(context, "Carrinho Vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private void reloadPedidos(){
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                itens = ComandaNetwork.getPedidosByUsuarioComanda(Connection.URL, comanda.getId(), clienteLogado.getId());
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      carregarItens();
                                                      verifyPagos();

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

    private void carregarItens() {
        try {
            getTotalComanda();
            textViewTotalValor.setText(doubleToReal(valorTotalComanda));

            FinalizarPedidoClienteAdapter adapter = new FinalizarPedidoClienteAdapter(itens, this);
            listViewResumoPedido.setAdapter(adapter);
            listViewResumoPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    getItemComandaDetalhe(itens[position].getIdPedido());
                }
            });

        } catch (NullPointerException e) {
            Toast.makeText(context, "Carrinho Vazio!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void getItemComandaDetalhe(final int idPedido) {
        if (Connection.isConnected(context)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Item[] pedidoNaoFormatado = ComandaNetwork.getPedidoUsuarioBydId(Connection.URL, idPedido);
                        final Item itemDetalhe = Util.formatItens(pedidoNaoFormatado)[0];
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Log.d("TESTES", "Item Selecionado: idPedido=" + idPedido);
                                              intentItem = new Intent(context, ItemComandaDetalheClienteActivity.class);
                                              intentItem.putExtra(ComandaMesaClienteActivity.COMANDA, comanda);
                                              intentItem.putExtra(ComandaMesaClienteActivity.ITEM, itemDetalhe);
                                              intentItem.putExtra(ComandaMesaClienteActivity.CLIENTE_LOGADO, clienteLogado);
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

    private void getTotalComanda() {

        valorTotalComanda = 0.0;
        for (Item i : itens) {
            valorTotalComanda += i.getValor();
        }

    }


    private void implementarComponentes() {
        //ListView
        listViewResumoPedido = (ListView) findViewById(R.id.listview_finalizarPedidoCliente_lista);


        //Button
        buttonConfirmar = (Button) findViewById(R.id.button_finalizarPedido_confirmar);


        //TextView
        textViewTotal = (TextView) findViewById(R.id.textview_finalizarpedido_total);
        textViewTotalValor = (TextView) findViewById(R.id.textview_finalizarpedido_totalvalor);
        textViewItensSelecionado = (TextView) findViewById(R.id.textView_finalizarPedido_itensSelecionados);
    }

    public void onClickFInalizarPedidos(View view) {
        Intent intentPagamento = new Intent(this, FormasPagamentoActivity.class);
        intentPagamento.putExtra(USUARIO_LOGADO, clienteLogado);
        intentPagamento.putExtra(COMANDA, comanda);
        startActivityForResult(intentPagamento, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS){
            setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
            finish();
        }
    }
}
