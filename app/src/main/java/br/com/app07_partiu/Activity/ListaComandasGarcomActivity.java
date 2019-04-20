package br.com.app07_partiu.Activity;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.ComandaGarcomAdapter;
import br.com.app07_partiu.ComandaVaziaGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.GarcomNetwork;
import br.com.app07_partiu.R;

public class ListaComandasGarcomActivity extends AppCompatActivity {

    public static final String URL = "http://192.168.50.102:8080/partiu";
    public static final String COMANDA = "br.com.app07_partiu.ListaComandasGarcomActivity.comanda";

    //AlertDialog / Buider
    private AlertDialog alertaProximaSprint;
    private AlertDialog.Builder alertaNumeroMesa;

    //Spinner
    private Spinner spinnerNumeroMesa;

    //Intent
    public Intent intentNovaComanda;

    //Objeto
    Comanda[] comandas;
    Restaurante restaurante;

    //String
    private String[] mesas;
    private String resultado;

    //int
    private int mesa;

    private Usuario garcom;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comandas_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNovaComanda();
            }
        });
        context = this;
        Intent intent = getIntent();

        garcom = (Usuario) intent.getSerializableExtra(MainActivity.USUARIO);
        comandas = (Comanda[]) intent.getSerializableExtra(MainActivity.COMANDAS);
        restaurante = (Restaurante) intent.getSerializableExtra(MainActivity.RESTAURANTE);

        String[] sTemp= new String[restaurante.getQtdMesas()];
        for(int i = 0; i<restaurante.getQtdMesas(); i++){
            sTemp[i] = String.valueOf(i+1);
        }
        mesas = sTemp;

        alertaNumeroMesa = new AlertDialog.Builder(this);

        ListView listView = (ListView) findViewById(R.id.list_view_comandas);
        ComandaGarcomAdapter adapter = new ComandaGarcomAdapter(comandas, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                /* manda para a tela de detalhe
                Intent intent = new Intent(atividade, DetalhesComandaGarcomActivity.class);
                intent.putExtra(COMANDA, comandas[position]);

                startActivity(intent);
                */

                //mensagem informando que a funcionalidade estara disponível em breve
                alertProximaSprint();

            }

        });

    }

    //gera uma nova comanda
    private void alertNovaComanda(){
        //Cria o gerador do AlertDialog
        alertaNumeroMesa = new AlertDialog.Builder(this);
        //define o titulo
        alertaNumeroMesa.setTitle(R.string.title_alert_criar_comanda).setItems(mesas, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mesa = which;
            }
        });

        alertaNumeroMesa.setSingleChoiceItems(mesas, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //define o comportamento do botão criar comanda
        alertaNumeroMesa.setPositiveButton(R.string.btn_alert_criar_comanda, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                criarComanda(mesa);
            }
        });
        //define o comportamento do botão cancelar
        alertaNumeroMesa.setNegativeButton(R.string.btn_alert_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                fecharAlertNovaComanda();
            }
        });
        //cria o AlertDialog
        alertaProximaSprint = alertaNumeroMesa.create();
        //Exibe
        alertaProximaSprint.show();
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
    private void alertProximaSprint(){
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

    //criar nova comanda
    public void criarComanda(final int mesa) {
        intentNovaComanda = new Intent(context, ComandaVaziaGarcomActivity.class);
        if(GarcomNetwork.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GarcomNetwork.createComanda(URL,garcom.getId(), mesa);
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              intentNovaComanda.putExtra(COMANDA, resultado);
                                              startActivity(intentNovaComanda);
                                          }
                                      }
                        );
                    } catch (IOException e)  {
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





}
