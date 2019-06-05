package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity.FinalizarPedidoClienteActivity;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class FormasPagamentoActivity extends AppCompatActivity {


    private Usuario clienteLogado;
    private Comanda comanda;

    Context context;
    Intent intent;

    private Intent intentTelaPago;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formas_pagamento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        intent = this.getIntent();

        clienteLogado = (Usuario) intent.getSerializableExtra(FinalizarPedidoClienteActivity.USUARIO_LOGADO);
        comanda = (Comanda) intent.getSerializableExtra(FinalizarPedidoClienteActivity.COMANDA);


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


    public void onClickConcluir(View view) {
        intentTelaPago = new Intent(this, PagamentoConfirmadoActivity.class);

        if (Connection.isConnected(context)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarItemPedidoUsuario(Connection.URL, clienteLogado.getId(), comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(context, "Pedidos Pagos!", Toast.LENGTH_LONG).show();
//                                              setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
//                                              finish();

                                              startActivityForResult(intentTelaPago, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS){
            setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
            finish();
        }
    }
}
