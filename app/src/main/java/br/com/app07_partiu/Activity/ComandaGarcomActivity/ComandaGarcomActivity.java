package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import br.com.app07_partiu.Activity.CardapioGarcomActivity.CardapioGarcomActivity;
import br.com.app07_partiu.Activity.FinalizarComandaGarcom.FinalizarComandaGarcomActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.ItemDetalheGarcomActivity;
import br.com.app07_partiu.Activity.ResumoCardapioGarcomActivity.ResumoCardapioGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.ItemNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Util.Util.doubleToReal;

public class ComandaGarcomActivity extends AppCompatActivity {


    public static final String ITEM = "br.com.app07_partiu.ComandaGarcomActivity.item";
    public static final String ITENS_FINALIZAR = "br.com.app07_partiu.ComandaGarcomActivity.ItensFInalizarComanda";
    public static final String COMANDA = "br.com.app07_partiu.ComandaGarcomActivity.comanda";
    public static final String ITENS_RESTAURANTE = "br.com.app07_partiu.ComandaGarcomActivity.itensRestaurante";

    public static final String ID_COMANDA = "CardapioGarcom.idComanda";

    public static final int RESULT_PEDIDOS_CRIADOS = 1000;
    public static final int RESULT_PEDIDO_REMOVIDO = 2000;

    //Toolbar
    private Toolbar toolbar;


    //TextView
    private TextView textViewItemCodigoComanda;
    private TextView textViewItemTotalComanda;
    private TextView textViewItemTotalComandaValor;
    private TextView textViewItemPessoaComanda;
    private TextView textViewItemPessoaComandaNumero;
    private TextView textViewItemMesa;
    private TextView textViewItemMesaNumero;
    private TextView textViewItemData;
    private TextView textViewItemDataValor;
    private TextView textViewItensDaComanda;


    //ListView
    private ListView listViewItensComanda;


    //Button
    private Button buttonPedido;
    private Button buttonFinalizar;


    //Itent
    private Intent intent;
    private Intent intentItem;


    //Objeto
    private Comanda comanda;
    private ComandaConvertView convertedComanda;
    public Item[] itens;
    public Item[] itensFormatados;
    private Context context;

    private Double valorTotalComanda = 0.0;
    private Double valorPagoComanda = 0.0;
    private ComandaConvertView comandaConvertView;
    private Restaurante restaurante;
    private Intent intentPedidoSelecaoGarcom;
    private Item[] itensRestaurante;
    private int[] idUsuario;

    private String dataAtualizacao;

    private Timer timerAtualizacao;

    private View viewSnackbar;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_garcom);
        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);


        context = this;
        viewSnackbar = findViewById(R.id.comandaGarcomActivityView);

        buttonFinalizar.setBackgroundTintList(getResources().getColorStateList(R.drawable.button_float_tintlist));


        buttonPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizarItensRestaurante();
            }
        });


        intent = getIntent();


        restaurante = (Restaurante) intent.getSerializableExtra(HomeGarcomActivity.RESTAURANTE);
        comanda = (Comanda) intent.getSerializableExtra(HomeGarcomActivity.COMANDA);
        itens = (Item[]) intent.getSerializableExtra(HomeGarcomActivity.PEDIDOS);
        idUsuario = (int[]) intent.getSerializableExtra(HomeGarcomActivity.USUARIO_IDS);
        dataAtualizacao = (String) intent.getSerializableExtra(HomeGarcomActivity.DATA_ATUALIZACAO_COMANDA);

        //Detalhes da comanda
        textViewItemCodigoComanda.setText(comanda.getCodigoComanda());
        textViewItemPessoaComandaNumero.setText("0");
        textViewItemMesaNumero.setText(String.valueOf(comanda.getMesa()));
        textViewItemDataValor.setText(comanda.getDataEntrada().split(" ")[1].replace(":", "h"));

        if (itens != null) {
            carregarItens();
        }else{
            buttonFinalizar.setEnabled(false);
        }

        textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));
        textViewItemPessoaComandaNumero.setText("" + idUsuario.length);

        setReloadInterval();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                break;
            case R.id.action_settings: {
                Util.logoff(context);
            }
            default:break;
        }
        return true;
    }


    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_comandagarcom_titulopagina);     //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setReloadInterval();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerAtualizacao.cancel();
    }

    public void onClickFinalizarComanda(View view){
        final Intent intentFinalizar = new Intent(context, FinalizarComandaGarcomActivity.class);
        if(Connection.isConnected(context, viewSnackbar)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Item[] itensFinalizar = ComandaNetwork.getPedidosEmAbertoByComanda(Connection.URL, comanda.getId());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {

                                              //Comanda necessaria para saber em que id inserir pedidos
                                              intentFinalizar.putExtra(ITENS_FINALIZAR, itensFinalizar);
                                              intentFinalizar.putExtra(COMANDA, comanda);
                                              startActivityForResult(intentFinalizar, 0);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "ComandaGarcom: IOException visualizarItensRestaurante");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();


        }
    }

    private void carregarItens() {
        try {

            itensFormatados = Util.formatItens(itens);
            getTotalComanda();
            textViewItemTotalComandaValor.setText(doubleToReal(valorTotalComanda));

            listViewItensComanda.setVisibility(View.VISIBLE);
            //Listview com itens da comanda selecionada
            listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);
            ComandaGarcomAdapter adapter = new ComandaGarcomAdapter(itensFormatados, this);
            listViewItensComanda.setAdapter(adapter);
            listViewItensComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    intentItem = new Intent(context, ItemDetalheGarcomActivity.class);
                    intentItem.putExtra(ITEM, itens[position]);
                    intentItem.putExtra(ID_COMANDA, comanda.getId());
                    startActivityForResult(intentItem, 0);
                }
            });

        }catch(NullPointerException e){
            Log.d("TESTES", "carregarItens: Sem Pedidos na Comanda");
            listViewItensComanda.setVisibility(View.INVISIBLE);
            buttonFinalizar.setEnabled(false);


        }

    }

    private void visualizarItensRestaurante() {
        intentPedidoSelecaoGarcom = new Intent(context, CardapioGarcomActivity.class);
        if (Connection.isConnected(this, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itensRestaurante = ItemNetwork.getItensCardapioGarcom(Connection.URL, restaurante.getCnpj());
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {

                                              //Comanda necessaria para saber em que id inserir pedidos
                                              intentPedidoSelecaoGarcom.putExtra(COMANDA, comanda);
                                              intentPedidoSelecaoGarcom.putExtra(ITENS_RESTAURANTE, itensRestaurante);
                                              startActivityForResult(intentPedidoSelecaoGarcom, RESULT_PEDIDOS_CRIADOS);
                                          }
                                      }
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "ComandaGarcom: IOException visualizarItensRestaurante");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    }
                }
            }).start();
        }
    }


    private void getTotalComanda() {
        valorTotalComanda = 0.0;
        valorPagoComanda = 0.0;
        for (Item i : itensFormatados) {
            valorTotalComanda += i.getValor();
            if (i.getStatusPedido().equals("P")){
                valorPagoComanda += i.getValor();
            }else if(i.getStatusPedido().equals("S")){

                for (Usuario u : i.getUsuariosPedido()){
                    if (u.getStatusPedido().equals("P")){
                        valorPagoComanda += u.getPorcPedido()*i.getValor()/100;
                    }
                }
            }

        }
        Log.d("TESTES", "ComandaValor Total: "+valorTotalComanda+"; Pago: "+valorPagoComanda);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Item[] itensRecarregar;
        switch(resultCode){
            case RESULT_PEDIDOS_CRIADOS:
                Util.showSnackbar(viewSnackbar, "Itens Adicionados!");
                itensRecarregar = (Item[]) data.getSerializableExtra(ResumoCardapioGarcomActivity.RETORNO_ITENS_COMANDA);
                itens = itensRecarregar;
                carregarItens();
                buttonFinalizar.setEnabled(true);
                break;

            case RESULT_PEDIDO_REMOVIDO:
                Util.showSnackbar(viewSnackbar, "Pedido Cancelado!");
                 itensRecarregar = (Item[]) data.getSerializableExtra(ItemDetalheGarcomActivity.PEDIDOS_REFRESH);
                itens = itensRecarregar;
                carregarItens();
                break;

            case FinalizarComandaGarcomActivity.RESULT_COMANDA_FINALIZADA:
                setResult(FinalizarComandaGarcomActivity.RESULT_COMANDA_FINALIZADA);
                finish();
                break;

        }
    }

    private void setReloadInterval(){
        timerAtualizacao = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                if (Connection.isConnected(context, viewSnackbar)) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String novaDataAtualizacao = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());
//                            Log.d("TESTES", "ComandaGarcom_novaDataAtualizacao = "+novaDataAtualizacao);
                                if (dataAtualizacao != null) {
                                    if (!(dataAtualizacao.equals(novaDataAtualizacao))) {
                                        Log.d("TESTES", "ComandaGarcom_novaDataAtualizacao; DataAtualização diferentes, recarregando List Pedidos");
                                        dataAtualizacao = novaDataAtualizacao;
                                        final Item[] itensNovos = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());

                                        runOnUiThread(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              itens = itensNovos;
                                                              carregarItens();
                                                          }
                                                      }
                                        );
                                    }
                                } else {
                                    dataAtualizacao = novaDataAtualizacao;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "ComandaGarcom: IOException setReloadInterval");
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                            }
                        }
                    }).start();
                }
            }
        };
        timerAtualizacao.schedule( task, 0L ,3000L);
    }


    private void implementarComponentes() {
        //Toolbar
        toolbar = findViewById(R.id.toolbar);


        //TextView
        textViewItemCodigoComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemCodigoComanda);
        textViewItemTotalComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComanda);
        textViewItemTotalComandaValor = (TextView) findViewById(R.id.textView_comandaGarcom_itemTotalComandaValor);
        textViewItemPessoaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComanda);
        textViewItemPessoaComandaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemPessoasComandaNumero);
        textViewItemMesa = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesa);
        textViewItemMesaNumero = (TextView) findViewById(R.id.textView_comandaGarcom_itemMesaNumero);
        textViewItemData = (TextView) findViewById(R.id.textView_comandaGarcom_data);
        textViewItemDataValor = (TextView) findViewById(R.id.textView_comandaGarcom_dataValor);
        textViewItensDaComanda = (TextView) findViewById(R.id.textView_comandaGarcom_itensNaComanda);

        //ListView
        listViewItensComanda = (ListView) findViewById(R.id.listView_comandaGarcom_itensDaComanda);

        //Button
        buttonPedido = (Button) findViewById(R.id.button_comandaGarcom_pedido);
        buttonFinalizar = findViewById(R.id.button_comandagarcom_finalizarComanda);

    }
}
