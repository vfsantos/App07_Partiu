package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

public class AdicionarItemGarcomActivity extends AppCompatActivity {
    //TextView
    private TextView textViewTituloPagina;
    private TextView textViewNomeItem;
    private TextView textViewDetalhesItem;
    private TextView textViewValorItem;
    private TextView textViewAlgumaObservacao;
    private TextView textViewQuantidade;


    //EditText
    private EditText editTextObservacao;


    //Button
    private Button buttonRemover;
    private Button buttonVoltar;
    private Button buttonAdicionarItem;


    //ImageView
    private ImageView imageViewAdd;
    private ImageView imageViewSub;


    //int
    private int quantidade;

    Item item;

    Intent intent;
    Context context;

    public static final String ITENS_RETORNADOS = "AdicionarItemGarcom.ItensRetornados";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaComponentes();

        intent = getIntent();
        context = this;
        item = (Item) intent.getSerializableExtra(CardapioGarcomActivity.ITEM);

        textViewNomeItem.setText(item.getNome());
        textViewValorItem.setText(item.getValorString());
        textViewDetalhesItem.setText("DETALHE PLACEHOLDER");
        textViewQuantidade.setText("1");
        quantidade = 1;

    }

    private void inicializaComponentes() {
        //TextView
        textViewTituloPagina = (TextView) findViewById(R.id.textView_adicionarItemGarcom_tituloPage);
        textViewNomeItem = (TextView) findViewById(R.id.textView_adicionarItemGarcom_nome);
        textViewDetalhesItem = (TextView) findViewById(R.id.textView_adicionarItemGarcom_detalhes);
        textViewValorItem = (TextView) findViewById(R.id.textView_adicionarItemGarcom_valorItem);
        textViewAlgumaObservacao = (TextView) findViewById(R.id.textView_adicionarItemGarcom_algumaObservacao);
        textViewQuantidade = (TextView) findViewById(R.id.textView_adicionarItem_quantidade);

        //EditText
        editTextObservacao = (EditText) findViewById(R.id.editText_adicionarItemGarcom_observacao);

        //Button
        buttonAdicionarItem = (Button) findViewById(R.id.button_adicionarItem_addComanda);

        //ImageView
        imageViewAdd = (ImageView) findViewById(R.id.imageView_adicionarItem_add);
        imageViewSub = (ImageView) findViewById(R.id.imageView_adicionarItem_retirar);
    }

    public void onClickAdicionarQuantidade(View v) {
        if(quantidade<9) {
            quantidade++;
            String mensagem = Integer.toString(quantidade);
            textViewQuantidade.setText(mensagem);
        }
    }

    public void onClickRemoverQunatidade(View v) {
        if(quantidade>2) {
            quantidade--;
            String mensagem = Integer.toString(quantidade);
            textViewQuantidade.setText(mensagem);
        }
    }

    public void retornarItens(View view) {
        Item[] itensRetornar = new Item[quantidade];
        for(int i = 0; i < quantidade; i++){
            itensRetornar[i] = item;
        }
        Intent returnIntent = new Intent();
        Log.d("TESTES", "AdicionarItemGarcom.qtdItensRetornadso="+itensRetornar.length);
        returnIntent.putExtra(ITENS_RETORNADOS,itensRetornar);
        setResult(CardapioGarcomActivity.RESULT_DETALHE_RETORNADO, returnIntent);
        finish();

    }
}