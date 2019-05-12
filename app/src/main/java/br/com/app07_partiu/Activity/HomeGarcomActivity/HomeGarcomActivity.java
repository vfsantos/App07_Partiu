package br.com.app07_partiu.Activity.HomeGarcomActivity;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaGarcomActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Mesa;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.R;

public class HomeGarcomActivity extends AppCompatActivity {

    //public static final String URL = "http://10.0.2.2:8080/partiu"; //emulador
    //public static final String URL = "http://192.168.200.2:8080/partiu";
    public static final String URL = LoginActivity.URL; //
    public static final String COMANDA = "br.com.app07_partiu.HomeGarcomActivity.comanda";

    //AlertDialog / Buider
    private AlertDialog alertaProximaSprint;
    private AlertDialog.Builder alertaNumeroMesa;

    //Intent
    public Intent intentNovaComanda;
    public Intent intentUsuario;
    public Intent intentComanda;
    public Intent intentRestaurante;

    //Objeto
    private ComandaConvertView[] comandas;
    private Restaurante restaurante;
    private Usuario garcom;
    private Context context;
    private Comanda comanda;
    private Mesa mesa;

    //String
    private String[] mesas;
    private String resultado;

    //int
    private int idGarcom;

    //ListView
    ListView listViewComandas;

    //Toolbar
    private Toolbar toolbar;

    //FAB
    private FloatingActionButton fab;

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

            }
        });


        context = this;
        Intent intent = getIntent();

        restaurante = (Restaurante) intent.getSerializableExtra(LoginActivity.RESTAURANTE);
        comandas = (ComandaConvertView[]) intent.getSerializableExtra(LoginActivity.COMANDAS);
        garcom = (Usuario) intent.getSerializableExtra(LoginActivity.USUARIO);
       // mesa = new Mesa();
//        Log.d("TESTES", comandas.toString());


        String[] sTemp= new String[restaurante.getQtdMesas()];
        for(int i = 0; i<restaurante.getQtdMesas(); i++){
            sTemp[i] = String.valueOf(i+1);
        }
        mesas = sTemp;
        System.out.println("Numero de mesa aqui" + mesas);


        listViewComandas = (ListView) findViewById(R.id.list_view_comanda_garcom);
        HomeGarcomAdapter adapter = new HomeGarcomAdapter(comandas, this);
        listViewComandas.setAdapter(adapter);
        listViewComandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }

        });

    }


    //Alert com númerodas mesas
    private void alertNovaComanda() {

        mesa = new Mesa();

        alertaNumeroMesa = new AlertDialog.Builder(this);
        alertaNumeroMesa.setTitle(R.string.title_alert_criar_comanda)
                .setItems(mesas, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mesa.setNumeroDaMesa(which+1);
                        criarComanda(garcom.getId(), mesa.getNumeroDaMesa());
                    }
                 }).setPositiveButton(R.string.btn_alert_criar_comanda, new DialogInterface.OnClickListener() {
                     @Override
                    public void onClick(DialogInterface arg0, int arg1) {



                    }
                }).setNegativeButton(R.string.btn_alert_cancelar, new DialogInterface.OnClickListener() {
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
        if (ComandaNetwork.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        comanda = ComandaNetwork.createComanda(URL, idGarcom, numeroDaMesa);
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              //String codigo = comanda.getCodigoComanda();
                                              //snackbarComandaCriada = Snackbar.make(findViewById(R.id.constraintLayoutHomeGarcom), codigo, Snackbar.LENGTH_LONG);
                                              //snackbarComandaCriada.show();
                                              intentNovaComanda.putExtra(COMANDA, comanda);
                                              startActivity(intentNovaComanda);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast.makeText(this, "Rede inativa", Toast.LENGTH_SHORT).show();
        }
    }

    //fecha o alertNovaComanda quando clicar no cancelar
    private void fecharAlertNovaComanda() {
        final Dialog dialog = alertaNumeroMesa.show();
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



    //exibe um alert informanda que a funcionalidade ´so esta disponível na próxima sprint
    private void alertProximaSprint() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(R.string.title_alert_disponivel_em_breve);
        //define a mensagem
        builder.setMessage(R.string.subtitle1_alert_disponivel_em_breve);
        //define um botão como positivo
        builder.setPositiveButton(R.string.btn_alert_criar_comanda, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alertaProximaSprint = builder.create();
        //Exibe
        alertaProximaSprint.show();
    }

    public void inicializarComponentes() {

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FAB criar comanda
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNovaComanda();
            }
        });

        //SwipeRefreshLayout
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);

    }

}