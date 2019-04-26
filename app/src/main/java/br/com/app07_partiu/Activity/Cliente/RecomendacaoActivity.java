package br.com.app07_partiu.Activity.Cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.app07_partiu.Model.Estabelecimento;
import br.com.app07_partiu.R;
import br.com.app07_partiu.RecomendacaoRecyclerViewAdapter;

public class RecomendacaoActivity extends AppCompatActivity {

    private static final String TAG = "RecomendacaoActivity";
    private static final String ESTABELECIMENTO = "br.com.app07_partiu.estabelecimento";
    Activity atividade;
    Estabelecimento[] estabelecimentos;

    //variáveis
    private ArrayList<String> mNomes = new ArrayList<>();
    private ArrayList<String> mImgEstabelecimantoUrls = new ArrayList<>();


    private Intent intent = getIntent();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        atividade = this;
        Intent intent = getIntent();

        //estabelecimentos = (Estabelecimento[]) intent.getSerializableExtra(MainActivity.ESTABELECIMENTO);


        ListView listViewEmAlta = (ListView) findViewById(R.id.recycler_em_alta);
        ListView listViewSugestaoParaVoce = (ListView) findViewById(R.id.recycler_sugestao_para_voce);
        ListView listViewVisiteNovamente = (ListView) findViewById(R.id.recycler_visite_novamente);


        listViewEmAlta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // manda para a tela de detalhe
                Intent intent = new Intent(atividade, RecomendacaoDetalhesActivity.class);
                intent.putExtra(ESTABELECIMENTO, estabelecimentos[position]);

                startActivity(intent);

            }
        });

        listViewSugestaoParaVoce.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // manda para a tela de detalhe
                Intent intent = new Intent(atividade, RecomendacaoDetalhesActivity.class);
                intent.putExtra(ESTABELECIMENTO, estabelecimentos[position]);

                startActivity(intent);

            }
        });

        listViewVisiteNovamente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // manda para a tela de detalhe
                Intent intent = new Intent(atividade, RecomendacaoDetalhesActivity.class);
                intent.putExtra(ESTABELECIMENTO, estabelecimentos[position]);

                startActivity(intent);

            }
        });


        //Código abaixo é unicamente para teste do layout
        getEstabelecimentoEmAlta();
        getEstabelecimentoSugestaoParaVoce();
        getEstabelecimentoVisiteNovamente();

        /*
        getEstabelecimentoPorqueVoceVisitou();
        getEstabelecimentoOMelhorDa();
        getEstabelecimentoVamosCurtir();
        getEstabelecimentoParaFas();
        getEstabelecimentoParaFamilia();
        getEstabelecimentoParecidosCom();
        */
    }

   
    //Métodos abaixos devem ser desconsiderados pois foram criados unicamente para teste
    private void getEstabelecimentoEmAlta(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewEmAlta();

    }

    private void getEstabelecimentoSugestaoParaVoce(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewSugestaoParaVoce();

    }

    private void getEstabelecimentoVisiteNovamente(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewVisiteNovamente();

    }

    /*

    private void getEstabelecimentoPorqueVoceVisitou(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewPorqueVoceVisitou();

    }

    private void getEstabelecimentoOMelhorDa(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewOMelhorDa();

    }

    private void getEstabelecimentoVamosCurtir(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewVamosCurtir();

    }

    private void getEstabelecimentoParaFas(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewParaFas();

    }

    private void getEstabelecimentoParaFamilia(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewParaFamilia();

    }

    private void getEstabelecimentoParecidosCom(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        mImgEstabelecimantoUrls.add("https://res-4.cloudinary.com/crunchbase-production/image/upload" +
                "/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1449817239/zn8t4bgjwpfkhiueore5.png");
        mNomes.add("Outback");

        initRecyclerViewParecidosCom();

    }
*/

    //-------------------------


    private void initRecyclerViewEmAlta(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_em_alta);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewSugestaoParaVoce(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_sugestao_para_voce);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewVisiteNovamente(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_visite_novamente);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    /*

    private void initRecyclerViewPorqueVoceVisitou(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_porque_voce_visitou);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewOMelhorDa(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_o_melhor_da);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewVamosCurtir(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_vamos_curtir);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewParaFas(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_para_fas);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewParaFamilia(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_para_familia);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewParecidosCom(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_parecidos_com);
        recyclerView.setLayoutManager(layoutManager);
        RecomendacaoRecyclerViewAdapter adapter = new RecomendacaoRecyclerViewAdapter(this, mNomes,mImgEstabelecimantoUrls);
        recyclerView.setAdapter(adapter);
    }
*/

}
