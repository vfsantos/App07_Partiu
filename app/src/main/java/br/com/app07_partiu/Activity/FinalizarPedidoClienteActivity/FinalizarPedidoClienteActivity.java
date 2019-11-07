package br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
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


    public static final String USUARIO_LOGADO = "FinalizarPedido.Usuario";
    public static final String COMANDA = "FinalizarPEdido.Comadna";


    //ConstraintLayout
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutFechar;
    private ConstraintLayout constraintLayoutDadosRestaurante;
    private ConstraintLayout constraintLayoutBody;
    private ConstraintLayout constraintLayoutButton;


    //ImageView
    private ImageView imageViewFechar;


    //ListView
    private ListView listViewResumoPedido;


    //Button
    private Button buttonConfirmar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewNomeRestaurante;
    private TextView textViewEnderecoRestaurante;


    //Objeto
    private Usuario clienteLogado;
    private Comanda comanda;
    private Item[] itens;
    private Context context;


    //Double
    private double valorTotalComanda = 0.0;


    //Intent
    private Intent intent;
    private Intent intentItem;


    //SnackBar
    private View viewSnackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido_cliente);
        implementarComponentes();

        comanda = new Comanda();
        context       = this;
        intent        = getIntent();
        viewSnackbar  = findViewById(R.id.finalizarPedidoClienteActivityView);
        clienteLogado = (Usuario) intent.getSerializableExtra(ComandaMesaClienteActivity.CLIENTE_LOGADO);
        comanda       = (Comanda) intent.getSerializableExtra(ComandaMesaClienteActivity.COMANDA);
        itens         = (Item[]) intent.getSerializableExtra(ComandaMesaClienteActivity.ITENS_FINALIZAR);


        textViewNomeRestaurante.setText("Comanda " + comanda.getCodigoComanda());
        textViewEnderecoRestaurante.setText("Valor total R$ " + doubleToReal(valorTotalComanda));

        carregarItens();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        reloadPedidos();
    }

    private void reloadPedidos() {
        if (Connection.isConnected(this, viewSnackbar)) {
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
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                                Log.d("TESTES", "FinalizarPedidoCliente: IOException reloadPedidos");
                            }
                        }
                    }).start();
        }


    }

    private void carregarItens() {
        try {
            getTotalComanda();

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
            Log.d("TESTES", "carregarItens; n sei como chegou aqui");
//            finish();
        }
    }

    private void getItemComandaDetalhe(final int idPedido) {
        if (Connection.isConnected(context, viewSnackbar)) {
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
                        e.printStackTrace();
                        Log.d("TESTES", "FinalizarPedidoCLiente: getItemComandaDetalhe - Erro ao pegar pedido de id=" + idPedido);
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }

    }

    private void getTotalComanda() {

        valorTotalComanda = 0.0;
        for (Item i : itens) {
            valorTotalComanda += i.getValor() * i.getPorcPaga() / 100;
        }

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

        if (resultCode == ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS) {
            setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
            finish();
        }
    }


    public void onClickFecharComandaMesaCliente(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    private void implementarComponentes() {

        //ConstraintLayout
        constraintLayoutHeader          = (ConstraintLayout) findViewById(R.id.constraintLayout_finalizarpedido_header);
        constraintLayoutFechar          = (ConstraintLayout) findViewById(R.id.constraintLayour_finalizarpedido_fechar);
        constraintLayoutDadosRestaurante= (ConstraintLayout) findViewById(R.id.constraintLayour_finalizarpedido_dadosRestaurante);
        constraintLayoutBody            = (ConstraintLayout) findViewById(R.id.constraintLayout_finalizarpedido_body);
        constraintLayoutButton          = (ConstraintLayout) findViewById(R.id.constraintLayour_finalizarpedido_button);


        //TextView
        textViewTitulo                  = (TextView) findViewById(R.id.textView_finalizarpedido_tituloPagina);
        textViewNomeRestaurante         = (TextView) findViewById(R.id.textView_finalizarpedido_nomeRestaurante);
        textViewEnderecoRestaurante     = (TextView) findViewById(R.id.textView_finalizarpedido_enderecoRestaurante);


        //ImageView
        imageViewFechar                 = (ImageView) findViewById(R.id.imageview_finalizarpedido_fechar);


        //ListView
        listViewResumoPedido            = (ListView) findViewById(R.id.listview_finalizarPedidoCliente_lista);


        //Button
        buttonConfirmar                 = (Button) findViewById(R.id.button_finalizarPedido_confirmar);
    }
}
