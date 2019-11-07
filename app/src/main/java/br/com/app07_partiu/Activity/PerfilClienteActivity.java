package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARCOMANDA;
import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARUSUARIO;

public class PerfilClienteActivity extends AppCompatActivity {


    public static final String USUARIO = "br.com.app07_partiu.Activity.PerfilClienteActivity.Cliente";


    //ConstraintLayout
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutImagemPerfil;
    private ConstraintLayout constraintLayoutInfoCliente;
    private ConstraintLayout constraintLayoutButtonSair;


    //ImageView
    private ImageView imageViewPerfil;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewNome;
    private TextView textViewCpf;
    private TextView textViewdataNascimento;
    private TextView textViewEmail;
    private TextView textViewTelefone;


    //Button
    private Button buttonSair;


    //BottomNavigationBar
    private BottomNavigationView bottomNavigationView;


    //Objetos
    public Usuario cliente;
    public Comanda[] comandas;


    //Context
    private Context context;


    //Intent
    private Intent intent;
    private Intent intentComanda;

    private View viewSnackbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        implementarComponentes();

        context = this;
        intent = getIntent();
        cliente = (Usuario) intent.getSerializableExtra(EXPLORARUSUARIO);
        comandas = (Comanda[]) intent.getSerializableExtra(EXPLORARCOMANDA);

        viewSnackbar = findViewById(R.id.explorarClienteActivityView);

        textViewNome.setText(cliente.getNome());
        textViewCpf.setText("CPF: " + String.valueOf(cliente.getCpf()));
        textViewdataNascimento.setText("Data de nascimento: " + cliente.getDta_nascimento());
        textViewEmail.setText("E-mail: " + cliente.getEmail());
        textViewTelefone.setText("Telefone: " + String.valueOf(cliente.getTelefone()));


        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setSelectedItemId(R.id.menu_perfil);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        Intent a = new Intent(PerfilClienteActivity.this, ExplorarClienteActivity.class);
                        a.putExtra(USUARIO, cliente);
                        a.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(a);
                    case R.id.menu_comanda:
                        visualizarHistoricoComanda(cliente.getCpf());
                        break;
                    case R.id.menu_perfil:
                        break;
                }
                return false;
            }
        });

    }

    public void visualizarHistoricoComanda(final String cpf) {
        intentComanda = new Intent(context, HistoricoComandasActivity.class);
        if (Connection.isConnected(this, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        comandas = ComandaNetwork.getComandasById(Connection.URL, cliente.getId());

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              intentComanda.putExtra(EXPLORARCOMANDA, comandas);
                                              intentComanda.putExtra(EXPLORARUSUARIO, cliente);
                                              intentComanda.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                              startActivityForResult(intentComanda, 0);
                                          }
                                      }
                        );


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "ExplorarCliente: IOException visualizarHistoricoComanda");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }



    public void onClickPerfilClienteSair(View view) {
        Util.logoff(context);
    }



    private void implementarComponentes() {
        //ConstraintLayout
        constraintLayoutHeader       = (ConstraintLayout) findViewById(R.id.constraintLayout_perfilcliente_header);
        constraintLayoutImagemPerfil = (ConstraintLayout) findViewById(R.id.constraintLayout_perfilcliente_imagemperfil);
        constraintLayoutInfoCliente  = (ConstraintLayout) findViewById(R.id.constraintLayout_perfilcliente_infocliente);
        constraintLayoutButtonSair   = (ConstraintLayout) findViewById(R.id.constraintLayout_perfilcliente_botaosair);


        //ImageView
        imageViewPerfil              = (ImageView) findViewById(R.id.imageView_perfilcliente_imagemperfil);


        //TextView
        textViewTitulo               = (TextView) findViewById(R.id.textView_perfilcliente_tituloPagina);
        textViewNome                 = (TextView) findViewById(R.id.textView_perfilcliente_nome);
        textViewCpf                  = (TextView) findViewById(R.id.textView_perfilcliente_cpf);
        textViewdataNascimento       = (TextView) findViewById(R.id.textView_perfilcliente_datanascimento);
        textViewEmail                = (TextView) findViewById(R.id.textView_perfilcliente_email);
        textViewTelefone             = (TextView) findViewById(R.id.textView_perfilcliente_telefone);


        //Button
        buttonSair                   = (Button) findViewById(R.id.button_perfilcliente_sair);


        //BottomNavigationView
        bottomNavigationView         = findViewById(R.id.bottomNavegation);
    }

}
