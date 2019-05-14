package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class AdicionarItemGarcomActivity extends AppCompatActivity {

    //TextView
    private TextView textViewTituloPagina;
    private TextView textViewNomeItem;
    private TextView textViewDetalhesItem;
    private TextView textViewValorItem;
    private TextView textViewAlgumaObservacao;
    private TextView texViewQuantidade;


    //EditText
    private EditText editTextObservacao;


    //Button
    private Button buttonRemover;
    private Button buttonVoltar;
    private Button buttonAdicionarItem;


    //ImageView
    private ImageView imageViewAdd;
    private ImageView imageViewSub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaComponentes();

    }

    private void inicializaComponentes() {
        textViewTituloPagina = (TextView) findViewById(R.id.textView_adicionarItemGarcom_tituloPage);
        textViewNomeItem = (TextView) findViewById(R.id.textView_adicionarItemGarcom_nome);
        textViewDetalhesItem = (TextView) findViewById(R.id.textView_adicionarItemGarcom_detalhes);
        textViewValorItem = (TextView) findViewById(R.id.textView_adicionarItemGarcom_valorItem);
        textViewAlgumaObservacao = (TextView) findViewById(R.id.textView_adicionarItemGarcom_algumaObservacao);
        texViewQuantidade = (TextView) findViewById(R.id.textView_adicionarItem_quantidade);


        editTextObservacao = (EditText) findViewById(R.id.editText_adicionarItemGarcom_observacao);


        buttonAdicionarItem = (Button) findViewById(R.id.button_adicionarItem_addComanda);


        imageViewAdd = (ImageView) findViewById(R.id.imageView_adicionarItem_add);
        imageViewSub = (ImageView) findViewById(R.id.imageView_adicionarItem_retirar);
    }

}
