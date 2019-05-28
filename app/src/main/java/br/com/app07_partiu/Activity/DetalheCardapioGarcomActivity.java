package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.ITEM_DETALHE;
import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.RESULT_DETALHE_RETORNADO;

public class DetalheCardapioGarcomActivity extends AppCompatActivity {

    public static final String ITENS_NOVOS = "DetalheCardapioGarcomActivity.ItensNovos";

    Item itemRecebido;
    Item[] itensNovos;

    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cardapio_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        intent = this.getIntent();

        itemRecebido = (Item) intent.getSerializableExtra(ITEM_DETALHE);

        finalizarDetalhe();

    }

    private void finalizarDetalhe() {
        // TODO remover variavel placeholder
        int qtd = 3;
        itensNovos = new Item[qtd];
        for (int i = 0; i < qtd; i++) {
            String observacao = "placeholder"; //get do adapter
            Item novo = itemRecebido;
            //TODO adicionar Observaçao ao Model
//             novo.setObservacao(observacao);
            itensNovos[i] = novo;
        }

        retornarItens();
    }

    private void retornarItens() {
        Intent intentRetorno = new Intent();
        intentRetorno.putExtra(ITENS_NOVOS, itensNovos);
        setResult(RESULT_DETALHE_RETORNADO);
        finish();
    }
}
