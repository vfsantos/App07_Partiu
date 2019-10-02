package br.com.app07_partiu.Activity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity.FinalizarPedidoClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class FormasPagamentoActivity extends AppCompatActivity {

    public static final String COMANDA = "br.com.app07_partiu.FormasPagamento.comanda";
    public static final String USUARIO = "br.com.app07_partiu.FormasPagamento.usuario";


    //Toolbar
    private Toolbar toolbar;


    //TextView
    private TextView textViewCategoriasCartaoCredito;
    private TextView textViewCategoriasCartaoDebito;
    private TextView textViewCategoriasOutros;
    private TextView textViewCartaoCreditoVisa;
    private TextView textViewCartaoCreditoMasterCard;
    private TextView textViewCartaoDebitoVisa;
    private TextView textViewCartaoDebitoMasterCard;
    private TextView textViewDinhiero;
    private TextView textViewTicket;



    //ImageView
    private ImageView imageViewCartaoCreditoVisa;
    private ImageView imageViewCartaoCreditoMasterCard;
    private ImageView imageViewCartaoDebitoVisa;
    private ImageView imageViewCartaoDebitoMasterCard;
    private ImageView imageViewDinheiro;
    private ImageView imageViewTicket;



    //ConstraintLayout
    private ConstraintLayout constraintLayoutCartaoCreditoVisa;
    private ConstraintLayout constraintLayoutCartaoCreditoMasterCard;
    private ConstraintLayout constraintLayoutCartaoDebitoVisa;
    private ConstraintLayout constraintLayoutCartaoDebitoMasterCard;
    private ConstraintLayout constraintLayoutTicket;
    private ConstraintLayout constraintLayoutDinheiro;



    //Objetos
    private Usuario clienteLogado;
    private Comanda comanda;

    Context context;
    Intent intent;


    //Intent
    private Intent intentTelaPago;


    //Snackbar
    private View viewSnackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formas_pagamento);

        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

        context = this;
        viewSnackbar = findViewById(R.id.formasPagamentoActivityView);

        clienteLogado = new Usuario();
        comanda= new Comanda();
        intent = this.getIntent();
        clienteLogado = (Usuario) intent.getSerializableExtra(FinalizarPedidoClienteActivity.USUARIO_LOGADO);
        comanda = (Comanda) intent.getSerializableExtra(FinalizarPedidoClienteActivity.COMANDA);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                break;
            case R.id.action_settings: {
                Util.logoff(context);
            }
            default:break;
        }
        return true;
    }


    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_formaspagamento_titulopagina); //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS){
            setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
            finish();
        }
    }


    public void onClickCartaoCreditoMasterCard(final View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Util.showSnackbar(viewSnackbar, "Pedidos Pagos!");
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();

                                              intentTelaPago.putExtra(USUARIO, clienteLogado);
                                              intentTelaPago.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "FormasPagamento: IOException onClickConcluir");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }


    }


    public void onClickCartaoCreditoVisa(final View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Util.showSnackbar(viewSnackbar, "Pedidos Pagos!");
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();

                                              intentTelaPago.putExtra(USUARIO, clienteLogado);
                                              intentTelaPago.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "FormasPagamento: IOException onClickConcluir");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }


    }


    public void onClickCartaoDebitoMasterCard(final View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Util.showSnackbar(viewSnackbar, "Pedidos Pagos!");
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();

                                              intentTelaPago.putExtra(USUARIO, clienteLogado);
                                              intentTelaPago.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "FormasPagamento: IOException onClickConcluir");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }


    }


    public void onClickCartaoDebitoVisa(final View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Util.showSnackbar(viewSnackbar, "Pedidos Pagos!");
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();


                                              intentTelaPago.putExtra(USUARIO, clienteLogado);
                                              intentTelaPago.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "FormasPagamento: IOException onClickConcluir");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }


    }


    public void onClickDinheiro(final View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Util.showSnackbar(viewSnackbar, "Pedidos Pagos!");
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();


                                              intentTelaPago.putExtra(USUARIO, clienteLogado);
                                              intentTelaPago.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "FormasPagamento: IOException onClickConcluir");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }


    }


    public void onClickCartaoTicket(final View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Util.showSnackbar(viewSnackbar, "Pedidos Pagos!");
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();


                                              intentTelaPago.putExtra(USUARIO, clienteLogado);
                                              intentTelaPago.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "FormasPagamento: IOException onClickConcluir");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }


    }


    private void implementarComponentes() {
        //Toolbar
        toolbar                                     = (Toolbar) findViewById(R.id.toolbar);


        //TextView
        textViewCategoriasCartaoCredito             = (TextView) findViewById(R.id.textView_formaspagamento_categoriacartaocredito);
        textViewCategoriasCartaoDebito              = (TextView) findViewById(R.id.textView_formaspagamento_categoriacartaodebito);
        textViewCategoriasOutros                    = (TextView) findViewById(R.id.textView_formaspagamento_categoriaoutro);


        //ImageView
        imageViewCartaoCreditoMasterCard            = (ImageView) findViewById(R.id.imageView_formaspagamento_arrowright_ctcreditomastercard);
        imageViewCartaoCreditoVisa                  = (ImageView) findViewById(R.id.imageView_formaspagamento_arrowright_ctcreditovisa);
        imageViewCartaoDebitoMasterCard             = (ImageView) findViewById(R.id.imageView_formaspagamento_arrowright_ctdebitomastercard);
        imageViewCartaoDebitoVisa                   = (ImageView) findViewById(R.id.imageView_formaspagamento_arrowright_ctdebitovisa);
        imageViewTicket                             = (ImageView) findViewById(R.id.imageView_formaspagamento_arrowright_ticket);
        imageViewDinheiro                           = (ImageView) findViewById(R.id.imageView_formaspagamento_arrowright_dinhiero);


        //Button
        constraintLayoutCartaoCreditoMasterCard     = (ConstraintLayout) findViewById(R.id.constraintLayout_formaspagamento_ctcreditomastercard);
        constraintLayoutCartaoCreditoVisa           = (ConstraintLayout) findViewById(R.id.constraintLayout_formaspagamento_ctcreditovisa);
        constraintLayoutCartaoDebitoMasterCard      = (ConstraintLayout) findViewById(R.id.constraintLayout_formaspagamento_ctdebitomastercard);
        constraintLayoutCartaoDebitoVisa            = (ConstraintLayout) findViewById(R.id.constraintLayout_formaspagamento_ctdebitovisa);
        constraintLayoutTicket                      = (ConstraintLayout) findViewById(R.id.constraintLayout_formaspagamento_ticket);
        constraintLayoutDinheiro                    = (ConstraintLayout) findViewById(R.id.constraintLayout_formaspagamento_dinheiro);
    }
}
