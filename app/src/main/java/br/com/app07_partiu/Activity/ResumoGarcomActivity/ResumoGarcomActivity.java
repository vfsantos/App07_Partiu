package br.com.app07_partiu.Activity.ResumoGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class ResumoGarcomActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar toolbar;

    //ListView
    private ListView listViewItensResumo;


    //Button
    private Button buttonAdicionarMaisItens;
    private Button buttonFinalizarPedido;


    //TextView;
    private TextView textViewTotal;
    private TextView getTextViewTotalValor;

    private Context context;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_garcom);
        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

        context = this;
        intent = this.getIntent();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                break;
            default:break;
        }
        return true;
    }


    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                     //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                          //Ativar o botão
            getSupportActionBar().setTitle(R.string.text_cardapiogarcom_titulopagina); //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }

    private void implementarComponentes(){
        //ListView
        listViewItensResumo = (ListView) findViewById(R.id.listView_resumoGarcom_itensDoPedido);


        //Button
        buttonFinalizarPedido = (Button) findViewById(R.id.button_resumoGarcom_finalizarPedido);


        //TextView
        textViewTotal = (TextView) findViewById(R.id.textView_resumoGarcom_total);
        getTextViewTotalValor = (TextView) findViewById(R.id.textView_resumoGarcom_totalvalor);
    }


}
