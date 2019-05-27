package br.com.app07_partiu.Activity.ExplorarClienteActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.LoginActivity.RECOMENDACAO_DIA;
import static br.com.app07_partiu.Activity.LoginActivity.RECOMENDACAO_AVALIACAO;
import static br.com.app07_partiu.Activity.LoginActivity.RECOMENDACAO_EMALTA;
import static br.com.app07_partiu.Activity.LoginActivity.RECOMENDACAO_ESPECIALIDADE;
import static br.com.app07_partiu.Activity.LoginActivity.RECOMENDACAO_RECENTE;

public class ExplorarClienteActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Context context;
    private Intent intent;

    Restaurante[] recomendacaoDia, recomendacaoAvaliacao, recomendacaoEmAlta, recomendacaoEspecialidade, recomendacaoRecente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        context = this;

        intent = this.getIntent();

        recomendacaoDia = (Restaurante[]) intent.getSerializableExtra(RECOMENDACAO_DIA);
        recomendacaoAvaliacao = (Restaurante[]) intent.getSerializableExtra(RECOMENDACAO_AVALIACAO);
        recomendacaoEmAlta = (Restaurante[]) intent.getSerializableExtra(RECOMENDACAO_EMALTA);
        recomendacaoEspecialidade = (Restaurante[]) intent.getSerializableExtra(RECOMENDACAO_ESPECIALIDADE);
        recomendacaoRecente = (Restaurante[]) intent.getSerializableExtra(RECOMENDACAO_RECENTE);

        //TODO inserir recomendação nos carroseis

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_explorar: {
                Intent intent = new Intent(context, ExplorarClienteActivity.class);
                startActivity(intent);
            }

            case R.id.menu_comanda: {
                Intent intent = new Intent(context, CodigoComandaClienteActivity.class);
                startActivity(intent);
            }

            case R.id.menu_perfil: {

            }
        }
        return true;
    }
}
