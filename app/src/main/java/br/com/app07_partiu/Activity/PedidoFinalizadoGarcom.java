package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class PedidoFinalizadoGarcom extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textViewText1;
    private TextView textViewText2;
    private Button buttonFechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_finalizado_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_pedidoFinalizadoGarcom);
        imageView = (ImageView) findViewById(R.id.imageView_pedidoFinalizadoGarcom);
        textViewText1 = (TextView) findViewById(R.id.textView_text1_pedidoFinalizadoGarcom);
        textViewText2 = (TextView) findViewById(R.id.textView_text2_pedidoFinalizadoGarcom);
        buttonFechar = (Button) findViewById(R.id.button_fechar_pedidoFinalizadoGarcom);


    }

}
