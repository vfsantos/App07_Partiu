package br.com.app07_partiu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Activity.CadastroActivity.CadastroEmailActivity;
import br.com.app07_partiu.R;

public class MainClienteActivity extends AppCompatActivity {

    //ImageView
    private ImageView imageViewLogo;


    //TextView
    private TextView textViewEscolhaUmaOpcaoParaContinuar;


    //Button
    private Button    buttonCadatrar;
    private Button    buttonEntrar;


    //Intent
    private Intent    intentToCadastroEmail;
    private Intent    intentToLoginCliente;
    private Intent    intentToMainGarcom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);
        implementarComponentes();
    }

    public void onClickCadastro(View view) {
        intentToCadastroEmail = new Intent(this, CadastroEmailActivity.class);
        startActivity(intentToCadastroEmail);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }


    public void onClickEntrarCliente(View view) {
        intentToLoginCliente = new Intent(this, LoginClienteActivity.class);
        startActivity(intentToLoginCliente);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }



    private void implementarComponentes() {
        //ImageView
        imageViewLogo                        = (ImageView) findViewById(R.id.imageView_logo);

        //TextView
        textViewEscolhaUmaOpcaoParaContinuar = (TextView) findViewById(R.id.textView_escolhaUmaOpcaoParaContinuar);

        //Button
        buttonCadatrar                       = (Button) findViewById(R.id.button_cadastrar);
        buttonEntrar                         = (Button) findViewById(R.id.button_cadastrosucesso_entrar);
    }


}
