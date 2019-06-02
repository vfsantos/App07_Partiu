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


import br.com.app07_partiu.Activity.ComandaClienteActivity;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

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


    //ConstraintLayout
    private ConstraintLayout constraintLayoutItemSelecioando;
    private ConstraintLayout constraintLayoutItemDeselecionado;



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



        implementarComponentes();
        carregarViews();


    }

    private void carregarViews(){
        textViewNomeDoItem.setText(item.getNome());
        textViewValor.setText(item.getValorString());
        textViewDescricao.setText("Descrição");
        try {
                Log.d("TESTES", "Qtd Usuários_Pedido de id=" + item.getIdPedido() + " : " + item.getUsuariosPedido().size());
                listViewPessoaItemSelecionado = (ListView) findViewById(R.id.listView_itemDetalhesCliente_pessoaItemSelecionado);
                ItemComandaDetalheClienteAdapter adapter = new ItemComandaDetalheClienteAdapter(item.getUsuariosPedido().toArray(new Usuario[item.getUsuariosPedido().size()]), item.getValor(), this);
                listViewPessoaItemSelecionado.setAdapter(adapter);


        }catch(NullPointerException e){
            Log.d("TESTES", "ListUsuariosPedido vaiza");
        }
    }

    private void implementarComponentes() {
        //TextView
        textViewNomeDoItem = (TextView) findViewById(R.id.textView_itemDetalhesCliente_nome);
        textViewDescricao = (TextView) findViewById(R.id.textView_itemDetalhesCliente_detalhes);
        textViewValor = (TextView) findViewById(R.id.textView_itemDetalhesCliente_valor);


        //ListView
        listViewPessoaItemSelecionado = (ListView) findViewById(R.id.listView_itemDetalhesCliente_pessoaItemSelecionado);


        //Button
        buttonSelecionar = (Button) findViewById(R.id.button_itemDetalhesCliente_selecionar);


        //ConstraintLayout
        constraintLayoutItemDeselecionado = (ConstraintLayout) findViewById(R.id.constraintLayoutDeselecionar);
        constraintLayoutItemSelecioando   = (ConstraintLayout) findViewById(R.id.constraintLayoutSelecionado);
    }

}
