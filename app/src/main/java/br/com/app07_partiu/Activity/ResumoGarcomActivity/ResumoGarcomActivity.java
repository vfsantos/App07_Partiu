package br.com.app07_partiu.Activity.ResumoGarcomActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class ResumoGarcomActivity extends AppCompatActivity {

    //ListView
    ListView listViewItensResumo;


    //Button
    Button buttonAdicionarMaisItens;
    Button buttonFinalizarPedido;


    //TextView;
    TextView textViewTotal;
    TextView getTextViewTotalValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarComponentes();
    }

    private void inicializarComponentes(){
        //ListView
        listViewItensResumo = (ListView) findViewById(R.id.listView_resumoGarcom_itensDoPedido);


        //Button
        buttonAdicionarMaisItens = (Button) findViewById(R.id.button_resumoGarcom_adicionarMaisItens);
        buttonFinalizarPedido = (Button) findViewById(R.id.button_resumoGarcom_finalizarPedido);


        //TextView
        textViewTotal = (TextView) findViewById(R.id.textView_resumoGarcom_total);
        getTextViewTotalValor = (TextView) findViewById(R.id.textView_resumoGarcom_totalvalor);
    }

}
