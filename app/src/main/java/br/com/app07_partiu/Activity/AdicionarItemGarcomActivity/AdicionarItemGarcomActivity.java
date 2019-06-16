package br.com.app07_partiu.Activity.AdicionarItemGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class AdicionarItemGarcomActivity extends AppCompatActivity {
    //Toolbar
    private Toolbar toolbar;


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

    private ConstraintLayout viewObservacao;


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
        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

        intent = getIntent();
        context = this;
        item = (Item) intent.getSerializableExtra(CardapioGarcomActivity.ITEM);

        textViewNomeItem.setText(item.getNome());
        textViewValorItem.setText(item.getValorString());
        textViewDetalhesItem.setText(item.getDetalhe());
        textViewQuantidade.setText("1");
        quantidade = 1;

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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                          //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                               //Ativar o botão
            getSupportActionBar().setTitle(R.string.text_adicionaritemgarcom_titulopagina); //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }


    public void onClickAdicionarQuantidade(View v) {
        if(quantidade<9) {
            quantidade++;
            viewObservacao.setVisibility(View.INVISIBLE);
            String mensagem = Integer.toString(quantidade);
            textViewQuantidade.setText(mensagem);
        }
    }

    public void onClickRemoverQunatidade(View v) {
        if(quantidade>1) {
            quantidade--;
            if (quantidade == 1) viewObservacao.setVisibility(View.VISIBLE);
            String mensagem = Integer.toString(quantidade);
            textViewQuantidade.setText(mensagem);
        }
    }

    public void retornarItens(View view) {
        Item[] itensRetornar = new Item[quantidade];
        for(int i = 0; i < quantidade; i++){
            itensRetornar[i] = item;
        }
        if (quantidade == 1){
            itensRetornar[0].setObsPedido(editTextObservacao.getText().toString());
        }
        Intent returnIntent = new Intent();
        Log.d("TESTES", "AdicionarItemGarcom.qtdItensRetornadso="+itensRetornar.length);
        returnIntent.putExtra(ITENS_RETORNADOS,itensRetornar);
        setResult(CardapioGarcomActivity.RESULT_DETALHE_RETORNADO, returnIntent);
        finish();
    }

    private void implementarComponentes() {
        //Toolbar
        toolbar                  = (Toolbar) findViewById(R.id.toolbar);


        //TextView
        textViewNomeItem         = (TextView) findViewById(R.id.textView_adicionarItemGarcom_nome);
        textViewDetalhesItem     = (TextView) findViewById(R.id.textView_adicionarItemGarcom_detalhes);
        textViewValorItem        = (TextView) findViewById(R.id.textView_adicionarItemGarcom_valorItem);
        textViewAlgumaObservacao = (TextView) findViewById(R.id.textView_adicionarItemGarcom_algumaObservacao);
        textViewQuantidade       = (TextView) findViewById(R.id.textView_adicionarItem_quantidade);


        //EditText
        editTextObservacao       = (EditText) findViewById(R.id.editText_adicionarItemGarcom_observacao);

        //Button
        buttonAdicionarItem      = (Button) findViewById(R.id.button_adicionarItem_addComanda);

        //ImageView
        imageViewAdd             = (ImageView) findViewById(R.id.imageView_adicionarItem_add);
        imageViewSub             = (ImageView) findViewById(R.id.imageView_adicionarItem_retirar);

        viewObservacao           = (ConstraintLayout) findViewById(R.id.constraintLayout4);
    }
}