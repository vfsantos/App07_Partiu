package br.com.app07_partiu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.R;

public class ComandaGarcomActivity extends AppCompatActivity {

    //TextView
    private TextView textViewItemCodigoComanda;
    private TextView textViewItemTotalComanda;
    private TextView textViewItemTotalComandaValor;
    private TextView textViewItemPessoaComanda;
    private TextView textViewItemPessoaComandaNumero;
    private TextView textViewItemMesa;
    private TextView textViewItemMesaNumero;
    private TextView textViewItemHora;
    private TextView textViewItensDaComanda;


    //ListView
    private ListView listViewItensComanda;


    //Objeto
    public ComandaConvertView comanda;
    public ComandaConvertView comandaConvertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_garcom);
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

        inicializarComponentes();

        Intent intent = getIntent();
        comanda = (ComandaConvertView) intent.getSerializableExtra(HomeGarcomActivity.COMANDA);
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        textViewItemPessoaComandaNumero.setText("12");
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        textViewItemTotalComandaValor.setText("120");
        textViewItemHora.setText(comanda.getDataEntrada());


    }

    public void inicializarComponentes(){
        textViewItemCodigoComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemCodigoComanda);
        textViewItemTotalComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComanda);
        textViewItemTotalComandaValor = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComandaValor);
        textViewItemPessoaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComandaNumero);
        textViewItemMesa = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesa);
        textViewItemMesaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesaNumero);
        textViewItemHora = (TextView) findViewById(R.id.textView_comandaGarcom_itemHora);
        textViewItensDaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itensNaComanda);


        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);

    }

}
