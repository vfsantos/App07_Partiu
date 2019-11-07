package br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class ItemComandaDetalheClienteActivity extends AppCompatActivity {


    //ConstraintLayout
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutVoltar;
    private ConstraintLayout constraintLayoutBody;
    private ConstraintLayout constraintLayoutDeselecionar;
    private ConstraintLayout constraintLayoutSelecionar;
    private ConstraintLayout constraintLayoutLista;
    private ConstraintLayout constraintLayoutTituloLista;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewNomeDoItem;
    private TextView textViewDescricao;
    private TextView textViewValor;
    private TextView textViewSelecionar;
    private TextView textViewDeselecionar;


    //ImageView
    private ImageView imageViewVoltar;
    private ImageView imageViewFlagSelecionar;
    private ImageView imageViewFlagDeselecionar;


    //ListView
    private ListView listViewPessoaItemSelecionado;


    //Button
    private Button buttonSelecionar;
    private Button btnDeselecionar;
    private Button btnSelecionar;


    public Context context;


    //Intent
    public Intent intent;


    //Array
    private Item item;
    private Comanda comanda;
    private Usuario clienteLogado;



    //SnackBar
    private View viewSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_comanda_detalhe_cliente);

        implementarComponentes();

        context = this;
        intent = getIntent();
        viewSnackbar = findViewById(R.id.itemComandaDetalheClienteActivityView);

        comanda = (Comanda) intent.getSerializableExtra(ComandaMesaClienteActivity.COMANDA);
        item = (Item) intent.getSerializableExtra(ComandaMesaClienteActivity.ITEM);
        clienteLogado = (Usuario) intent.getSerializableExtra(ComandaMesaClienteActivity.CLIENTE_LOGADO);

        carregarViews();
        setButtonListeners();


//        checkUsuarioJaPagou();

    }


    private void checkUsuarioJaPagou() {
//        try {
//
//            for (Usuario u : item.getUsuariosPedido()) {
//                if (u.getId() == clienteLogado.getId() && u.getStatusPedido() == "P") {
        btnSelecionar.setVisibility(View.INVISIBLE);
        btnDeselecionar.setVisibility(View.INVISIBLE);
//                }
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
    }

    private void carregarViews() {


        textViewNomeDoItem.setText(item.getNome());
        textViewValor.setText(item.getValorString());
        textViewDescricao.setText(item.getDetalhe());
        try {

            boolean usuarioJaSelecionou = false;
            boolean usuarioJaPagou = false;

            Log.d("TESTES", "Qtd Usuários_Pedido de id=" + item.getIdPedido() + " : " + item.getUsuariosPedido().size());
            ItemComandaDetalheClienteAdapter adapter = new ItemComandaDetalheClienteAdapter(item.getUsuariosPedido().toArray(new Usuario[item.getUsuariosPedido().size()]), item.getValor(), this);
            listViewPessoaItemSelecionado.setAdapter(adapter);

            for (Usuario u : item.getUsuariosPedido()) {
//                Log.d("TESTES", u.toString());
                if (u.getId() == clienteLogado.getId()) {
                    usuarioJaSelecionou = true;
                    if (u.getStatusPedido().toUpperCase().equals("P")) {
                        usuarioJaPagou = true;
                    }
                }
            }

            switchButtons(usuarioJaSelecionou);

            if (usuarioJaPagou) checkUsuarioJaPagou();


        } catch (NullPointerException e) {
            Log.d("TESTES", "ListUsuariosPedido vaiza");
        }


    }

    private void switchButtons(boolean selecionado) {
        if (selecionado) {
            constraintLayoutSelecionar.setVisibility(View.INVISIBLE);
            constraintLayoutDeselecionar.setVisibility(View.VISIBLE);

        } else {
            constraintLayoutSelecionar.setVisibility(View.VISIBLE);
            constraintLayoutDeselecionar.setVisibility(View.INVISIBLE);
        }
    }

    private void setButtonListeners() {
         imageViewFlagSelecionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TESTES", "Botao Selecionar Clicado");
                if (Connection.isConnected(context, viewSnackbar)) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ComandaNetwork.selecionarPedidoUsuario(Connection.URL, item.getIdPedido(), clienteLogado.getId(), comanda.getId());
/*
                            Item[] pedidoNaoFormatado = ComandaNetwork.getPedidoUsuarioBydId(Connection.URL, item.getIdPedido());
                            item = Util.formatItens(pedidoNaoFormatado)[0];*/
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                  /*carregarViews();
                                                  switchButtons(true);*/
                                                      setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSELECIONADO);
                                                      finish();
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                Log.e("TESTES", "ItemComandaDetalheCliente: IOException setButtons ;Erro ao selecionar; idPedido=" + item.getId() + ", idUsuario=" + clienteLogado.getId());
                                e.printStackTrace();
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);

                            }
                        }
                    }).start();
                }
            }

        });

        imageViewFlagDeselecionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TESTES", "Botao Selecionar Clicado");

                if (Connection.isConnected(context, viewSnackbar)) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ComandaNetwork.deselecionarPedidoUsuario(Connection.URL, item.getIdPedido(), clienteLogado.getId(), comanda.getId());
/*

                            Item[] pedidoNaoFormatado = ComandaNetwork.getPedidoUsuarioBydId(Connection.URL, item.getIdPedido());
                            item = Util.formatItens(pedidoNaoFormatado)[0];
                            for(Usuario u : item.getUsuariosPedido()){
                                Log.d("TESTES", "Usuario:"+u.toString());
                            }

*/
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                  /*carregarViews();
                                                  switchButtons(false);*/
                                                      setResult(ComandaMesaClienteActivity.RESULT_PEDIDODESELECIONADO);
                                                      finish();
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                Log.e("TESTES", "ItemComandaDetalheCliente: IOException setButtons ;Erro ao DESelecionar; idPedido=" + item.getId() + ", idUsuario=" + clienteLogado.getId());
                                e.printStackTrace();
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void onClickVoltaFinalizarPedidoCliente(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    private void implementarComponentes() {
        //ConstraintLayout
        constraintLayoutHeader          = (ConstraintLayout) findViewById(R.id.constraintLayout_itemDetalhesCliente_header);
        constraintLayoutVoltar          = (ConstraintLayout) findViewById(R.id.constraintLayour_itemDetalhesCliente_voltar);
        constraintLayoutBody            = (ConstraintLayout) findViewById(R.id.constraintLayout_itemDetalhesCliente_body);
        constraintLayoutSelecionar      = (ConstraintLayout) findViewById(R.id.constraintLayour_itemDetalhesCliente_selecionar);
        constraintLayoutDeselecionar    = (ConstraintLayout) findViewById(R.id.constraintLayour_itemDetalhesCliente_deselecionar);
        constraintLayoutLista           = (ConstraintLayout) findViewById(R.id.constraintLayour_itemDetalhesCliente_lista);
        constraintLayoutTituloLista     = (ConstraintLayout) findViewById(R.id.constraintLayour_itemDetalhesCliente_titulolista);


        //TextView
        textViewTitulo                    = (TextView) findViewById(R.id.textView_itemDetalhesCliente_tituloPagina);
        textViewNomeDoItem                = (TextView) findViewById(R.id.textView_itemDetalhesCliente_nome);
        textViewDescricao                 = (TextView) findViewById(R.id.textView_itemDetalhesCliente_detalhes);
        textViewValor                     = (TextView) findViewById(R.id.textView_itemDetalhesCliente_valor);
        textViewSelecionar                = (TextView) findViewById(R.id.textView_itemDetalhesCliente_flag_selecionar);
        textViewDeselecionar              = (TextView) findViewById(R.id.textView_itemDetalhesCliente_flag_deselecionar);



        //ImageView
        imageViewVoltar                   = (ImageView) findViewById(R.id.imageview_itemDetalhesCliente_voltar);
        imageViewFlagDeselecionar         = (ImageView) findViewById(R.id.imageView_itemDetalhesCliente_flag_deselecionar);
        imageViewFlagSelecionar           = (ImageView) findViewById(R.id.imageView_itemDetalhesCliente_flag_selecionar);


        //ListView
        listViewPessoaItemSelecionado     = (ListView) findViewById(R.id.listView_itemDetalhesCliente_pessoaItemSelecionado);

    }

}
