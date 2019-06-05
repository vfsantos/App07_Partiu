package br.com.app07_partiu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.R;

public class PagamentoConfirmadoActivity extends AppCompatActivity {

    //ImageView
    private ImageView imageViewCerveja;


    //TextView
    private TextView textViewDescritivo1;
    private TextView textViewDescritivo2;


    //Button
    private Button buttonFechar;
    private Button buttonFeedback;


    //Intent
    private Intent intentAvaliacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_confirmado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        implementarComponentes();
    }

    public void onClickFechar(View view) {
        buttonFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickFeedback(View view) {
        intentAvaliacao = new Intent(this, AvaliacaoActivity.class);
        startActivityForResult(intentAvaliacao, ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
    }


    private void implementarComponentes() {

        //ImageView
        imageViewCerveja    = (ImageView) findViewById(R.id.imageView_pagamentoConfirmado_cerveja);


        //TextView
        textViewDescritivo1 = (TextView) findViewById(R.id.textView_pagamentoConfirmado_descritivo1);
        textViewDescritivo2 = (TextView) findViewById(R.id.textView_pagamentoConfirmado_descritivo2);


        //Button
        buttonFechar        = (Button) findViewById(R.id.button_pagamentoConfirmado_fechar);
        buttonFeedback      = (Button) findViewById(R.id.button_pagamentoConfirmado_feedback);
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
