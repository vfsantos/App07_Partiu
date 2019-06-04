package br.com.app07_partiu.Activity.FinalizarPedidoClienteActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class FinalizarPedidoClienteActivity extends AppCompatActivity {


    //ListView
    private ListView listViewResumoPedido;


    //Button
    private Button buttonConfirmar;


    //TextView
    private TextView textViewTotal;
    private TextView textViewTotalValor;
    private TextView textViewItensSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        implementarComponentes();
    }


    private void implementarComponentes(){
        //ListView
        listViewResumoPedido     = (ListView) findViewById(R.id.listview_finalizarPedidoCliente_lista);


        //Button
        buttonConfirmar          = (Button) findViewById(R.id.button_finalizarPedido_confirmar);


        //TextView
        textViewTotal            = (TextView) findViewById(R.id.textview_finalizarpedido_total);
        textViewTotalValor       = (TextView) findViewById(R.id.textview_finalizarpedido_totalvalor);
        textViewItensSelecionado = (TextView) findViewById(R.id.textView_finalizarPedido_itensSelecionados);
    }

}
