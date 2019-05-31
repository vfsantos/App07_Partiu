package br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.RESULT_RESUMO_FINALIZADO;
import static br.com.app07_partiu.Model.Item.itemListToArray;
import static br.com.app07_partiu.Util.Util.doubleToReal;

//TODO Classe PLACEHOLDER
public class ResumoCardapioGarcomActivity extends AppCompatActivity {
    //ListView
    ListView listViewItensResumo;


    //Button
    Button buttonAdicionarMaisItens;
    Button buttonFinalizarPedido;


    //TextView;
    TextView textViewTotal;
    TextView textViewTotalValor;

    ConstraintLayout constraintLayout12;

    Context context;
    Intent intent;
    Item[] itensAdicionar;
    Comanda comanda;

    List<Item> itensComanda;

    public static final String RETORNO_ITENS_COMANDA = "ResumoCardapio.ItensRequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_resumo_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        intent = this.getIntent();

        comanda = (Comanda) intent.getSerializableExtra(CardapioGarcomActivity.COMANDA);
        itensAdicionar = (Item[]) intent.getSerializableExtra(CardapioGarcomActivity.ITENS_ADICIONAR);

        for (Item i : itensAdicionar) {
            Log.d("TESTES", "ResumoCardapio.ItensRecebidos.Item=" + i.toString());
        }

        inicializarComponentes();
        textViewTotalValor.setText(calcularTotal());
        carregarItens();


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
        ResumoCardapioGarcomAdapter adapter = new ResumoCardapioGarcomAdapter(itensAdicionar, this);
        listViewItensResumo.setAdapter(adapter);
    }

    private void inicializarComponentes() {
        //ListView
        listViewItensResumo = (ListView) findViewById(R.id.listview_cardapioresumo);

        //Button
        buttonFinalizarPedido = (Button) findViewById(R.id.button_cardapioResumoGarcom_finalizar);

        //TextView
        textViewTotal = (TextView) findViewById(R.id.textView_cardapioResumoGarcom_total);
        textViewTotalValor = (TextView) findViewById(R.id.textView_cardapioResumoGarcom_totalvalor);
    }


    private void criarPedidos() {
        buttonFinalizarPedido.setEnabled(false);
        if (Connection.isConnected(this)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itensComanda = ComandaNetwork.createItemPedido(Connection.URL, itensAdicionar, comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(context, "Itens Adicionados!", Toast.LENGTH_LONG).show();
                                              Log.d("TESTES", "Itens Adicionados à comanda com sucesso");
                                              Intent intent = new Intent();
                                              intent.putExtra(RETORNO_ITENS_COMANDA, itemListToArray(itensComanda));
                                              setResult(RESULT_RESUMO_FINALIZADO, intent);
                                              finish();
                                          }
                                      }
                        );
                    } catch (IOException e) {

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(context, "ERRO INTERNO", Toast.LENGTH_SHORT).show();

                                          }
                                      }
                        );
                        Log.d("TESTES", "IOException; não conseguiu criar pedidos");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void onClickFinalizarCardapio(View view) {
        criarPedidos();
    }
}
