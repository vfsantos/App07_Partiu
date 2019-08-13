package br.com.app07_partiu.Activity.ExplorarClienteActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import br.com.app07_partiu.Activity.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Activity.PerfilClienteActivity;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.RecomendacaoNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class ExplorarClienteActivity extends AppCompatActivity {

    //BottomNavigationView
    private BottomNavigationView bottomNavigationView;


    //TextView - Título
    private TextView textViewRecomendacaoDiaSemana;
    private TextView textViewRecomendacaoMaisVisitados;
    private TextView textViewRecomendacaoVisitadosRecentemente;
    private TextView textViewRecomendacaoEspecialidadeUsuario;
    private TextView textViewRecomendacaoRestauranteAvaliado;
    private TextView textViewRecomendacaoRestauranteAvaliado1;


    //TextView - Descrição
    private TextView textViewRecomendacaoDiaSemanaDescricao;
    private TextView textViewRecomendacaoMaisVisitadosDescricao;
    private TextView textViewRecomendacaoVisitadosDescricao;
    private TextView textViewRecomendacaoEspecialidadeUsuarioDescricao;
    private TextView textViewRecomendacaoRestauranteAvaliadoDescricao;
    private TextView textViewRecomendacaoRestauranteAvaliadoDescricao1;


    //RecyclerView - Carrossel
    private MultiSnapRecyclerView recyclerViewRecomendacaoDiaSemana;
    private MultiSnapRecyclerView recyclerViewRecomendacaoMaisVisitados;
    private MultiSnapRecyclerView recyclerViewRecomendacaoVisitadosRecentemente;
    private MultiSnapRecyclerView recyclerViewRecomendacaoEspecialidadeUsuario;
    private MultiSnapRecyclerView recyclerViewRecomendacaoRestauranteAvaliado;
    private MultiSnapRecyclerView recyclerViewRecomendacaoRestauranteAvaliado1;


    //ExplorarClienteAdapter
    ExplorarClienteAdapter recomendacaoDiaSemanaAdapter;
    ExplorarClienteAdapter recomendacaoMaisVisitadosAdapter;
    ExplorarClienteAdapter recomendacaoVisitadosRecentementeAdapter;
    ExplorarClienteAdapter recomendacaoEspecialidadeUsuarioAdapter;
    ExplorarClienteAdapter recomendacaoRestauranteAvaliadoAdapter;
    ExplorarClienteAdapter recomendacaoRestauranteAvaliadoAdapter1;


    //LinearLayoutManager;
    private LinearLayoutManager linearLayoutManagerDiaSemana;
    private LinearLayoutManager linearLayoutManagerMaisVisitados;
    private LinearLayoutManager linearLayoutManagerVisitadosRecentemente;
    private LinearLayoutManager linearLayoutManagerEspecialidadeUsuario;
    private LinearLayoutManager linearLayoutManagerRestauranteAvaliado;
    private LinearLayoutManager linearLayoutManagerRestauranteAvaliado1;


    private ConstraintLayout constraintLayoutDiaSemana;
    private ConstraintLayout constraintLayoutMaisVisitados;
    private ConstraintLayout constraintLayoutVisitadodsRecentemente;
    private ConstraintLayout constraintLayoutEspecialidadeUsuario;
    private ConstraintLayout constraintLayoutRestauranteAvaliado;
    private ConstraintLayout constraintLayoutRestauranteAvaliado1;


    //List
    List listaRecomendacaoDiaSemanda;
    List listaRecomendacaoMaisVisitados;
    List listaRecomendacaoVisitadosRecentemente;
    List listaRecomendacaoEspecialidadeUsuario;
    List listaRecomendacaoRestauranteAvaliado;
    List listaRecomendacaoRestauranteAvaliado1;


    //Array
    private Restaurante[] recomendacoesDiaSemana;
    private Restaurante[] recomendacoesMaisVisitados;
    private Restaurante[] recomendacoesVisitadosRecentemente;
    private Restaurante[] recomendacoesEspecialidadeUsuario;
    private Restaurante[] recomendacoesRestauranteAvaliado;
    private Restaurante[] recomendacoesRestauranteAvaliado1;


    //Objetos
    private Usuario cliente;
    private Intent intent;
    private Context context;


    //Intent
    private Intent intentRecomendacaoDetalhe;

    public static final String USUARIO = "ExplorarClienteActivity.Cliente";

    private View viewSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar_cliente);

        context = this;
        intent = getIntent();

        viewSnackbar = findViewById(R.id.explorarClienteActivityView);


        cliente = (Usuario) intent.getSerializableExtra(LoginActivity.USUARIO);

//        recomendacoesDiaSemana = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_DIASEMANA);
//        recomendacoesMaisVisitados = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_MAISVISITADOS);
//        recomendacoesVisitadosRecentemente = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_VISITADOSRECENTEMENTE);
//        recomendacoesEspecialidadeUsuario = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_ESPECIALIDADEUSUARIO);
//        recomendacoesRestauranteAvaliado = (Restaurante[]) intent.getSerializableExtra(LoginActivity.RECOMENDACOES_RESTAURANTEAVALIADO);

        implentarComponentes();
//        popularRecomendacoes();

        bottomNavigationView = findViewById(R.id.bottomNavegation);
        bottomNavigationView.setSelectedItemId(R.id.menu_explorar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_explorar:
                        break;
                    case R.id.menu_comanda:
                        Intent a = new Intent(ExplorarClienteActivity.this, CodigoComandaClienteActivity.class);
                        a.putExtra(USUARIO, cliente);
                        a.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(a);
                        break;
                    case R.id.menu_perfil:
//                        Intent b = new Intent(ExplorarClienteActivity.this, PerfilClienteActivity.class);
//                        b.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(b);
                        Util.showManutencaoDialog(context);
                        break;
                }
                return false;
            }
        });

        getRecomendacoes();

    }

    private void popularRecomendacoes() {
        //RecomendacaoDiaSemanda
        if (recomendacoesDiaSemana.length == 0) {
            constraintLayoutDiaSemana.removeAllViews();
            Log.d("TESTES", "Recomendacao_DiaSemana Vazio");
        } else {
            listaRecomendacaoDiaSemanda = Arrays.asList(recomendacoesDiaSemana);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoDiaSemanaAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoDiaSemanda);
            linearLayoutManagerDiaSemana = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoDiaSemana.setLayoutManager(linearLayoutManagerDiaSemana);
            recyclerViewRecomendacaoDiaSemana.setAdapter(recomendacaoDiaSemanaAdapter);

            //Altera Título de acordo com dia
            String text = "Recomendações do Dia";
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    text = "Domingou";
                    break;
                case Calendar.MONDAY:
                    text = "Segundou";
                    break;
                case Calendar.TUESDAY:
                    text = "Terçou";
                    break;
                case Calendar.WEDNESDAY:
                    text = "Quartou";
                    break;
                case Calendar.THURSDAY:
                    text = "Quintou";
                    break;
                case Calendar.FRIDAY:
                    text = "Sextou";
                    break;
                case Calendar.SATURDAY:
                    text = "Sabadou";
                    break;

            }
            textViewRecomendacaoDiaSemana.setText(text);
        }

        //RecomendacaoMaisVisitados
        if (recomendacoesMaisVisitados.length == 0) {
            Log.d("TESTES", "Recomendacao_MaisVisitados Vazio");
            constraintLayoutMaisVisitados.removeAllViews();
        } else {
            listaRecomendacaoMaisVisitados = Arrays.asList(recomendacoesMaisVisitados);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoMaisVisitadosAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoMaisVisitados);
            linearLayoutManagerMaisVisitados = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoMaisVisitados.setLayoutManager(linearLayoutManagerMaisVisitados);
            recyclerViewRecomendacaoMaisVisitados.setAdapter(recomendacaoMaisVisitadosAdapter);
        }

        //RecomendacaoVisitadosRecentemente
        if (recomendacoesVisitadosRecentemente.length == 0) {
            Log.d("TESTES", "Recomendacao_VisitadoRecentemente Vazio");
            constraintLayoutVisitadodsRecentemente.removeAllViews();
        } else {
            listaRecomendacaoVisitadosRecentemente = Arrays.asList(recomendacoesVisitadosRecentemente);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoVisitadosRecentementeAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoVisitadosRecentemente);
            linearLayoutManagerVisitadosRecentemente = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoVisitadosRecentemente.setLayoutManager(linearLayoutManagerVisitadosRecentemente);
            recyclerViewRecomendacaoVisitadosRecentemente.setAdapter(recomendacaoVisitadosRecentementeAdapter);
        }

        //RecomendacaoEspecialidadeUsuario
        if (recomendacoesEspecialidadeUsuario.length == 0) {
            Log.d("TESTES", "Recomendacao_EspecialidadeUsuario Vazio");
            constraintLayoutEspecialidadeUsuario.removeAllViews();
        } else {
            listaRecomendacaoEspecialidadeUsuario = Arrays.asList(recomendacoesEspecialidadeUsuario);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoEspecialidadeUsuarioAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoEspecialidadeUsuario);
            linearLayoutManagerEspecialidadeUsuario = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoEspecialidadeUsuario.setLayoutManager(linearLayoutManagerEspecialidadeUsuario);
            recyclerViewRecomendacaoEspecialidadeUsuario.setAdapter(recomendacaoEspecialidadeUsuarioAdapter);
        }

        //RecomendacaoRestauranteAvaliado
        if (recomendacoesRestauranteAvaliado.length == 0) {
            Log.d("TESTES", "Recomendacao_RestauranteAvaliado Vazio");
            constraintLayoutRestauranteAvaliado.removeAllViews();
        } else {
            listaRecomendacaoRestauranteAvaliado = Arrays.asList(recomendacoesRestauranteAvaliado);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoRestauranteAvaliadoAdapter = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoRestauranteAvaliado);
            linearLayoutManagerRestauranteAvaliado = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoRestauranteAvaliado.setLayoutManager(linearLayoutManagerRestauranteAvaliado);
            recyclerViewRecomendacaoRestauranteAvaliado.setAdapter(recomendacaoRestauranteAvaliadoAdapter);
        }

        //RecomendacaoRestauranteAvaliado1
        if (recomendacoesRestauranteAvaliado1.length == 0) {
            Log.d("TESTES", "Recomendacao_RestauranteAvaliado Vazio");
            constraintLayoutRestauranteAvaliado1.removeAllViews();
        } else {
            listaRecomendacaoRestauranteAvaliado1 = Arrays.asList(recomendacoesRestauranteAvaliado1);//converte array para list para poder ser trabalho no RecyclerView
            recomendacaoRestauranteAvaliadoAdapter1 = new ExplorarClienteAdapter(getApplicationContext(), listaRecomendacaoRestauranteAvaliado1);
            linearLayoutManagerRestauranteAvaliado1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecomendacaoRestauranteAvaliado1.setLayoutManager(linearLayoutManagerRestauranteAvaliado1);
            recyclerViewRecomendacaoRestauranteAvaliado1.setAdapter(recomendacaoRestauranteAvaliadoAdapter1);
        }

    }



    private void implentarComponentes() {
        //BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavegation);

        //TextView - Título
        textViewRecomendacaoDiaSemana = findViewById(R.id.textView_recomendacaoDiaSemana);
        textViewRecomendacaoEspecialidadeUsuario = findViewById(R.id.textView_recomendacaoEspecialidadeUsuario);
        textViewRecomendacaoMaisVisitados = findViewById(R.id.textView_recomendacaoMaisVisitados);
        textViewRecomendacaoRestauranteAvaliado = findViewById(R.id.textView_recomendacaoRestauranteAvaliado);
        textViewRecomendacaoRestauranteAvaliado1 = findViewById(R.id.textView_recomendacaoRestauranteAvaliado1);
        textViewRecomendacaoVisitadosRecentemente = findViewById(R.id.textView_recomendacaoVisitadosRecentemente);

        //TextView - Descrição
        textViewRecomendacaoDiaSemanaDescricao = findViewById(R.id.textView_recomendacaoDiaSemana_descricao);
        textViewRecomendacaoEspecialidadeUsuarioDescricao = findViewById(R.id.textView_recomendacaoEspecialidadeUsuario_descricao);
        textViewRecomendacaoMaisVisitadosDescricao = findViewById(R.id.textView_recomendacaoMaisVisitados_descricao);
        textViewRecomendacaoRestauranteAvaliadoDescricao = findViewById(R.id.textView_recomendacaoRestauranteAvaliado_descricao);
        textViewRecomendacaoRestauranteAvaliadoDescricao1 = findViewById(R.id.textView_recomendacaoRestauranteAvaliado_descricao1);
        textViewRecomendacaoVisitadosDescricao = findViewById(R.id.textView_recomendacaoVisitadosRecentemente_descricao);

        //RecyclerView - Carrossel
        recyclerViewRecomendacaoDiaSemana = findViewById(R.id.recyclerView_recomendacaoDiaSemana);
        recyclerViewRecomendacaoEspecialidadeUsuario = findViewById(R.id.recyclerView_recomendacaoEspecialidadeUsuario);
        recyclerViewRecomendacaoMaisVisitados = findViewById(R.id.recyclerView_recomendacaoMaisVisitados);
        recyclerViewRecomendacaoRestauranteAvaliado = findViewById(R.id.recyclerView_recomendacaoRestauranteAvaliado);
        recyclerViewRecomendacaoRestauranteAvaliado1 = findViewById(R.id.recyclerView_recomendacaoRestauranteAvaliado1);
        recyclerViewRecomendacaoVisitadosRecentemente = findViewById(R.id.recyclerView_recomendacaoVisitadosRecentemente);

        //ConstraintLayout
        constraintLayoutDiaSemana = findViewById(R.id.constraintLayout_diaSemana);
        constraintLayoutEspecialidadeUsuario = findViewById(R.id.constraintLayout_especialidadeUsuario);
        constraintLayoutMaisVisitados = findViewById(R.id.constraintLayout_maisVisitados);
        constraintLayoutRestauranteAvaliado = findViewById(R.id.constraintLayout_restauranteAvaliado);
        constraintLayoutRestauranteAvaliado1 = findViewById(R.id.constraintLayout_restauranteAvaliado1);
        constraintLayoutVisitadodsRecentemente = findViewById(R.id.constraintLayout_visitadodsRecentemente);

    }


    public void getRecomendacoes(){
        final ProgressDialog mProgressDialog = ProgressDialog.show(this, null,"Carregando seus restaurantes preferidos", true);
        if(Connection.isConnected(this, viewSnackbar)) {
            Thread loginThread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {

                                recomendacoesRestauranteAvaliado       = RecomendacaoNetwork.getRecomendacaoRestauranteAvaliado(Connection.URL);
                                recomendacoesRestauranteAvaliado1       = RecomendacaoNetwork.getRecomendacaoRestauranteAvaliado(Connection.URL);
                                recomendacoesDiaSemana             = RecomendacaoNetwork.getRecomendacaoDiaSemana(Connection.URL);
                                recomendacoesMaisVisitados          = RecomendacaoNetwork.getRecomendacaoMaisVisitados(Connection.URL);
                                recomendacoesEspecialidadeUsuario   = RecomendacaoNetwork.getRecomendacaoEspecialidadeUsuario(Connection.URL, cliente.getId());
                                recomendacoesVisitadosRecentemente         = RecomendacaoNetwork.getRecomendacaoVisitadoRecentemente(Connection.URL, cliente.getId());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            popularRecomendacoes();
                                            mProgressDialog.dismiss();

                                        }
                                    });


                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "ExplorarCLiente: IOException getRecomendacoes");
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                                mProgressDialog.dismiss();
                            }

                        }
                    },"RecomendacoesThread");

            loginThread.start();

        }
    }

}
