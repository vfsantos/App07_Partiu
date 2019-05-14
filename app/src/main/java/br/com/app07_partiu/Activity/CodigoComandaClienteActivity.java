package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;

public class CodigoComandaClienteActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewDescricao;
    private EditText editTextCodigoComanda;
    private Button buttonEntrarComanda;
    public Intent intentComanda;
    private AlertDialog alerta;
    private BottomNavigationView bottomNavigationView;
    public static final String URL = "http://10.0.2.2:8080/partiu"; //emulador
    public static final String COMANDA = "br.com.app07_partiu.CodigoComandaClienteActivity.comanda";
    public static final String ITENS = "br.com.app07_partiu.CodigoComandaClienteActivity.itens" ;
    public Comanda comanda;
    private Context contexto;

    List<Item> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_comanda_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contexto=this;
        textViewTitulo = (TextView) findViewById(R.id.text_view_codigo_comanda_titulo);
        textViewDescricao = (TextView) findViewById(R.id.text_view_codigo_comanda_descricao);
        editTextCodigoComanda = (EditText) findViewById(R.id.edit_texte_codigo_comanda_codigo);
        buttonEntrarComanda = (Button) findViewById(R.id.button_codigo_comanda_entrar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom);
        buttonEntrarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editTextCodigoComanda.getText().toString();
                getCodComanda(codigo);
            }
        });
    }


    public void getCodComanda(final String codigo){

        if(ComandaNetwork.isConnected(this)) {

            //TODO consertar progressBarTime
            //TODO parte Cliente
            //progressBarTime.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comanda = ComandaNetwork.getCodComanda(URL, codigo);
                                System.out.println(comanda.toString());

                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        if(comanda.getCodigoComanda().equals(codigo)){
                                            getPedComanda(comanda);
                                        }
                                    }
                                });


                            } catch (IOException e) {
                                e.printStackTrace();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("TESTES", "Retornou 'Comanda Inválido!'");
                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        Toast.makeText(contexto, "Comanda Inválido!",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();


        } else {
            Toast.makeText(this, "Rede inativa", Toast.LENGTH_SHORT).show();
        }
    }



    public void getPedComanda(final Comanda comanda){

        final int idComanda = comanda.getId();
        intentComanda = new Intent(this, ComandaClienteActivity.class);


        if(ComandaNetwork.isConnected(this)) {

            //TODO consertar progressBarTime
            //TODO parte Cliente
            //progressBarTime.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                itens  = ComandaNetwork.getPedidosComanda(URL, idComanda);
                                Log.d("TESTES", comanda.toString());


                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intentComanda.putExtra(COMANDA,comanda);
                                                      //intentComanda.putExtra(PEDIDOS, itens.toArray());
                                                      startActivity(intentComanda);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("TESTES", "Retornou 'Comanda Inválido!'");
                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        Toast.makeText(contexto, "Comanda Inválido!",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
        } else {
            Toast.makeText(this, "Rede inativa", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButtonEntrarComanda (View view) {
//
    }

}
