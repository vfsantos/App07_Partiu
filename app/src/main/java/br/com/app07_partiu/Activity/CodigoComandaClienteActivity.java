package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.USUARIO;

public class CodigoComandaClienteActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;

    //TextView
    private TextView textViewTitulo;
    private TextView textViewDescricao;


    //EditText
    private EditText editTextCodigoComanda;


    //Button
    private Button buttonEntrarComanda;

    public static final String COMANDA = "br.com.app07_partiu.CodigoComandaClienteActivity.comanda";
    public static final String ITENS = "br.com.app07_partiu.CodigoComandaClienteActivity.itens";
    public static final String CLIENTE = "br.com.app07_partiu.CodigoComandaClienteActivity.cliente";

    private AlertDialog alerta;
    private Comanda comanda;

    private Intent intentComanda;
    private Intent intent;
    private Context context;

    private Usuario cliente;

    Item[] itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_comanda_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        implentarComponentes();
        context = this;

        intent = getIntent();
        cliente = (Usuario) intent.getSerializableExtra(USUARIO);

        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setSelectedItemId(R.id.menu_comanda);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        Intent a = new Intent(CodigoComandaClienteActivity.this, ExplorarClienteActivity.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(a);
                        break;
                    case R.id.menu_comanda:
                        break;
                    case R.id.menu_perfil:
                        Intent b = new Intent(CodigoComandaClienteActivity.this, PerfilClienteActivity.class);
                        b.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
    }


    private void getComandaPedidos(final String codigo) {
        intentComanda = new Intent(context, ComandaClienteActivity.class);
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comanda = ComandaNetwork.getComandaByCodigo(Connection.URL, codigo);
                                itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                ComandaNetwork.insertUsuarioComanda(Connection.URL, cliente.getId(), comanda.getId());
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        intentComanda.putExtra(CLIENTE, cliente);
                                        intentComanda.putExtra(COMANDA, comanda);
                                        intentComanda.putExtra(ITENS, itens);
                                        startActivity(intentComanda);
                                    }
                                });
                            } catch (IOException e) {
                                Log.e("TESTES", "IOException getComandaPedidos'");
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "Retornou 'Comanda Inválido!'");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, "Comanda Inválida!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
        }
    }



    private void implentarComponentes() {
        //TextView
        textViewTitulo = (TextView) findViewById(R.id.text_view_codigo_comanda_titulo);
        textViewDescricao = (TextView) findViewById(R.id.text_view_codigo_comanda_descricao);

        //EditText
        editTextCodigoComanda = (EditText) findViewById(R.id.edit_texte_codigo_comanda_codigo);

        //Button
        buttonEntrarComanda = (Button) findViewById(R.id.button_codigo_comanda_entrar);
        buttonEntrarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editTextCodigoComanda.getText().toString();
                getComandaPedidos(codigo);
            }
        });

        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);
    }

}
