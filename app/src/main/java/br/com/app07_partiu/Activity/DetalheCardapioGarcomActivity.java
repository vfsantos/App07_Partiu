package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.ITEM_DETALHE;
import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.RESULT_DETALHE_RETORNADO;
import static br.com.app07_partiu.Model.ItemComandaGarcomConvertView.listToArray;

public class DetalheCardapioGarcomActivity extends AppCompatActivity {

    public static final String ITENS_NOVOS = "DetalheCardapioGarcomActivity.ItensNovos";

    ItemComandaGarcomConvertView itemRecebido;
    ItemComandaGarcomConvertView[] itensNovos;

    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cardapio_garcom);
        context = this;
        intent = this.getIntent();

        itemRecebido = (ItemComandaGarcomConvertView) intent.getSerializableExtra(ITEM_DETALHE);



        // Quando finalizar, finalizarDetalhe()
        finalizarDetalhe();

    }

    private void finalizarDetalhe() {
        // TODO remover variavel placeholder
        int qtd = 3;
        itensNovos = new ItemComandaGarcomConvertView[qtd];
        for (int i = 0; i < qtd; i++) {
            String observacao = "placeholder"; //get do adapter
            ItemComandaGarcomConvertView novo = itemRecebido;
            //TODO adicionar Observaçao ao Model
            // novo.setObservacao(observacao);
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
