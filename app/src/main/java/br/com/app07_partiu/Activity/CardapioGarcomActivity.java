package br.com.app07_partiu.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.app07_partiu.Model.ItemCardapio;
import br.com.app07_partiu.Model.ItemCardapioAdapter;

public class CardapioGarcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO R.layout.activity_cardapio_garcom
        //setContentView(R.layout.activity_main);

        //TODO R.id.recycler_CardapioGarcom
        //RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);

        //populacoa pra teste
        List<ItemCardapio> items = test();
        //recyclerView.setAdapter(new ItemCardapioAdapter(items, this));
        //RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
        //LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(layout);
    }

    private List<ItemCardapio> test(){
        List<ItemCardapio> items = new ArrayList<ItemCardapio>();

        ItemCardapio comanda = new ItemCardapio("Camila", "12.99", "3", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Vanessa","12.99", "3", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Raquel","5.99", "1", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Wanessa","2.99", "3", "Pago");
        items.add(comanda);
        comanda = new ItemCardapio("Jessica","1.99", "1", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Mariana","12.99", "3", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Cerveja","12.99", "3", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Pinga","5.99", "1", "Em Aberto");
        items.add(comanda);
        comanda = new ItemCardapio("Coxinha","2.99", "3", "Pago");
        items.add(comanda);
        comanda = new ItemCardapio("narguille","1.99", "1", "Em Aberto");
        items.add(comanda);
        return items;
    }
}
