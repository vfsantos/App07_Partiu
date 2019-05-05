package br.com.app07_partiu.Activity.HomeGarcomActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import android.content.Intent.*;

import br.com.app07_partiu.Activity.ComandaVaziaGarcomActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.R;

public class HomeGarcomActivity extends AppCompatActivity {

    //public static final String URL = "http://10.0.2.2:8080/partiu"; //emulador
    //public static final String URL = "http://192.168.200.2:8080/partiu";
    public static final String URL = "http://192.168.137.1:8080/partiu"; //
    public static final String COMANDA = "br.com.app07_partiu.comanda";

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

    //String
    private String[] mesas;
    private String resultado;

    //int
    private int numeroDaMesa;
    private int idGarcom;

    //ListView
    ListView listViewComandas;

    //Toolbar
    private Toolbar toolbar;

    //FAB
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_garcom);

        inicializarComponentes();

        context = this;



        comandas = (ComandaConvertView[]) intentComanda.getSerializableExtra(LoginActivity.COMANDAS);
        Log.d("TESTES", comandas.toString());


        String[] sTemp= new String[restaurante.getQtdMesas()];
        for(int i = 0; i<restaurante.getQtdMesas(); i++){
            sTemp[i] = String.valueOf(i+1);
        }
        mesas = sTemp;


        listViewComandas = (ListView) findViewById(R.id.list_view_comanda_garcom);
        HomeGarcomAdapter adapter = new HomeGarcomAdapter(comandas, this);
        listViewComandas.setAdapter(adapter);
        listViewComandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                alertProximaSprint();
            }

        });

    }


    //Alert com númerodas mesas
    private void alertNovaComanda() {
        alertaNumeroMesa = new AlertDialog.Builder(this);
        alertaNumeroMesa.setTitle(R.string.title_alert_criar_comanda)
                .setItems(mesas, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        numeroDaMesa = which; //pega o número da mesa
                    }
                });
        alertaNumeroMesa.setSingleChoiceItems(mesas, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertaNumeroMesa.setPositiveButton(R.string.btn_alert_criar_comanda, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //Rebebe objeto usuario
                garcom = (Usuario) intentUsuario.getSerializableExtra(LoginActivity.USUARIO);

                //Solicita a criação de uam comanda nova
                criarComanda(garcom.getId(), numeroDaMesa);
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
        intentNovaComanda = new Intent(context, ComandaVaziaGarcomActivity.class);
        if (ComandaNetwork.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ComandaNetwork.createComanda(URL, idGarcom, numeroDaMesa);
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              intentNovaComanda.putExtra(COMANDA, resultado);
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

    }

}