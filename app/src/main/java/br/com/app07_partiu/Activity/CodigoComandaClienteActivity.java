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
import java.util.List;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;

public class CodigoComandaClienteActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Context context;

    //TextView
    private TextView textViewTitulo;
    private TextView textViewDescricao;


    //EditText
    private EditText editTextCodigoComanda;


    //Button
    private Button buttonEntrarComanda;




    private AlertDialog alerta;
    public static final String COMANDA = "br.com.app07_partiu.CodigoComandaClienteActivity.comanda";
    public static final String ITENS = "br.com.app07_partiu.CodigoComandaClienteActivity.itens";
    public Comanda comanda;
    private Context contexto;

    public Intent intentComanda;

    Item[] itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_comanda_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        implentarComponentes();
        context = this;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        Intent a = new Intent(CodigoComandaClienteActivity.this, ExplorarClienteActivity.class);
                        startActivity(a);
                        break;
                    case R.id.menu_comanda:
                        break;
                    case R.id.menu_perfil:
                        Intent b = new Intent(CodigoComandaClienteActivity.this,  PerfilClienteActivity.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
    }


    private void getComandaPedidos(final String codigo) {
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comanda = ComandaNetwork.getCodComanda(Connection.URL, codigo);
                                itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                runOnUiThread(new Runnable() {
                                    public void run() {
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
                                Log.e("TESTES", "Retornou 'Comanda Inválido!'");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(contexto, "Comanda Inválida!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
        }
    }

    //functions para a ComandaClienteActivity
//    private void selecionarPedido(final Item item) {
//        if (Connection.isConnected(this)) {
//            new Thread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//
//                                final String resultadoSelecionar = ComandaNetwork.selecionarPedido(Connection.URL, item.getIdPedido());
//                                if (resultadoSelecionar != "") {
//                                    itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
//                                    runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            switch (resultadoSelecionar) {
//                                                case "selecionado": // Selecionou e nenhum outro usuario selecionou
//                                                    Toast.makeText(contexto, "Item Selecionado!", Toast.LENGTH_LONG).show();
//                                                    break;
//                                                case "cancelado": // Não selecionou, item cancelado
//                                                    Toast.makeText(contexto, "O item não existe masi!", Toast.LENGTH_LONG).show();
//                                                    break;
//                                                case "dividindo": // Selecionou, retornou Nomes de usuários com quem vai dividindo
//                                                    Toast.makeText(contexto, "Item Selecionado e sendo Dividido!", Toast.LENGTH_LONG).show();
//                                                    break;
//                                                default:
//                                                    Toast.makeText(contexto, "Deu ruim!", Toast.LENGTH_LONG).show();
//                                                    Log.d("TESTES", "Erro selecionarPedido (dentro do SWITCH");
//                                                    break;
//
//                                            }
//
////                                        reloadPedidos()
//                                        }
//                                    });
//                                }else{
//                                    runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            Toast.makeText(contexto, "Deu ruim!", Toast.LENGTH_LONG).show();
//                                            Log.d("TESTES", "Erro selecionarPedido (no ELSE");
//                                        }
//                                    });
//                                }
//                            } catch (IOException e) {
//                                Log.e("TESTES", "IOException selecioanarPedidos'");
//                                e.printStackTrace();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                //TODO ver oq acontece quando 2 pegam ao msm tempo
//                                Log.e("TESTES", "--");
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        Toast.makeText(contexto, "--", Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
//                        }
//                    }).start();
//        }
//    }

    private void getPedidos() {
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String dataComandaAtualizacao ="";
                                String novaData = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());

                                if (!novaData.equals(dataComandaAtualizacao)){
                                    itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                    dataComandaAtualizacao = novaData;

                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            reloadPedidos();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                Log.e("TESTES", "IOException getComandaPedidos'");
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }
    }

    private void reloadPedidos(){};

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
                Log.d("TESTES","Erro aqui no implementar");
//                getCodComanda(codigo);
            }
        });


        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
