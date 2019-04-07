package br.com.app07_partiu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.app07_partiu.Activity.DetalhesComandaGarcomActivity;
import br.com.app07_partiu.Activity.MainActivity;
import br.com.app07_partiu.ComandaGarcomAdapter;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.R;

public class ListaComandasGarcomActivity extends AppCompatActivity {

    public ArrayList<Comanda> comandaGarcomModel;
    public static final String COMANDA = "br.usjt.desmob.geodata.pais";
    Activity atividade;
    Comanda[] comandas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comandas_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();

        comandas = (Comanda[]) intent.getSerializableExtra(MainActivity.COMANDAS);

        ListView listView = (ListView) findViewById(R.id.list_view_comandas);
        ComandaGarcomAdapter adapter = new ComandaGarcomAdapter(comandas, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // manda para a tela de detalhe
                Intent intent = new Intent(atividade, DetalhesComandaGarcomActivity.class);
                intent.putExtra(COMANDA, comandas[position]);

                startActivity(intent);

            }

        });

    }

}
