package br.com.app07_partiu.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

    public static final String COMANDA = "br.com.app07_partiu.Model.comanda";
    private AlertDialog alerta;
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

                /* manda para a tela de detalhe
                Intent intent = new Intent(atividade, DetalhesComandaGarcomActivity.class);
                intent.putExtra(COMANDA, comandas[position]);

                startActivity(intent);
                */

                //mensagem informando que a funcionalidade estara disponível em breve
                alertProximaSprint();

            }

        });

    }

    private void alertProximaSprint(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(R.string.title_alert_disponivel_em_breve);
        //define a mensagem
        builder.setMessage(R.string.subtitle1_alert_disponivel_em_breve);
        //define um botão como positivo
        builder.setPositiveButton(R.string.btn_alert_ok_entendi, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
