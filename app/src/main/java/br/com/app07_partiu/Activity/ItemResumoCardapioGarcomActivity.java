package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity.ResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class ItemResumoCardapioGarcomActivity extends AppCompatActivity {

    //TextView
    //item
    private TextView textViewNomeItem;
    private TextView textViewDetalhesItem;
    private TextView textViewValorItem;
    private EditText editTextObservacao;

    //Button
    private Button buttonRemover;
    private Button buttonVoltar;

    Item item;
    int posicaoItem;

    Intent intent;
    Context context;

    private View viewSnackbar;

    public static final String POSICAO_REMOVER = "ItemResumoCardapio.PosicaoRemover";
    public static final String POSICAO_ALTERAR = "ItemResumoCardapio.PosicaoAlterar";
    public static final String ITEM_ALTERADO = "ItemResumoCardapio.ItemAlterado";

    public static final int RESULT_REMOVER_PEDIDO = 2000;
    public static final int RESULT_ALTERAR_PEDIDO = 3000;




    private String obsOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_resumo_cardapio_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializaComponentes();

        intent = getIntent();
        context = this;
//        viewSnackbar = findViewById(R.id.itemDetalheGarcomActivityView);

        item = (Item) intent.getSerializableExtra(ResumoCardapioGarcomActivity.ITEM);
        posicaoItem = (Integer) intent.getSerializableExtra(ResumoCardapioGarcomActivity.POSICAO);

        obsOriginal = item.getObsPedido();

        textViewNomeItem.setText(item.getNome());
        textViewValorItem.setText(item.getValorString());
        textViewDetalhesItem.setText(item.getDetalhe());
        editTextObservacao.setText(item.getObsPedido());

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextObservacao.getText().toString().equals(obsOriginal)) {
                    finish();
                } else {


                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(context);

                    builder.setTitle("A observação do pedido foi alterada!");
                    builder.setMessage("Confirmar alteração?");

                    builder.setCancelable(true);
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("TESTES", "DialogClicked: Yes");
                            voltarPedido();
                        }
                    });

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("TESTES", "DialogClicked: No");
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }

            }
        });

        buttonRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(context);

                builder.setTitle("O pedido será excluído!");
                builder.setMessage("Continuar?");

                builder.setCancelable(true);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TESTES", "DialogClicked: Yes");
                        removerPedido();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TESTES", "DialogClicked: No");
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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


    private void removerPedido() {

        Intent intent = new Intent();
        intent.putExtra(POSICAO_REMOVER, posicaoItem);
        setResult(RESULT_REMOVER_PEDIDO, intent);
        finish();

    }

    private void voltarPedido(){

        item.setObsPedido(editTextObservacao.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(POSICAO_ALTERAR, posicaoItem);
        intent.putExtra(ITEM_ALTERADO, item);
        setResult(RESULT_ALTERAR_PEDIDO, intent);
        finish();


    }

    private void inicializaComponentes() {
//        textViewTituloPagina = (TextView) findViewById(R.id.textView_itemDetalhes_tituloPage);
        textViewNomeItem = (TextView) findViewById(R.id.textView_itemResumoCardapio_nome);
        textViewDetalhesItem = (TextView) findViewById(R.id.textView_itemResumoCardapio_detalhes);
        textViewValorItem = (TextView) findViewById(R.id.textView_itemResumoCardapio_valorItem);
        editTextObservacao = (EditText) findViewById(R.id.editText_itemResumoCardapio_observacao);

        buttonRemover = (Button) findViewById(R.id.button_itemResumoCardapio_remover);
        buttonVoltar = (Button) findViewById(R.id.button_itemResumoCardapio_voltar);
    }

}
