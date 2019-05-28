package br.com.app07_partiu.Activity.ExplorarClienteActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.Arrays;
import java.util.List;


import br.com.app07_partiu.Activity.AdicionarItemGarcomActivity;
import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.ExplorarClienteDetalhesActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Activity.PerfilClienteActivity;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.R;

public class ExplorarClienteActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //BottomNavigationView
    private BottomNavigationView bottomNavigationView;


    //TextView - Título
    private TextView textViewRecomendacaoDiaSemana;
    private TextView textViewRecomendacaoMaisVisitados;
    private TextView textViewRecomendacaoVisitadosRecentemente;
    private TextView textViewRecomendacaoEspecialidadeUsuario;
    private TextView textViewRecomendacaoRestauranteAvaliado;


    //TextView - Descrição
    private TextView textViewRecomendacaoDiaSemanaDescricao;
    private TextView textViewRecomendacaoMaisVisitadosDescricao;
    private TextView textViewRecomendacaoVisitadosDescricao;
    private TextView textViewRecomendacaoEspecialidadeUsuarioDescricao;
    private TextView textViewRecomendacaoRestauranteAvaliadoDescricao;


    //RecyclerView - Carrossel
    private MultiSnapRecyclerView recyclerViewRecomendacaoDiaSemana;
    private MultiSnapRecyclerView recyclerViewRecomendacaoMaisVisitados;
    private MultiSnapRecyclerView recyclerViewRecomendacaoVisitadosRecentemente;
    private MultiSnapRecyclerView recyclerViewRecomendacaoEspecialidadeUsuario;
    private MultiSnapRecyclerView recyclerViewRecomendacaoRestauranteAvaliado;


    //ExplorarClienteAdapter
    ExplorarClienteAdapter recomendacaoDiaSemanaAdapter;
    ExplorarClienteAdapter recomendacaoMaisVisitadosAdapter;
    ExplorarClienteAdapter recomendacaoVisitadosRecentementeAdapter;
    ExplorarClienteAdapter recomendacaoEspecialidadeUsuarioAdapter;
    ExplorarClienteAdapter recomendacaoRestauranteAvaliadoAdapter;


    //LinearLayoutManager;
    private LinearLayoutManager linearLayoutManagerDiaSemana;
    private LinearLayoutManager linearLayoutManagerMaisVisitados;
    private LinearLayoutManager linearLayoutManagerVisitadosRecentemente;
    private LinearLayoutManager linearLayoutManagerEspecialidadeUsuario;
    private LinearLayoutManager linearLayoutManagerRestauranteAvaliado;


    private ConstraintLayout constraintLayoutDiaSemana;
    private ConstraintLayout constraintLayoutMaisVisitados;
    private ConstraintLayout constraintLayoutVisitadodsRecentemente;
    private ConstraintLayout constraintLayoutEspecialidadeUsuario;
    private ConstraintLayout constraintLayoutRestauranteAvaliado;


    //List
    List listaRecomendacaoDiaSemanda;
    List listaRecomendacaoMaisVisitados;
    List listaRecomendacaoVisitadosRecentemente;
    List listaRecomendacaoEspecialidadeUsuario;
    List listaRecomendacaoRestauranteAvaliado;


    //Array
    private Restaurante[] recomendacoesDiaSemana;
    private Restaurante[] recomendacoesMaisVisitados;
    private Restaurante[] recomendacoesVisitadosRecentemente;
    private Restaurante[] recomendacoesEspecialidadeUsuario;
    private Restaurante[] recomendacoesRestauranteAvaliado;


    //Objetos
    private Usuario cliente;
    private Intent intent;
    private Context context;


    //Intent
    private Intent intentRecomendacaoDetalhe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar_cliente);



        context = this;
        intent = getIntent();
        cliente = (Usuario) intent.getSerializableExtra(LoginActivity.USUARIO);

        recomendacoesDiaSemana = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_DIASEMANA);
        recomendacoesMaisVisitados = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_MAISVISITADOS);
        recomendacoesVisitadosRecentemente = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_VISITADOSRECENTEMENTE);
        recomendacoesEspecialidadeUsuario = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_ESPECIALIDADEUSUARIO);
        recomendacoesRestauranteAvaliado = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_RESTAURANTEAVALIADO);

        implentarComponentes();
        popularRecomendacoes();

        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        break;
                    case R.id.menu_comanda:
                        Intent a = new Intent(ExplorarClienteActivity.this, CodigoComandaClienteActivity.class);
                        startActivity(a);
                        break;
                    case R.id.menu_perfil:
                        Intent b = new Intent(ExplorarClienteActivity.this, PerfilClienteActivity.class);
                        startActivity(b);
                        break;
                }

                return false;
            }
        });

    }

    private void popularRecomendacoes() {
        //RecomendacaoDiaSemanda
        if (recomendacoesDiaSemana == null) {
            constraintLayoutDiaSemana.removeAllViews();
        } else {
            listaRecomendacaoDiaSemanda = Arrays.asList(recomendacoesDiaSemana);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoDiaSemanaAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoDiaSemanda);
            linearLayoutManagerDiaSemana = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoDiaSemana.setLayoutManager(linearLayoutManagerDiaSemana);
            recyclerViewRecomendacaoDiaSemana.setAdapter(recomendacaoDiaSemanaAdapter);
        }

        //RecomendacaoMaisVisitados
        if (recomendacoesMaisVisitados == null) {
            constraintLayoutMaisVisitados.removeAllViews();
        } else {
            listaRecomendacaoMaisVisitados = Arrays.asList(recomendacoesMaisVisitados);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoMaisVisitadosAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoMaisVisitados);
            linearLayoutManagerMaisVisitados = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoMaisVisitados.setLayoutManager(linearLayoutManagerMaisVisitados);
            recyclerViewRecomendacaoMaisVisitados.setAdapter(recomendacaoMaisVisitadosAdapter);
        }

        //RecomendacaoVisitadosRecentemente
        if (recomendacoesVisitadosRecentemente == null) {
            constraintLayoutVisitadodsRecentemente.removeAllViews();
        } else {
            listaRecomendacaoVisitadosRecentemente = Arrays.asList(recomendacoesVisitadosRecentemente);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoVisitadosRecentementeAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoVisitadosRecentemente);
            linearLayoutManagerVisitadosRecentemente = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoVisitadosRecentemente.setLayoutManager(linearLayoutManagerVisitadosRecentemente);
            recyclerViewRecomendacaoVisitadosRecentemente.setAdapter(recomendacaoVisitadosRecentementeAdapter);
        }

        //RecomendacaoEspecialidadeUsuario
        if (recomendacoesEspecialidadeUsuario == null) {
            constraintLayoutEspecialidadeUsuario.removeAllViews();
        } else {
            listaRecomendacaoEspecialidadeUsuario = Arrays.asList(recomendacoesEspecialidadeUsuario);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoEspecialidadeUsuarioAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoEspecialidadeUsuario);
            linearLayoutManagerEspecialidadeUsuario = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoEspecialidadeUsuario.setLayoutManager(linearLayoutManagerEspecialidadeUsuario);
            recyclerViewRecomendacaoEspecialidadeUsuario.setAdapter(recomendacaoEspecialidadeUsuarioAdapter);
        }

        //RecomendacaoRestauranteAvaliado
        if (recomendacoesRestauranteAvaliado == null) {
            constraintLayoutRestauranteAvaliado.removeAllViews();
        } else {
            listaRecomendacaoRestauranteAvaliado = Arrays.asList(recomendacoesRestauranteAvaliado);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoRestauranteAvaliadoAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoRestauranteAvaliado);
            linearLayoutManagerRestauranteAvaliado = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoRestauranteAvaliado.setLayoutManager(linearLayoutManagerRestauranteAvaliado);
            recyclerViewRecomendacaoRestauranteAvaliado.setAdapter(recomendacaoRestauranteAvaliadoAdapter);
        }

    }


    private void implentarComponentes() {
        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);

        //TextView - Título
        textViewRecomendacaoDiaSemana = (TextView) findViewById(R.id.textView_recomendacaoDiaSemana);
        textViewRecomendacaoEspecialidadeUsuario = (TextView) findViewById(R.id.textView_recomendacaoEspecialidadeUsuario);
        textViewRecomendacaoMaisVisitados = (TextView) findViewById(R.id.textView_recomendacaoMaisVisitados);
        textViewRecomendacaoRestauranteAvaliado = (TextView) findViewById(R.id.textView_recomendacaoRestauranteAvaliado);
        textViewRecomendacaoVisitadosRecentemente = (TextView) findViewById(R.id.textView_recomendacaoVisitadosRecentemente);

        //TextView - Descrição
        textViewRecomendacaoDiaSemanaDescricao = (TextView) findViewById(R.id.textView_recomendacaoDiaSemana_descricao);
        textViewRecomendacaoEspecialidadeUsuarioDescricao = (TextView) findViewById(R.id.textView_recomendacaoEspecialidadeUsuario_descricao);
        textViewRecomendacaoMaisVisitadosDescricao = (TextView) findViewById(R.id.textView_recomendacaoMaisVisitados_descricao);
        textViewRecomendacaoRestauranteAvaliadoDescricao = (TextView) findViewById(R.id.textView_recomendacaoRestauranteAvaliado_descricao);
        textViewRecomendacaoVisitadosDescricao = (TextView) findViewById(R.id.textView_recomendacaoVisitadosRecentemente_descricao);

        //RecyclerView - Carrossel
        recyclerViewRecomendacaoDiaSemana = (MultiSnapRecyclerView) findViewById(R.id.recyclerView_recomendacaoDiaSemana);
        recyclerViewRecomendacaoEspecialidadeUsuario = (MultiSnapRecyclerView) findViewById(R.id.recyclerView_recomendacaoEspecialidadeUsuario);
        recyclerViewRecomendacaoMaisVisitados = (MultiSnapRecyclerView) findViewById(R.id.recyclerView_recomendacaoMaisVisitados);
        recyclerViewRecomendacaoRestauranteAvaliado = (MultiSnapRecyclerView) findViewById(R.id.recyclerView_recomendacaoRestauranteAvaliado);
        recyclerViewRecomendacaoVisitadosRecentemente = (MultiSnapRecyclerView) findViewById(R.id.recyclerView_recomendacaoVisitadosRecentemente);

        //ConstraintLayout
        constraintLayoutDiaSemana = (ConstraintLayout) findViewById(R.id.constraintLayout_diaSemana);
        constraintLayoutEspecialidadeUsuario = (ConstraintLayout) findViewById(R.id.constraintLayout_especialidadeUsuario);
        constraintLayoutMaisVisitados = (ConstraintLayout) findViewById(R.id.constraintLayout_maisVisitados);
        constraintLayoutRestauranteAvaliado = (ConstraintLayout) findViewById(R.id.constraintLayout_restauranteAvaliado);
        constraintLayoutVisitadodsRecentemente = (ConstraintLayout) findViewById(R.id.constraintLayout_visitadodsRecentemente);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
                Intent intent = new Intent(context, PerfilClienteActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }
}
