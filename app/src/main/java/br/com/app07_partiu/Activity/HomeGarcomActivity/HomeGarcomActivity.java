package br.com.app07_partiu.Activity.HomeGarcomActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;

public class HomeGarcomActivity extends AppCompatActivity {

    public static final String COMANDA = "br.com.app07_partiu.HomeGarcomActivity.comanda";
    public static final String RESTAURANTE = "br.com.app07_partiu.HomeGarcomActivity.restaurante";
    public static final String PEDIDOS = "br.com.app07_partiu.HomeGarcomActivity.pedidos";
    public static final String USUARIO_IDS = "HomeGarcomActivity.UsuariosId";


    //AlertDialog / Buider
    private AlertDialog alertaProximaSprint;
    private AlertDialog.Builder alertaNumeroMesa;

    //Intent
    public Intent intentNovaComanda;
    public Intent intentUsuario;
    public Intent intentComanda;
    public Intent intentItensComanda;


    //Objeto
    private ComandaConvertView[] comandas;
    public Item[] pedidos;
    private Restaurante restaurante;
    private Usuario garcom;
    private Context context;
    private Comanda comanda;

    //String
    private String[] mesas;
    private String resultado;

    //int
    private int idGarcom;
    private int mesaSelecionadaAlert;
    private int[] idUsuario;


    //ListView
    ListView listViewComandas;


    //Toolbar
    private Toolbar toolbar;

    //FAB
    private FloatingActionButton fab;
    private Button buttonCriarComanda;


    //Snackbar
    private Snackbar snackbarComandaCriada;


    //SwipeRefreshLayout
    private SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_garcom);

        inicializarComponentes();

        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshComandas();
                pullToRefresh.stopNestedScroll();
            }
        });


        context = this;
        Intent intent = getIntent();

        restaurante = (Restaurante) intent.getSerializableExtra(LoginActivity.RESTAURANTE);
        comandas = (ComandaConvertView[]) intent.getSerializableExtra(LoginActivity.COMANDAS);
        garcom = (Usuario) intent.getSerializableExtra(LoginActivity.USUARIO);
//        Log.d("TESTES", comandas.toString());


        String[] sTemp = new String[restaurante.getQtdMesas()];
        for (int i = 0; i < restaurante.getQtdMesas(); i++) {
            sTemp[i] = String.valueOf(i + 1);
        }
        mesas = sTemp;

        loadComandas();


        buttonCriarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNovaComanda();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshComandas();
    }

    private void loadComandas(){
        listViewComandas = (ListView) findViewById(R.id.list_view_comanda_garcom);
        final HomeGarcomAdapter adapter = new HomeGarcomAdapter(comandas, this);
        listViewComandas.setAdapter(adapter);
        listViewComandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listViewComandas.setEnabled(false);
                ComandaConvertView comandaCV = (ComandaConvertView) adapter.getItem(position);
                int idComanda = comandaCV.getId();
                Log.d("TESTES", "Carregando comanda de id: " + idComanda);
                visualizarComanda(idComanda);

            }

        });
    }

    private void refreshComandas(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            comandas = ComandaNetwork.buscarComandas(Connection.URL, garcom.getId(), 'A');
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  loadComandas();
                                                  pullToRefresh.setRefreshing(false);
                                              }
                                          }
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("TESTES", "ResfreshComandas(): Erro no webservice ou na conexão");
                            Toast.makeText(context, "Erro Conexão ou Webservice!", Toast.LENGTH_LONG).show();
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


    //Alert com númerodas mesas
    private void alertNovaComanda() {
        alertaNumeroMesa = new AlertDialog.Builder(this);
        alertaNumeroMesa.setTitle(R.string.title_alert_criar_comanda);


        alertaNumeroMesa.setItems(mesas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mesaSelecionadaAlert = which + 1;
                criarComanda(garcom.getId(), mesaSelecionadaAlert);

            }
        });


        alertaNumeroMesa.setNegativeButton(R.string.btn_alert_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                fecharAlertNovaComanda();
            }
        });

        alertaProximaSprint = alertaNumeroMesa.create();
        alertaProximaSprint.show();

    }

    //Criar comadanda
    public void criarComanda(final int idGarcom, final int numeroDaMesa) {
        intentNovaComanda = new Intent(context, ComandaGarcomActivity.class);
        if (Connection.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        comanda = ComandaNetwork.createComanda(Connection.URL, idGarcom, numeroDaMesa);
                        pedidos = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              //String codigo = comanda.getCodigoComanda();
                                              //snackbarComandaCriada = Snackbar.make(findViewById(R.id.constraintLayoutHomeGarcom), codigo, Snackbar.LENGTH_LONG);
                                              //snackbarComandaCriada.show();
                                              intentNovaComanda.putExtra(COMANDA, comanda);
                                              intentNovaComanda.putExtra(PEDIDOS, pedidos);
                                              intentNovaComanda.putExtra(RESTAURANTE, restaurante);
                                              intentNovaComanda.putExtra(USUARIO_IDS, new int[0]);
                                              Log.d("TESTES", "criarComanda: Comanda id " + comanda.getId() + " criada");
                                              startActivity(intentNovaComanda);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        Log.d("TESTES", "criarComanda: IOException");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Log.d("TESTES", "criarComanda: JSONException");
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(context, "Comanda de mesa " + numeroDaMesa + " já existe!", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                        );
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    //ver comanda
    public void visualizarComanda(final int idComanda) {
        intentComanda = new Intent(context, ComandaGarcomActivity.class);
        if (Connection.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        comanda = ComandaNetwork.getComandaById(Connection.URL, idComanda);
                        pedidos = ComandaNetwork.buscarPedidosComanda(Connection.URL, idComanda);
                        idUsuario = ComandaNetwork.getIdsUsuarioComanda(Connection.URL, idComanda);

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              intentComanda.putExtra(COMANDA, comanda);
                                              intentComanda.putExtra(PEDIDOS, pedidos);
                                              intentComanda.putExtra(RESTAURANTE, restaurante);
                                              intentComanda.putExtra(USUARIO_IDS, idUsuario);
                                              startActivity(intentComanda);
                                          }
                                      }
                        );


                    } catch (IOException e) {
                        Log.d("TESTES", "visualizarComanda: IOException");
                        e.printStackTrace();
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
        }


    }

    //fecha o alertNovaComanda quando clicar no cancelar
    private void fecharAlertNovaComanda() {
        final Dialog dialog   = alertaNumeroMesa.show();
        final Handler handler = new Handler();
        dialog.dismiss();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // verificar se a caixa de diálogo está visível
                if (dialog.isShowing()) {
                    // fecha a caixa de diálogo
                    dialog.dismiss();
                }
            }
        };

        alertaNumeroMesa.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 10000);
    }

    public void inicializarComponentes() {

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Button
        buttonCriarComanda = (Button) findViewById(R.id.button_homegarcom_criarComanda);


        //SwipeRefreshLayout
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
    }


}