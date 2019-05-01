package br.com.app07_partiu.Activity.PedidoSelecaoGarcomActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.app07_partiu.R;

public class PedidoSelecaoGarcomActivity extends AppCompatActivity {

    private ProgressBar progressBarSterSelecionar;
    private TextView textViewSelecionarItem;
    private ListView listViewItensCardapio;
    private Button buttonContinuar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_selecao_garcom);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBarSterSelecionar = (ProgressBar) findViewById(R.id.progress_bar_lancar_item_comanda_garcom_step_selecionar);
        textViewSelecionarItem = (TextView) findViewById(R.id.text_view_selecionar_item_lancar_comanda_garcom);
        listViewItensCardapio = (ListView) findViewById(R.id.list_view_itens_cardapio);
        buttonContinuar = (Button) findViewById(R.id.button_continuar_lancar_item_comanda_garcom);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_menu_busca: {
                Toast.makeText(this, "buscar", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.item_menu_notificacao: {
                Toast.makeText(this, "notificação", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
