package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity.FinalizarPedidoClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class PagamentoConfirmadoActivity extends AppCompatActivity {

    public static final String COMANDA = "br.com.app07_partiu.PagamentoConfirmado.comanda";
    public static final String USUARIO = "br.com.app07_partiu.PagamentoConfirmado.usuario";


    //ConstraintLayout
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutVoltar;


    //ImageView
    private ImageView imageViewConfirmacao;
    private ImageView imageViewVoltar;


    //TextView
    private TextView textViewDescritivo1;


    //Button
    private Button buttonFechar;
    private Button buttonFeedback;


    //Intent
    private Intent intentAvaliacao;
    private Intent intentFormaPagamento;


    //Context
    private Context context;


    //Objetos
    private Usuario usuario;
    private Comanda comanda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_confirmado);
        implementarComponentes();

        context = this;


        usuario = new Usuario();
        comanda= new Comanda();
        intentFormaPagamento = this.getIntent();
        usuario = (Usuario) intentFormaPagamento.getSerializableExtra(FormasPagamentoActivity.USUARIO);
        comanda = (Comanda) intentFormaPagamento.getSerializableExtra(FormasPagamentoActivity.COMANDA);

    }



    public void onClickVoltarFeedback(View view) {
        setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
        finish();
    }

    public void onClickFeedback(View view) {
        intentAvaliacao = new Intent(this, AvaliacaoActivity.class);
        intentAvaliacao.putExtra(USUARIO, usuario);
        intentAvaliacao.putExtra(COMANDA, comanda);
        startActivityForResult(intentAvaliacao, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS) {
            setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
            finish();
        }

    }


    public void onClickFecharPagamentoConfirmado(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void implementarComponentes() {
        //ConstraintLayout
        constraintLayoutHeader          = (ConstraintLayout) findViewById(R.id.constraintLayout_pagamentoConfirmado_header);
        constraintLayoutVoltar          = (ConstraintLayout) findViewById(R.id.constraintLayour_pagamentoConfirmado_voltar);


        //ImageView
        imageViewConfirmacao            = (ImageView) findViewById(R.id.imageView_pagamentoConfirmado_confirmacao);
        imageViewVoltar                 = (ImageView) findViewById(R.id.imageview_pagamentoConfirmado_voltar);


        //TextView
        textViewDescritivo1 = (TextView) findViewById(R.id.textView_pagamentoConfirmado_descritivo1);


        //Button
        buttonFechar        = (Button) findViewById(R.id.button_pagamentoConfirmado_fechar);
        buttonFeedback      = (Button) findViewById(R.id.button_pagamentoConfirmado_feedback);
    }
}
