package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.ItemDetalheGarcomActivity;
import br.com.app07_partiu.Activity.LoginActivity;
import br.com.app07_partiu.Activity.PedidoSelecaoGarcomActivity.PedidoSelecaoGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.ItemConvertView;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.R;

public class ComandaGarcomActivity extends AppCompatActivity {
    public static final String URL = LoginActivity.URL;
    public static final String ITEM = "br.com.app07_partiu.ComandaGarcomActivity.item";


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

    public static final String COMANDA = "br.com.app07_partiu.ComandaGarcomActivity.comanda";
    public static final String ITENS_RESTAURANTE = "br.com.app07_partiu.ComandaGarcomActivity.itensRestaurante";



    //ListView
    private ListView listViewItensComanda;


    //Itent
    public Intent intentItem;


    //Objeto
    public ComandaConvertView comanda;
    public ItemComandaGarcomConvertView[] itens;
    private Context context;

    private ComandaConvertView comandaConvertView;

    private Restaurante restaurante;

    private Intent intentPedidoSelecaoGarcom;


    private ItemComandaGarcomConvertView[] itensRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                visualizarItensRestaurante();
            }
        });

        inicializarComponentes();

        Intent intent = getIntent();
        restaurante = (Restaurante) intent.getSerializableExtra(HomeGarcomActivity.RESTAURANTE);
        comanda = (ComandaConvertView) intent.getSerializableExtra(HomeGarcomActivity.COMANDA);


        context = this;
        Intent intentComanda = getIntent();
        Intent intentItens = getIntent();
        comanda = (ComandaConvertView) intentComanda.getSerializableExtra(HomeGarcomActivity.COMANDA);
        itens = (ItemComandaGarcomConvertView[]) intentItens.getSerializableExtra(HomeGarcomActivity.ITENS);


        //Detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        // TODO carregar qtd pessoas na comanda
        textViewItemPessoaComandaNumero.setText("12");
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        // TODO verificar total dos pedidos
        textViewItemTotalComandaValor.setText("120");
        textViewItemHora.setText(comanda.getDataEntrada());


        //Listview com itens da comanda selecionada
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);
        ComandaGarcomAdapter adapter = new ComandaGarcomAdapter(itens, this);
        listViewItensComanda.setAdapter(adapter);
        listViewItensComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                intentItem = new Intent(context, ItemDetalheGarcomActivity.class);
                intentItem.putExtra(ITEM, itens[position]);
                startActivity(intentItem);
            }
        });
    }

    private void inicializarComponentes(){
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

    private void visualizarItensRestaurante() {
        intentPedidoSelecaoGarcom = new Intent(context, PedidoSelecaoGarcomActivity.class);
        if (ComandaNetwork.isConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itensRestaurante = ItemNetwork.getItensCardapio(URL, restaurante.getCnpj());
                        //pedidos = ItemNetwork.getItensCardapio();
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              intentPedidoSelecaoGarcom.putExtra(COMANDA, comanda);
                                              intentPedidoSelecaoGarcom.putExtra(ITENS_RESTAURANTE, itensRestaurante);
                                              startActivity(intentPedidoSelecaoGarcom);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast.makeText(this, "Rede inativa", Toast.LENGTH_SHORT).show();
        }
    }

}
