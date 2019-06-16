package br.com.app07_partiu.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteAdapter;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class ExplorarClienteDetalhesActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar toolbar;


    //TextView
    private TextView textViewRecomendacaoDetalheNomeRestaurante;
    private TextView textViewRecomendacaoDetalheDetalhe;
    private TextView textViewRecomendacaoDetalheDetalheValor;
    private TextView textViewRecomendacaoDetalheEndereco;
    private TextView textViewRecomendacaoDetalheEnderecoValor;


    //ImageView
    private ImageView imageViewRecomendacaoDetalheLogo;


    //Intent
    private Intent intentRecomendacao;


    //Context
    public Context context;
    public Activity activity;


    //Objeto
    public Restaurante restaurante;
    public Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar_cliente_detalhes);

        context = this;

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

        implementarComponentes();

        intentRecomendacao = getIntent();

        restaurante = (Restaurante) intentRecomendacao.getSerializableExtra(ExplorarClienteAdapter.RECOMENDACAO_DETALHE);

        textViewRecomendacaoDetalheNomeRestaurante.setText(restaurante.getNomeFantasia());
        textViewRecomendacaoDetalheDetalheValor.setText(restaurante.getDescricao());
        textViewRecomendacaoDetalheEnderecoValor.setText(restaurante.getEndereco().toString());

        Glide.with(this).load(restaurante.getLogo()).into(imageViewRecomendacaoDetalheLogo);
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                   //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                                        //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_explorarclientedetalhes_titulopagina);  //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }


    public void implementarComponentes() {
        //Toolbar
        toolbar = findViewById(R.id.toolbar);


        //ImageView
        imageViewRecomendacaoDetalheLogo                     = (ImageView) findViewById(R.id.imageView_recomendacaoDetalhe_logo);


        //TextView
        textViewRecomendacaoDetalheNomeRestaurante           = (TextView) findViewById(R.id.textView_recomendcaoDetalhe_nomeRestaurante);
        textViewRecomendacaoDetalheDetalhe                   = (TextView) findViewById(R.id.textView_recomendacaoDetalhe_detalhe);
        textViewRecomendacaoDetalheDetalheValor              = (TextView) findViewById(R.id.textView_recomendacaoDetalhe_detalheValor);
        textViewRecomendacaoDetalheEndereco                  = (TextView) findViewById(R.id.textView_recomendcaoDetalhe_endereco);
        textViewRecomendacaoDetalheEnderecoValor             = (TextView) findViewById(R.id.textView_recomendacaoDetalhe_enderecoValor);


    }

}
