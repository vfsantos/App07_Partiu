package br.com.app07_partiu.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteAdapter;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.ItemRestauranteConvertView;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class ExplorarClienteDetalhesActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar toolbar;


    //TextView
    private TextView textViewRecomendacaoDetalheNomeRestaurante;
    private TextView textViewRecomendacaoDetalheHorarioFuncionamento;
    private TextView textViewRecomendacaoDetalheHorarioFuncionamentoValor;
    private TextView textViewRecomendacaoDetalheEndereco;
    private TextView textViewRecomendacaoDetalheEnderecoValor;


    //ImageView
    private ImageView imageViewRecomendacaoDetalheLogo;


    //ButtonView
    private Button buttonRecomendacaoDetalhesVoltar;


    //Intent
    private Intent intentRecomendacao;


    //Context
    private Context context;
    public Activity activity;


    //Objeto
    public Restaurante restaurante;
    public Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar_cliente_detalhes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        implementarComponentes();

        buttonRecomendacaoDetalhesVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intentRecomendacao = getIntent();

        restaurante = (Restaurante) intentRecomendacao.getSerializableExtra(ExplorarClienteAdapter.RECOMENDACAO_DETALHE);

        textViewRecomendacaoDetalheNomeRestaurante.setText(restaurante.getNomeFantasia());
        textViewRecomendacaoDetalheHorarioFuncionamentoValor.setText(null);
        textViewRecomendacaoDetalheEnderecoValor.setText(restaurante.getEndereco().toString());

    }


    public void implementarComponentes() {
        //Toolbar
        toolbar = findViewById(R.id.toolbar);


        //ImageView
        imageViewRecomendacaoDetalheLogo                     = (ImageView) findViewById(R.id.imageView_recomendacaoDetalhe_logo);


        //TextView
        textViewRecomendacaoDetalheNomeRestaurante           = (TextView) findViewById(R.id.textView_recomendcaoDetalhe_nomeRestaurante);
        textViewRecomendacaoDetalheHorarioFuncionamento      = (TextView) findViewById(R.id.textView_recomendacaoDetalhe_horarioFuncionamento);
        textViewRecomendacaoDetalheHorarioFuncionamentoValor = (TextView) findViewById(R.id.textView_recomendacaoDetalhe_horarioFuncionamentoValor);
        textViewRecomendacaoDetalheEndereco                  = (TextView) findViewById(R.id.textView_recomendcaoDetalhe_endereco);
        textViewRecomendacaoDetalheEnderecoValor             = (TextView) findViewById(R.id.textView_recomendacaoDetalhe_enderecoValor);


        //Button
        buttonRecomendacaoDetalhesVoltar = (Button) findViewById(R.id.button_recomendacao_voltar);
    }

}
