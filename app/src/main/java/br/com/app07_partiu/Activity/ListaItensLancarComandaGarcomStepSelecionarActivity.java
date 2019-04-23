package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class ListaItensLancarComandaGarcomStepSelecionarActivity extends AppCompatActivity {

    private ProgressBar progressBarSterSelecionar;
    private TextView textViewSelecionarItem;
    private ListView listViewItensCardapio;
    private Button buttonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens_lancar_comanda_garcom_step_selecionar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBarSterSelecionar = (ProgressBar) findViewById(R.id.progress_bar_lancar_item_comanda_garcom_step_selecionar);
        textViewSelecionarItem = (TextView) findViewById(R.id.text_view_selecionar_item_lancar_comanda_garcom);
        listViewItensCardapio = (ListView) findViewById(R.id.list_view_itens_cardapio);
        buttonContinuar = (Button) findViewById(R.id.button_continuar_lancar_item_comanda_garcom);

    }

}
