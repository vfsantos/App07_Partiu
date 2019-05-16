package br.com.app07_partiu.Activity.CardapioGarcomActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.app07_partiu.Activity.ComandaGarcomActivity.ComandaGarcomActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.Model.ItemConvertView;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Model.ItemComandaGarcomConvertView.listToArray;

public class CardapioGarcomActivity extends AppCompatActivity {

    public static final String ITENS_ADICIONAR = "CardapioGarcomActivity.ItensAdicionar";
    public static final String COMANDA = "CardapioGarcomActivity.Comanda";

    Intent intent;
    Context context;

    Intent intentResumoAddItens;

    //Do intent
    private Comanda comanda;
    private ItemConvertView[] itensRestaurante;

    List<ItemComandaGarcomConvertView> itensAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_garcom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = this.getIntent();
        context = this;

        comanda = (Comanda) intent.getSerializableExtra(ComandaGarcomActivity.COMANDA);
        itensRestaurante = (ItemConvertView[]) intent.getSerializableExtra(ComandaGarcomActivity.ITENS_RESTAURANTE);

        itensAdicionar = new ArrayList<ItemComandaGarcomConvertView>();
        //OnClickListener addItem();

    }

    private void addItem(ItemComandaGarcomConvertView item){
        itensAdicionar.add(item);
    }

    private void proximaTela(){
        // TODO Definir Activity
        intentResumoAddItens =  new Intent(context, null );
        intentResumoAddItens.putExtra(ITENS_ADICIONAR, listToArray(itensAdicionar));
        intentResumoAddItens.putExtra(COMANDA, comanda);
        startActivity(intentResumoAddItens);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TESTES", "RequestCode " + requestCode);
        Log.d("TESTES", "ResultCode " + resultCode);
        Log.d("TESTES", "Data " + data);
        //result code deve ser o mesmo para fechar
        //data é a intent anterior; dela podemos pegar os dados necessários com data.getSerializableExtra()
        //também dá pra dar finish caso necessário
        if (resultCode == 1000) {
//            String resultadoLoco = (String) data.getSerializableExtra("SLA");
//            Log.d("TESTES", "sla " + resultadoLoco);
//            finish();
        }

        // TODO inserir no final da proxima activity
        //  Result =  o do if
//        Intent i = new Intent();
//        i.putExtra("SLA", "retornei coisas, porra");
//        setResult(1000,i);
//        finish();
    }

}
