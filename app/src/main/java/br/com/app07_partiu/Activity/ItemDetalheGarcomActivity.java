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
import android.widget.TextView;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.R;

public class ItemDetalheGarcomActivity extends AppCompatActivity {

    //TextView
    private TextView textViewTituloPagina;
    //item
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

    ItemComandaGarcomConvertView item;

    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detalhe_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializaComponentes();

        intent = getIntent();
        context = this;
        item = (ItemComandaGarcomConvertView) intent.getSerializableExtra(ComandaGarcomActivity.ITEM);

        textViewNomeItem.setText(item.getNome());
        textViewValorItem.setText(item.getValorString());
        textViewDetalhesItem.setText("Desrição Placeholder de Item_Descricao");

        textViewQuantidade.setText("");
        textViewQuantidadeValor.setText("");

        String status = "";
        switch(item.getStatusPedido()){
            case "N":
                status ="Não Selecionado por Usuário";
                break;

            case "S":
                status = "Selecionado por Usuário";
                break;

            case "P":
                status = "Item Pago";
                break;

            default:
                status = "heh, se fudeu";
                break;

        }

        textViewStatusValor.setText(status);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                this.clo
            }
        });


    }

    private void inicializaComponentes() {
//        textViewTituloPagina = (TextView) findViewById(R.id.textView_itemDetalhes_tituloPage);
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
