package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class ItemDetalheGarcomActivity extends AppCompatActivity {

    //TextView
    private TextView textViewTituloPagina;
    private TextView textViewNomeItem;
    private TextView textViewDetalhesItem;
    private TextView textViewValorItem;
    private TextView textViewAlgumaObservacao;
    private TextView textViewDetalhesObservacao;
    private TextView textViewQuantidade;
    private TextView textViewQuantidadeValor;
    private TextView textViewStatus;
    private TextView textViewStatusValor;

    //Button
    private Button buttonRemover;
    private Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detalhe_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaComponentes();

    }

    private void inicializaComponentes() {
        textViewTituloPagina = (TextView) findViewById(R.id.textView_itemDetalhes_tituloPage);
        textViewNomeItem = (TextView) findViewById(R.id.textView_itemDetalhes_nome);
        textViewDetalhesItem = (TextView) findViewById(R.id.textView_itemDetalhes_detalhes);
        textViewValorItem = (TextView) findViewById(R.id.textView_itemDetalhe_valorItem);
        textViewAlgumaObservacao = (TextView) findViewById(R.id.textView_itemDetalhes_algumaObservacao);
        textViewDetalhesObservacao = (TextView) findViewById(R.id.textView_itemDetalhes_detalheObservacao);
        textViewQuantidade = (TextView) findViewById(R.id.textView_itemDetalhe_quantidade);
        textViewQuantidadeValor = (TextView) findViewById(R.id.textView_itemDetalhes_quantidadeValor);
        textViewStatus = (TextView) findViewById(R.id.textView_itemDetalhes_status);
        textViewStatusValor = (TextView) findViewById(R.id.textView_itemDetalhes_statusValor);


        buttonRemover = (Button) findViewById(R.id.button_itemDetalhes_remover);
        buttonVoltar = (Button) findViewById(R.id.button_itemDetalhes_voltar);
    }

}
