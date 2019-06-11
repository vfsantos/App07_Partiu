package br.com.app07_partiu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class PerfilClienteActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar toolbar;


    //BottomNavigationBar
    private BottomNavigationView bottomNavigationView;


    //Objetos
    private Usuario cliente;

    public static final String USUARIO = "PerfilClienteActivity.Cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        implementarComponentes();

        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setSelectedItemId(R.id.menu_explorar);
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
                        Intent b = new Intent(PerfilClienteActivity.this, CodigoComandaClienteActivity.class);
                        b.putExtra(USUARIO, cliente);
                        b.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(b);
                        break;
                    case R.id.menu_perfil:
                        break;
                }
                return false;
            }
        });

    }

    protected void setUpToolbar() {
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                        //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                             //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_perfilcliente_titulopagina); //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }

    private void implementarComponentes() {
        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);
    }

}
