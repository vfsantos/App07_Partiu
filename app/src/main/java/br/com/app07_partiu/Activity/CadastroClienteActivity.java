package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class CadastroClienteActivity extends AppCompatActivity {

    public static final String URL = "http://10.0.2.2:8080/projeto_cartorio/";
    public static final String SENHA = "br.com.victor.geradordesenha.senha";

    private TextView titulo;
    private EditText nome;
    private EditText sobrenome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
