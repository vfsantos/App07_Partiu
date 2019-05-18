package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity.RESULT_RESUMO_FINALIZADO;
import static br.com.app07_partiu.Model.ItemComandaGarcomConvertView.listToArray;

//TODO Classe PLACEHOLDER
public class ResumoCardapioGarcomActivity extends AppCompatActivity {
//    private static final String ITENS_ADICIONAR = "CardapioGarcomActivity.ItensAdicionar";

    Intent intent;
    Context context;

//    Intent intentResumoAddItens;

    private ItemComandaGarcomConvertView[] itensAdicionar;
    private Comanda comanda;

    int[] idItens;
    String[] obsItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = this.getIntent();
        context = this;

        itensAdicionar = (ItemComandaGarcomConvertView[]) intent.getSerializableExtra(CardapioGarcomActivity.ITENS_ADICIONAR);
        comanda = (Comanda) intent.getSerializableExtra(CardapioGarcomActivity.COMANDA);
        //OnClickListener addItem();

    }


    private void finalizar() {
        // TODO

    }

    private void criarPedidos() {
        if (Connection.isConnected(this)) {
            idItens = new int[itensAdicionar.length];
            obsItens = new String[itensAdicionar.length];

            for (int i = 0; i < itensAdicionar.length; i++) {
                idItens[i] = itensAdicionar[i].getId();
                //TODO pegar do objeto
                obsItens[i] = "PLACEHOLDER";
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.createItemPedido(Connection.URL, idItens, obsItens, comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(context, "Itens Adicionados!", Toast.LENGTH_LONG).show();
                                              Log.d("TESTES", "Itens Adicionados à comanda com sucesso");
                                              setResult(RESULT_RESUMO_FINALIZADO);
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

}
