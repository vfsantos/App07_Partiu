package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
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

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.R;

public class EditarItemComandaActivity extends AppCompatActivity {
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
    private Button buttonRemoverItem;
    private Button buttonAdicionarItem;


    //ImageView
    private ImageView imageViewAdd;
    private ImageView imageViewSub;


    //int
    private int quantidade;

    ItemComandaGarcomConvertView item;

    Intent intent;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item_comanda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaComponentes();
        context = this;
    }

    private void inicializaComponentes() {
        //TextView
        textViewTituloPagina = (TextView) findViewById(R.id.textView_editarItemGarcom_tituloPage);
        textViewNomeItem = (TextView) findViewById(R.id.textView_editarItemGarcom_nome);
        textViewDetalhesItem = (TextView) findViewById(R.id.textView_editarItemGarcom_detalhes);
        textViewValorItem = (TextView) findViewById(R.id.textView_editarItemGarcom_valorItem);
        textViewAlgumaObservacao = (TextView) findViewById(R.id.textView_editarItemGarcom_algumaObservacao);
        texViewQuantidade = (TextView) findViewById(R.id.textView_editarItem_quantidade);


        //EditText
        editTextObservacao = (EditText) findViewById(R.id.editText_editarItemGarcom_observacao);


        //Button
        buttonAdicionarItem = (Button) findViewById(R.id.button_editarItem_addComanda);
        buttonRemoverItem = (Button) findViewById(R.id.button_editarItem_removerComanda);


        //ImageView
        imageViewAdd = (ImageView) findViewById(R.id.imageView_editarItem_add);
        imageViewSub = (ImageView) findViewById(R.id.imageView_editarItem_retirar);
    }


    public void onClickAdicionarQuantidade(View v) {
        quantidade++;
        String mensagem = Integer.toString(quantidade);
        texViewQuantidade.setText(mensagem);
    }


    public void onClickRemoverQunatidade(View v) {
        if(quantidade>= 1) {
            quantidade--;
            String mensagem = Integer.toString(quantidade);
            texViewQuantidade.setText(mensagem);
        }else {
            String mensagem = "0";
            texViewQuantidade.setText(mensagem);
            Snackbar.make(v, "Teste", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }

}



