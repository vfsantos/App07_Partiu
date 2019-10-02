package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.USUARIO;

public class HistoricoComandasActivity extends AppCompatActivity {

    public static final String HISTORICOCOMANDAS = "br.com.app07_partiu.Activity.HistoricoComandasActivity.novaComanda";


    //ConstraintLayout
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutFechar;


    //TextView
    private TextView textViewTituloPage;


    //ImageView
    private ImageView imageViewNovo;


    //Context
    private Context context;


    //Intent
    private Intent intentToCodigoComanda;
    private Intent intent;


    //Objeto
    private Usuario cliente;


    //BottomNavigationView
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_comandas);
        implentarComponentes();

        intent = getIntent();
        cliente = (Usuario) intent.getSerializableExtra(USUARIO);
        System.out.println(cliente.toString());

        context = this;

        //BottomNavigationBar
        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setSelectedItemId(R.id.menu_comanda);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        Intent a = new Intent(HistoricoComandasActivity.this, ExplorarClienteActivity.class);
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

    }

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
        imageViewNovo       = (ImageView) findViewById(R.id.imageview_historicoComanda_novo);


        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);
    }

}
