package br.com.app07_partiu.Activity.CardapioGarcomActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.app07_partiu.Activity.AdicionarItemGarcomActivity;
import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity.ResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity.RESULT_PEDIDOS_CRIADOS;
import static br.com.app07_partiu.Model.Item.itemListToArray;

public class CardapioGarcomActivity extends AppCompatActivity {

    public static final String ITEM = "br.com.app07_partiu.CardapioGarcomActivity.item";
    public static final String ITENS_ADICIONAR = "CardapioGarcomActivity.ItensAdicionar";
    public static final String COMANDA = "CardapioGarcomActivity.Comanda";

    public static final int RESULT_RESUMO_FINALIZADO = 1000;
    public static final int RESULT_DETALHE_RETORNADO = 2000;


    FloatingActionButton buttonCardapioFinalizar;
    Intent intent;
    private Intent intentItem;

    Context context;

    Intent intentResumoAddItens;
    Intent intentDetalheCardapioGarcom;
    Intent intentAdicionarItemPedido;

    //Do intent
    private Comanda comanda;
    private Item[] itensRestaurante;

    List<Item> itensAdicionar;

    //RecyclerView
    private ListView listViewItensCardapio;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        context = this;

        buttonCardapioFinalizar = findViewById(R.id.button_cardapio_finalizar);
        buttonCardapioFinalizar.setEnabled(false);
        buttonCardapioFinalizar.setBackgroundTintList(getResources().getColorStateList(R.drawable.btn_floating_tintlist));

        comanda = (Comanda) intent.getSerializableExtra(ComandaGarcomActivity.COMANDA);
        itensRestaurante = (Item[]) intent.getSerializableExtra(ComandaGarcomActivity.ITENS_RESTAURANTE);

        itensAdicionar = new ArrayList<Item>();
        //OnClickListener detalheCardapioGarcom();

        if (itensRestaurante != null) {
            carregarItens();
        }

        //TODO Trocar icone do FloatingActionButton

    }

    private void carregarItens() {
        //Recycler com itens da cardapio do restaurante
        listViewItensCardapio = (ListView) findViewById(R.id.listView_cardapioGarcom);
        CardapioGarcomAdapter adapter = new CardapioGarcomAdapter(itensRestaurante, this);
        listViewItensCardapio.setAdapter(adapter);
        listViewItensCardapio.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                intentItem = new Intent(context, AdicionarItemGarcomActivity.class);
                intentItem.putExtra(ITEM, itensRestaurante[position]);
                startActivityForResult(intentItem, RESULT_DETALHE_RETORNADO);
            }
        });
    }

    // Adiciona item à list de itens a adicionar (list enviada ao Resumo)
    private void addItem(Item item) {
        itensAdicionar.add(item);
        Log.d("TESTES", "CardapioGarcom.addItem="+item.toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Retornando sinal para fechar Activity de Cardapio (Resumo confirmado; volta à Comanda)
        Log.d("TESTES", "CardapioGarcomActivity.resultCode = "+resultCode);
        if (resultCode == RESULT_RESUMO_FINALIZADO) {

            setResult(RESULT_PEDIDOS_CRIADOS, data);
            finish();

            //Retornando itens do AdicionarItemGarcomActivity
        } else if (resultCode == RESULT_DETALHE_RETORNADO) {
            buttonCardapioFinalizar.setEnabled(true);
            Item[] novosItens = (Item[]) data.getSerializableExtra(AdicionarItemGarcomActivity.ITENS_RETORNADOS);
            Log.d("TESTES", "CardapioGarcom.qtdItensRetornadosNovos="+novosItens.length);
            for (Item item : novosItens) {
                addItem(item);
            }
        }
    }

    //click finalizar
    public void onClickButtonCardapioFinalizar(View view){
        Log.d("TESTES", "CardapioGarcomActivity.onClickButtonCardapioFinalizar");
        intentResumoAddItens = new Intent(context, ResumoCardapioGarcomActivity.class);
        intentResumoAddItens.putExtra(ITENS_ADICIONAR, itemListToArray(itensAdicionar));
        intentResumoAddItens.putExtra(COMANDA, comanda);
        startActivityForResult(intentResumoAddItens, RESULT_RESUMO_FINALIZADO);

    }


}


/* ------------BACKUP------------------

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TESTES", "RequestCode " + requestCode);
        Log.d("TESTES", "ResultCode " + resultCode);
        Log.d("TESTES", "Data " + data);
        //result code deve ser o mesmo para fechar
        //data é a intent anterior; dela podemos pegar os dados necessários com data.getSerializableExtra()
        //também dá pra dar finish caso necessário
        // a prox activity precisa ser iniciar com um requestCode: startActivityForResult(intent, 1000);

        //Retornando sinal para fechar Activity de Cardapio (Resumo confirmado; volta à Comanda)
        if (resultCode == RESULT_RESUMO_FINALIZADO) {
            setResult(RESULT_PEDIDOS_CRIADOS);
            finish();

        } else if (resultCode == RESULT_DETALHE_RETORNADO) {
            Item[] novosItens = (Item[]) data.getSerializableExtra(ITENS_NOVOS);
            for (Item item : novosItens) {
                addItem(item);
            }
        }
    }
 */