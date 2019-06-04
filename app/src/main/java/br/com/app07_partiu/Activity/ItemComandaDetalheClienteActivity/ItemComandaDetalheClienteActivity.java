package br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import br.com.app07_partiu.Activity.ComandaClienteActivity;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class ItemComandaDetalheClienteActivity extends AppCompatActivity {

    //TextView
    private TextView textViewNomeDoItem;
    private TextView textViewDescricao;
    private TextView textViewValor;


    //ListView
    private ListView listViewPessoaItemSelecionado;


    //Button
    private Button buttonSelecionar;


    public Context context;


    //Intent
    public Intent intent;


    //Array
    private Item item;
    private Comanda comanda;
    private Usuario clienteLogado;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutItemSelecioando;
    private ConstraintLayout constraintLayoutItemDeselecionado;

    private Button btnDeselecionar;
    private Button btnSelecionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_comanda_detalhe_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        intent = getIntent();

        comanda = (Comanda) intent.getSerializableExtra(ComandaMesaClienteActivity.COMANDA);
        item = (Item) intent.getSerializableExtra(ComandaMesaClienteActivity.ITEM);
        clienteLogado = (Usuario) intent.getSerializableExtra(ComandaMesaClienteActivity.CLIENTE_LOGADO);


        implementarComponentes();
        carregarViews();
        setButtonListeners();



    }

    private void carregarViews() {
        boolean usuarioJaSelecionou = false;

        textViewNomeDoItem.setText(item.getNome());
        textViewValor.setText(item.getValorString());
        textViewDescricao.setText("Descrição");
        try {
            Log.d("TESTES", "Qtd Usuários_Pedido de id=" + item.getIdPedido() + " : " + item.getUsuariosPedido().size());
            ItemComandaDetalheClienteAdapter adapter = new ItemComandaDetalheClienteAdapter(item.getUsuariosPedido().toArray(new Usuario[item.getUsuariosPedido().size()]), item.getValor(), this);
            listViewPessoaItemSelecionado.setAdapter(adapter);

            for (Usuario u : item.getUsuariosPedido()) {
                if (u.getId() == clienteLogado.getId()) {
                    usuarioJaSelecionou = true;
                    return;
                }
            }

            switchButtons(usuarioJaSelecionou);




        } catch (NullPointerException e) {
            Log.d("TESTES", "ListUsuariosPedido vaiza");
        }




    }

    private void switchButtons(boolean selecionado){
        if (selecionado) {
            constraintLayoutItemSelecioando.setVisibility(View.INVISIBLE);
            constraintLayoutItemDeselecionado.setVisibility(View.VISIBLE);

        }else{
            constraintLayoutItemSelecioando.setVisibility(View.VISIBLE);
            constraintLayoutItemDeselecionado.setVisibility(View.INVISIBLE);
        }
    }

    private void setButtonListeners() {

        btnSelecionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TESTES", "Botao Selecionar Clicado");

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
                                                  Toast.makeText(context, "Item removido com sucesso!", Toast.LENGTH_LONG).show();
                                                  finish();
                                              }
                                          }
                            );
                        } catch (IOException e) {
                            Log.e("TESTES", "Erro ao selecionar; idPedido=" + item.getId() + ", idUsuario=" + clienteLogado.getId());
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btnDeselecionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TESTES", "Botao Selecionar Clicado");

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
                                                  Toast.makeText(context, "Pedido Deselecionado!", Toast.LENGTH_LONG).show();

                                                  finish();
                                              }
                                          }
                            );
                        } catch (IOException e) {
                            Log.e("TESTES", "Erro ao selecionar; idPedido=" + item.getId() + ", idUsuario=" + clienteLogado.getId());
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


    private void implementarComponentes() {
        //TextView
        textViewNomeDoItem = (TextView) findViewById(R.id.textView_itemDetalhesCliente_nome);
        textViewDescricao = (TextView) findViewById(R.id.textView_itemDetalhesCliente_detalhes);
        textViewValor = (TextView) findViewById(R.id.textView_itemDetalhesCliente_valor);


        //ListView
        listViewPessoaItemSelecionado = (ListView) findViewById(R.id.listView_itemDetalhesCliente_pessoaItemSelecionado);


        //Button
//        buttonSelecionar = (Button) findViewById(R.id.button_itemDetalhesCliente_selecionar);


        //ConstraintLayout
        constraintLayoutItemDeselecionado = (ConstraintLayout) findViewById(R.id.constraintLayoutDeselecionar);
        btnDeselecionar = findViewById(R.id.button_itemDetalhesCliente_dsselecionar);

        constraintLayoutItemSelecioando = (ConstraintLayout) findViewById(R.id.constraintLayoutSelecionado);
        btnSelecionar = findViewById(R.id.button_itemDetalhesCliente_selecionar);
    }

}
