package br.com.app07_partiu.Activity.FinalizarComandaGarcom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Activity.ExplorarClienteDetalhesActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class FinalizarComandaGarcomActivity extends AppCompatActivity {

    public static final int RESULT_COMANDA_FINALIZADA = 4321;

    //Toolbar
    private Toolbar toolbar;


    //ListView
    private ListView listViewItensFinalizar;

    private Button buttonFinalizarComanda;


    //TextView;
    private TextView textViewTotalValor;

    private Context context;
    private Intent intent;
    private Item[] itensFinalizar;
    private Comanda comanda;

    private View viewSnackbar;

    private FinalizarComandaGarcomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_comanda_garcom);

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

        context = this;
        intent = this.getIntent();
        viewSnackbar = findViewById(R.id.finalizarComandaGarcomActivityView);

        comanda = (Comanda) intent.getSerializableExtra(ComandaGarcomActivity.COMANDA);
        itensFinalizar = (Item[]) intent.getSerializableExtra(ComandaGarcomActivity.ITENS_FINALIZAR);

        inicializarComponentes();
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                  //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                                       //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_finalizarcomandagarcom_titulopagina);  //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }

    private String calcularTotal() {
        double total = 0;
        for (Item item : itensFinalizar) {
            if (item.getUsuariosPedido().size() > 0) {
                double aPagar = 100;
                for (Usuario u : item.getUsuariosPedido()) {
                    if (u.getStatusPedido().equals("P")) {
                        aPagar -= u.getPorcPedido();
                    }
                }

                total += item.getValor()*aPagar/100;

            } else {
                total += item.getValor();
            }
        }
        return doubleToReal(total);
    }

    private void carregarItens() {
        //Recycler com itens da cardapio do restaurante
        itensFinalizar = Util.formatItens(itensFinalizar);
        textViewTotalValor.setText(calcularTotal());

        adapter = new FinalizarComandaGarcomAdapter(itensFinalizar, this);
        listViewItensFinalizar.setAdapter(adapter);

        /*
        listViewItensFinalizar.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intentItemDetalheFinalizar = new Intent(context, ItemResumoCardapioGarcomActivity.class);
                intentItemDetalheFinalizar.putExtra(ITEM, itensFinalizar[position]);
                startActivityForResult(intentItemDetalheFinalizar, 0);
            }
        });
*/
    }

    private void inicializarComponentes() {
        //Toolbar
        toolbar                = (Toolbar) findViewById(R.id.toolbar);


        //ListView
        listViewItensFinalizar = (ListView) findViewById(R.id.listview_finalizarComandaGarcom);

        //Button
        buttonFinalizarComanda = (Button) findViewById(R.id.button_finalizarComandaGarcom_finalizar);

        //TextView
        textViewTotalValor     = (TextView) findViewById(R.id.textView_finalizarComandaGarcom_totalvalor);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* switch (resultCode) {
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
                if (itensAdicionar.length == 0) {
                    setResult(RESULT_SEMPEDIDOS);
                    finish();
                } else {
                    carregarItens();
                }
                break;

            default:
                break;

        }*/
    }

    public void onClickFinalizarCardapio(View view) {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Confirmar Pagamento");
        builder.setMessage("Confirmar pagamento e finalizar a comanda?");

        builder.setCancelable(true);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TESTES", "DialogClicked: Yes");
                finalizarPedidosComanda();
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

    private void finalizarPedidosComanda(){
        buttonFinalizarComanda.setEnabled(false);
        if (Connection.isConnected(this, viewSnackbar)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.finalizarPedidosComanda(Connection.URL, comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              setResult(RESULT_COMANDA_FINALIZADA);
                                              finish();
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                        Log.d("TESTES", "finalizarPedidosComanda: IOException pedidos");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
