package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS;
import static br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity.RESULT_SAIUDACOMANDA;
import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.USUARIO;

public class CodigoComandaClienteActivity extends AppCompatActivity{


    //Toolbar
    private  Toolbar toolbar;


    //BottomNavigationView
    private BottomNavigationView bottomNavigationView;

    //TextView
    private TextView textViewDescricao1;
    private TextView textViewDescricao2;
    private TextView textViewInserirCodigo;


    //ImageView
    private ImageView imageViewCode;


    //EditText
    private EditText editTextCodigoComanda;


    //Button
    private Button buttonEntrarComanda;

    public static final String COMANDA = "br.com.app07_partiu.CodigoComandaClienteActivity.comanda";
    public static final String RESTAURANTE = "br.com.app07_partiu.CodigoComandaClienteActivity.restaurante";

    public static final String ITENS = "br.com.app07_partiu.CodigoComandaClienteActivity.itens";
    public static final String CLIENTE = "br.com.app07_partiu.CodigoComandaClienteActivity.cliente";
    public static final String USUARIO_IDS = "CodigoComandaClienteActivity.UsuariosId";

    public static final String DATA_ATUALIZACAO_COMANDA = "CodigoComandaCliente.DataAtualizacao";


    //Objeto
    private Comanda comanda;
    private Context context;
    private Usuario cliente;


    //Intent
    private Intent intentComanda;
    private Intent intent;

    //Array
    public Item[] itens;
    private int[] idUsuario;

    private View viewSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_comanda_cliente);
        implentarComponentes();
        setSupportActionBar(toolbar);

        context = this;
        intent = getIntent();

        viewSnackbar = findViewById(R.id.codigoComandaClienteActivityView);

        cliente = (Usuario) intent.getSerializableExtra(USUARIO);


        //BottomNavigationBar
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
//                        Intent b = new Intent(CodigoComandaClienteActivity.this, PerfilClienteActivity.class);
//                        b.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(b);
                        Util.showManutencaoDialog(context);

                        break;
                }
                return false;
            }
        });

        editTextCodigoComanda.setText("OAF01");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Util.logoff(context);
            }
            default:break;
        }
        return true;
    }

    private void getComandaPedidos(final String codigo) {
        intentComanda = new Intent(context, ComandaMesaClienteActivity.class);
        if (Connection.isConnected(this, viewSnackbar)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comanda = ComandaNetwork.getComandaByCodigo(Connection.URL, codigo);
                                itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                idUsuario = ComandaNetwork.getIdsUsuarioComanda(Connection.URL, comanda.getId());
                                ComandaNetwork.insertUsuarioComanda(Connection.URL, cliente.getId(), comanda.getId());
                                final String dataAtualizacao = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        intentComanda.putExtra(CLIENTE, cliente);
                                        intentComanda.putExtra(COMANDA, comanda);
                                        intentComanda.putExtra(ITENS, itens);
                                        intentComanda.putExtra(USUARIO_IDS, idUsuario);
                                        intentComanda.putExtra(DATA_ATUALIZACAO_COMANDA, dataAtualizacao);
                                        startActivityForResult(intentComanda,0);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("TESTES", "CodigoComandaClienteActivity: IOException getComandaPedidos'");
                                Util.showSnackbar(viewSnackbar,R.string.snackbar_erro_backend);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "CodigoComandaClienteActivity: Código Comanda Inválida");
                                Util.showSnackbar(viewSnackbar,"Comanda Inválida!");
                            }
                        }
                    }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case RESULT_PEDIDOSFINALIZADOS:
                Intent a = new Intent(CodigoComandaClienteActivity.this, ExplorarClienteActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(a);
                break;

            case RESULT_SAIUDACOMANDA:
                Intent b= new Intent(CodigoComandaClienteActivity.this, ExplorarClienteActivity.class);
                b.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(b);
                break;

        }
    }

    private void implentarComponentes() {
        //Toolbar
        toolbar               = (Toolbar) findViewById(R.id.toolbar);

        //TextView
        textViewDescricao1    = (TextView) findViewById(R.id.textView_codigoComandaDescricao1);
        textViewDescricao2    = (TextView) findViewById(R.id.textView_codigoComandaDescricao2);
        textViewInserirCodigo = (TextView) findViewById(R.id.textView_codigoComandaInserirCodido);


        //EditText
        editTextCodigoComanda = (EditText) findViewById(R.id.editTexte_codigoComandaCodigo);


        //ImageView
        imageViewCode         = (ImageView) findViewById(R.id.imageView_codigocomandaclienteCodigo);


        //Button
        buttonEntrarComanda = (Button) findViewById(R.id.button_codigoComandaEntrar);
        buttonEntrarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editTextCodigoComanda.getText().toString();
                Log.d("TESTES", "Cod: "+codigo);
                getComandaPedidos(codigo);
            }
        });

        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);
    }

}
