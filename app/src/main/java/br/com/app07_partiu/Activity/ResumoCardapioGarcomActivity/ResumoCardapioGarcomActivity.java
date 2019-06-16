package br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.com.app07_partiu.Activity.AdicionarItemGarcomActivity.AdicionarItemGarcomActivity;
import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Activity.ItemResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.RESULT_RESUMO_FINALIZADO;
import static br.com.app07_partiu.Model.Item.itemListToArray;
import static br.com.app07_partiu.Util.Util.doubleToReal;

public class ResumoCardapioGarcomActivity extends AppCompatActivity {

    public static final String RETORNO_ITENS_COMANDA = "ResumoCardapio.ItensRequest";
    public static final String ITEM = "ResumoCardapio.ItemDEtalhe";
    public static final String POSICAO = "ResumoCardapio.Posicao";
    public static final int RESULT_SEMPEDIDOS = 1234;

    //Toolbar
    private Toolbar toolbar;


    //ListView
    private ListView listViewItensResumo;


    //Button
    private Button buttonAdicionarMaisItens;
    private Button buttonFinalizarPedido;


    //TextView;
    private TextView textViewTotal;
    private TextView textViewTotalValor;

    private ConstraintLayout constraintLayout12;

    private Context context;
    private Intent intent;
    private Item[] itensAdicionar;
    private Comanda comanda;

    private List<Item> itensComanda;

    private View viewSnackbar;

    private ResumoCardapioGarcomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_resumo_garcom);

        inicializarComponentes();
        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

        context = this;
        intent = this.getIntent();
        viewSnackbar = findViewById(R.id.resumoCardapioGarcomActivityView);

        comanda = (Comanda) intent.getSerializableExtra(CardapioGarcomActivity.COMANDA);
        itensAdicionar = (Item[]) intent.getSerializableExtra(CardapioGarcomActivity.ITENS_ADICIONAR);

        for (Item i : itensAdicionar) {
            Log.d("TESTES", "ResumoCardapio.ItensRecebidos.Item=" + i.toString());
        }
        carregarItens();


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
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                     //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                          //Ativar o botão
            getSupportActionBar().setTitle(R.string.text_resumocomandagarcom_titulopagina);  //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }


    private String calcularTotal() {
        double total = 0;
        for (Item item : itensAdicionar) {
            total += item.getValor();
        }
        return doubleToReal(total);
    }

    private void carregarItens() {
        //Recycler com itens da cardapio do restaurante


        adapter = new ResumoCardapioGarcomAdapter(itensAdicionar, this);
        listViewItensResumo.setAdapter(adapter);
        listViewItensResumo.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intentItemDetalheResumo = new Intent(context, ItemResumoCardapioGarcomActivity.class);
                intentItemDetalheResumo.putExtra(ITEM, itensAdicionar[position]);
                intentItemDetalheResumo.putExtra(POSICAO, position);
                startActivityForResult(intentItemDetalheResumo, 999);
            }
        });

        textViewTotalValor.setText(calcularTotal());



    }

    private void inicializarComponentes() {
        //Toolbar
        toolbar               = (Toolbar)  findViewById(R.id.toolbar);

        //ListView
        listViewItensResumo   = (ListView) findViewById(R.id.listview_cardapioresumo);

        //Button
        buttonFinalizarPedido = (Button)   findViewById(R.id.button_cardapioResumoGarcom_finalizar);

        //TextView
        textViewTotal         = (TextView) findViewById(R.id.textView_cardapioResumoGarcom_total);
        textViewTotalValor    = (TextView) findViewById(R.id.textView_cardapioResumoGarcom_totalvalor);
    }


    private void criarPedidos() {
        buttonFinalizarPedido.setEnabled(false);
        if (Connection.isConnected(this, viewSnackbar)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itensComanda = ComandaNetwork.createItemPedido(Connection.URL, itensAdicionar, comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Log.d("TESTES", "Itens Adicionados à comanda com sucesso");
                                              Intent intent = new Intent();
                                              intent.putExtra(RETORNO_ITENS_COMANDA, itemListToArray(itensComanda));
                                              setResult(RESULT_RESUMO_FINALIZADO, intent);
                                              finish();
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                        Log.d("TESTES", "ResumoCardapioGarcom: IOException criarPedidos; não conseguiu criar pedidos");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void onClickFinalizarCardapio(View view) {
        criarPedidos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ItemResumoCardapioGarcomActivity.RESULT_ALTERAR_PEDIDO:
                int posicaoRetornada = (Integer) data.getSerializableExtra(ItemResumoCardapioGarcomActivity.POSICAO_ALTERAR);
                Item itemRetornado = (Item) data.getSerializableExtra(ItemResumoCardapioGarcomActivity.ITEM_ALTERADO);
                Log.d("TESTES", "ItemResumo.Result: Alterando item posicao=" + posicaoRetornada);

                itensAdicionar[posicaoRetornada] = itemRetornado;
                carregarItens();

                break;

            case ItemResumoCardapioGarcomActivity.RESULT_REMOVER_PEDIDO:
                int posicaoRemover = (Integer) data.getSerializableExtra(ItemResumoCardapioGarcomActivity.POSICAO_REMOVER);
                List<Item> itensTemp = new LinkedList<>(Arrays.asList(itensAdicionar));
                itensTemp.remove(posicaoRemover);
                itensAdicionar = itemListToArray(itensTemp);
                if (itensAdicionar.length == 0){
                    setResult(RESULT_SEMPEDIDOS);
                    finish();
                }else{
                    carregarItens();
                }
                break;

            default:
                break;

        }
    }
}
