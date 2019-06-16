package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

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

    //Toolbar
    private Toolbar toolbar;


    private Usuario clienteLogado;
    private Comanda comanda;

    Context context;
    Intent intent;

    private Intent intentTelaPago;

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
        intent = this.getIntent();
        viewSnackbar = findViewById(R.id.formasPagamentoActivityView);

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
            getSupportActionBar().setTitle(R.string.textview_formaspagamento_titulopagina);     //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch(view.getId()) {
            case R.id.radioButton_formasPagamento_credito:
                if (checked)
                    break;
            case R.id.radioButton_formasPagamento_debito:
                if (checked)
                    break;
            case R.id.radioButton_formasPagamento_dinheiro:
                if (checked)
                    break;
            case R.id.radioButton_formasPagamento_ticket:
                if (checked)
                    break;
        }
    }


    public void onClickConcluir(final View view) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS){
            setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
            finish();
        }
    }

    private void implementarComponentes() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
