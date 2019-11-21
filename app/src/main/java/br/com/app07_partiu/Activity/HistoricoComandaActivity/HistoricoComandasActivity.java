package br.com.app07_partiu.Activity.HistoricoComandaActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomAdapter;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteAdapter;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteAdapter;
import br.com.app07_partiu.Activity.FinalizarComandaGarcom.FinalizarComandaGarcomActivity;
import br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity.FinalizarPedidoClienteActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomAdapter;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
import br.com.app07_partiu.Activity.ItemDetalheGarcomActivity;
import br.com.app07_partiu.Activity.LoginClienteActivity;
import br.com.app07_partiu.Activity.PerfilClienteActivity;
import br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity.ResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.Network.RecomendacaoNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARCOMANDA;
import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARUSUARIO;
import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARUSUARIO;
import static br.com.app07_partiu.Util.Util.doubleToReal;

public class HistoricoComandasActivity extends AppCompatActivity {


    public static final String HISTORICOCOMANDAUSUARIO                        = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.usuario";
    public static final String HISTORICOCOMANDAS                         = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.novaComanda";
    public static final String HISTORICOCOMANDASCOMANDA                  = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.comanda";
    public static final String HISTORICOCOMANDASRESTAURANTE              = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.restaurante";
    public static final String HISTORICOCOMANDASPEDIDOS                  = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.pedidos";
    public static final String HISTORICOCOMANDASUSUARIO_IDS              = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.UsuariosId";
    public static final String HISTORICOCOMANDASDATA_ATUALIZACAO_COMANDA = "br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity.DataAtualizacao";


    //ConstraintLayout
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutFechar;


    //TextView
    private TextView textViewTituloPage;


    //ImageView
    private ImageView imageViewNovo;


    //ListView
    private ListView listViewComandas;


    //Context
    private Context context;


    //Intent
    private Intent intentToCodigoComanda;
    private Intent intent;
    private Intent intentComanda;


    //Array
    public Comanda[] comandas;
    public Item[] pedidos;
    public int[] idUsuario;


    //Objeto
    public Comanda comanda;
    public Usuario cliente;
    public Restaurante restaurante;


    //BottomNavigationView
    private BottomNavigationView bottomNavigationView;


    //Snackbar
    private View viewSnackbar;


    //Time
    private Timer timerAtualizacao;


    //String
    private String dataAtualizacao;


    //SwipeRefreshLayout
    private SwipeRefreshLayout pullToRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_comandas);
        implentarComponentes();

        intent = getIntent();
        cliente = (Usuario) intent.getSerializableExtra(EXPLORARUSUARIO);
        comandas = (Comanda[]) intent.getSerializableExtra(EXPLORARCOMANDA);

        context = this;


        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshComandas();
                pullToRefresh.stopNestedScroll();
            }
        });


        //BottomNavigationBar
        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setSelectedItemId(R.id.menu_comanda);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        Intent a = new Intent(context, ExplorarClienteActivity.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(a);
                        break;
                    case R.id.menu_comanda:
                        break;
                    case R.id.menu_perfil:
                        Intent b = new Intent(context, PerfilClienteActivity.class);
                        b.putExtra(EXPLORARUSUARIO, cliente);
                     //   b.putExtra(HISTORICOCOMANDASRESTAURANTE, restaurante);
                     //   b.putExtra(HISTORICOCOMANDASCOMANDA, comanda);
                        b.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(b);
                        //Util.showManutencaoDialog(context);

                        break;
                }
                return false;
            }
        });

        if (comandas!=null) loadComandas();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        refreshComandas();
    }


    private void loadComandas() {
        listViewComandas = (ListView) findViewById(R.id.listView_historicoComanda_listacomanda);
        final HistoricoComandasAdapter adapter = new HistoricoComandasAdapter(comandas, this);
        listViewComandas.setAdapter(adapter);
        listViewComandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listViewComandas.setEnabled(false);
                Comanda comandaCV = (Comanda) adapter.getItem(position);
                int idComanda = comandaCV.getId();
                Log.d("TESTES", "Carregando comanda de id: " + idComanda);
                visualizarComanda(idComanda);
                /*
                if(comandaCV.getStatus().equals("F")){
                    Util.comandaFechada(context);
                } else {
                    visualizarComanda(idComanda);
                }*/


            }

        });
    }


    private void refreshComandas() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            comandas = ComandaNetwork.getComandasById(Connection.URL, cliente.getId());
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  if (comandas!=null)loadComandas();

                                                  pullToRefresh.setRefreshing(false);
                                              }
                                          }
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("TESTES", "ResfreshComandas(): Erro no webservice ou na conexão");
                            Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  pullToRefresh.setRefreshing(false);
                                              }
                                          }
                            );
                        }
                    }
                }).start();

    }


    //ver comanda
    public void visualizarComanda(final int idComanda) {
        intentComanda = new Intent(context, ComandaMesaClienteActivity.class);
        if (Connection.isConnected(this, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        comanda = ComandaNetwork.getComandaById(Connection.URL, idComanda);
                        pedidos = ComandaNetwork.buscarPedidosComanda(Connection.URL, idComanda);
                        idUsuario = ComandaNetwork.getIdsUsuarioComanda(Connection.URL, idComanda);
                        final String dataAtualizacao = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              intentComanda.putExtra(HISTORICOCOMANDAUSUARIO, cliente);
                                              intentComanda.putExtra(HISTORICOCOMANDASCOMANDA, comanda);
                                              intentComanda.putExtra(HISTORICOCOMANDASPEDIDOS, pedidos);
//                                              intentComanda.putExtra(HISTORICOCOMANDASRESTAURANTE, restaurante);
                                              intentComanda.putExtra(HISTORICOCOMANDASUSUARIO_IDS, idUsuario);
                                              intentComanda.putExtra(HISTORICOCOMANDASDATA_ATUALIZACAO_COMANDA, dataAtualizacao);
                                              startActivityForResult(intentComanda, 0);
                                          }
                                      }
                        );


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "HomeGarcom: IOException visualizarComanda");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    } catch (JSONException e) {
                        Log.d("TESTES", "visualizarComanda: JSONException");
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    listViewComandas.setEnabled(true);
                }
            }).start();
        }else listViewComandas.setEnabled(true);


    }

/* não sei se precisa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case FinalizarComandaGarcomActivity.RESULT_COMANDA_FINALIZADA:
                Util.showSnackbar(viewSnackbar, "Comanda finalizada!");
        }
    }

 */


    public void onClickNovaComanda(View view) {
        intentToCodigoComanda = new Intent(HistoricoComandasActivity.this, CodigoComandaClienteActivity.class);
        intentToCodigoComanda.putExtra(HISTORICOCOMANDAS, cliente);
        startActivity(intentToCodigoComanda);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_in);
    }


    private void implentarComponentes() {

        //ConstraintLayout
        constraintLayoutHeader = (ConstraintLayout) findViewById(R.id.constraintLayout_historicoComanda_header);
        constraintLayoutFechar = (ConstraintLayout) findViewById(R.id.constraintLayout_historicoComanda_novo);


        //TextView
        textViewTituloPage     = (TextView) findViewById(R.id.textView_historicoComanda_tituloPagina);


        //ImageView
        imageViewNovo          = (ImageView) findViewById(R.id.imageview_historicoComanda_novo);


        //ListView
        listViewComandas       = (ListView) findViewById(R.id.listView_historicoComanda_listacomanda);


        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);


        //SwipeRefreshLayout
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.listView_historicoComanda_pullToRefresh);
    }

}
