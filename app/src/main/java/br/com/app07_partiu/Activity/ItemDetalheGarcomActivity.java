package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;

public class ItemDetalheGarcomActivity extends AppCompatActivity {
    //Toolbar
    private Toolbar toolbar;


    //TextView
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

    Item item;
    Item[] pedidosRefresh;

    Intent intent;
    Context context;

    private int idComanda;

    public static final String PEDIDOS_REFRESH = "ItemDetalhe.Remover.Pedidos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detalhe_garcom);
        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);


        intent = getIntent();
        context = this;
        item = (Item) intent.getSerializableExtra(ComandaGarcomActivity.ITEM);
        idComanda = (Integer) intent.getSerializableExtra(ComandaGarcomActivity.ID_COMANDA);

        textViewNomeItem.setText(item.getNome());
        textViewValorItem.setText(item.getValorString());
        textViewDetalhesItem.setText("Descrição Placeholder de Item_Descricao");

        textViewQuantidade.setText("");
        textViewQuantidadeValor.setText("");

        String status = "";
        switch (item.getStatusPedido()) {
            case "N":
                status = "Não Selecionado";
                break;

            case "S":
                status = "Selecionado";
                break;

            case "P":
                status = "Pago";
                break;

            default:
                status = "heh, se fudeu";
                break;

        }

        textViewStatusValor.setText(status);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Fazer verificação antes de remover
                removerPedido();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                break;
            default:break;
        }
        return true;
    }


    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                                            //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_itemdetalhecomandagarcom_titulopagina);     //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }

    private void removerPedido() {
        if (Connection.isConnected(this)) {
            //TODO consertar progressBarTime
//            progressBarTime.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                pedidosRefresh = ComandaNetwork.removerPedidoComanda(Connection.URL, item.getIdPedido(), idComanda);
                                //TODO verificar erro
                                Log.d("TESTES", "Removeu pedido id " + item.getIdPedido());
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      Intent intent = new Intent();
                                                      intent.putExtra(PEDIDOS_REFRESH, pedidosRefresh);
                                                      setResult(ComandaGarcomActivity.RESULT_PEDIDO_REMOVIDO, intent);
                                                      finish();
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "Erro no webservice ou na conexão");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, "Erro no webservice ou na conexão", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
        }
    }

    private void implementarComponentes() {
        //Toolbar
        toolbar                    = (Toolbar) findViewById(R.id.toolbar);


        //TextView
        textViewNomeItem           = (TextView) findViewById(R.id.textView_itemDetalhes_nome);
        textViewDetalhesItem       = (TextView) findViewById(R.id.textView_itemDetalhes_detalhes);
        textViewValorItem          = (TextView) findViewById(R.id.textView_itemDetalhe_valorItem);
        textViewAlgumaObservacao   = (TextView) findViewById(R.id.textView_itemDetalhes_algumaObservacao);
        textViewDetalhesObservacao = (TextView) findViewById(R.id.textView_itemDetalhes_detalheObservacao);
        textViewQuantidade         = (TextView) findViewById(R.id.textView_itemDetalhe_quantidade);
        textViewQuantidadeValor    = (TextView) findViewById(R.id.textView_itemDetalhes_quantidadeValor);
        textViewStatus             = (TextView) findViewById(R.id.textView_itemDetalhes_status);
        textViewStatusValor        = (TextView) findViewById(R.id.textView_itemDetalhes_statusValor);


        buttonRemover              = (Button) findViewById(R.id.button_itemDetalhes_remover);
        buttonVoltar               = (Button) findViewById(R.id.button_itemDetalhes_voltar);
    }

}
