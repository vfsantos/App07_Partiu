package br.com.app07_partiu.Activity.CadastroActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.LoginClienteActivity;
import br.com.app07_partiu.Activity.MainClienteActivity;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class CadastroSucesso extends AppCompatActivity {

    //ImageView
    private ImageView imageViewFechar;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutFechar;


    //ImageView
    private ImageView imageViewSucesso;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewSucesso;


    //Button
    private Button buttonEntrar;
    private Button buttonVoltar;


    //Intent
    private Intent intentToLoginCliente;
    private Intent intentToMainCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_sucesso);

        implementarComponentes();
    }


    public void onClickEntrarToLoginCliente(View view) {
        intentToLoginCliente = new Intent(CadastroSucesso.this, LoginClienteActivity.class);
        startActivity(intentToLoginCliente);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onClickVoltarToMainCliente(View view) {
        intentToMainCliente = new Intent(CadastroSucesso.this, MainClienteActivity.class);
        startActivity(intentToLoginCliente);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onClickFecharToMainCliente(View view) {
        intentToMainCliente = new Intent(CadastroSucesso.this, MainClienteActivity.class);
        startActivity(intentToLoginCliente);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutFechar   = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarsucesso_fechar);


        //ImageView
        imageViewFechar          = (ImageView) findViewById(R.id.imageview_cadastrarsucesso_fechar);
        imageViewSucesso         = (ImageView) findViewById(R.id.imageview_cadastrarsucesso_sucesso);


        //TextView
        textViewTitulo           = (TextView) findViewById(R.id.textView_cadastrarsucesso_titulo);
        textViewSucesso          = (TextView) findViewById(R.id.textview_cadastrarsucesso_sucesso);


        //Button
        buttonEntrar            = (Button) findViewById(R.id.button_cadastrarsucesso_entrar);
        buttonVoltar            = (Button) findViewById(R.id.button_cadastrarsucesso_voltar);
    }


}
