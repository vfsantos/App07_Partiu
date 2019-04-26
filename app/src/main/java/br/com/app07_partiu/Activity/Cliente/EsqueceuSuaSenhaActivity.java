package br.com.app07_partiu.Activity.Cliente;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pkmmte.pkrss.PkRSS;

import java.util.ArrayList;
import java.util.List;

import br.com.app07_partiu.ItemComandaGarcomRecyclerAdapter;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.R;

public class EsqueceuSuaSenhaActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_sua_senha);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
