package br.com.app07_partiu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import br.com.app07_partiu.R;

public class MainGarcomActivity extends AppCompatActivity {

    //ImageView
    private ImageView imageViewLogo;


    //Button
    private Button    buttonEntrar;
    private Button    buttonNaoSouUmGarcom;


    //Intent
    private Intent    intentToMainCliente;
    private Intent    intentToLoginGarcom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_garcom);
        implementarComponentes();
    }


    public void onClickEntrarGarcom(View view) {
        intentToLoginGarcom = new Intent(this, LoginGarcomActivity.class);
        startActivity(intentToLoginGarcom);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    public void onClickNaoSouUmGarcom(View view) {
        intentToMainCliente = new Intent(this, MainClienteActivity.class);
        startActivity(intentToMainCliente);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    private void implementarComponentes() {
        //ImageView
        imageViewLogo                        = (ImageView) findViewById(R.id.imageView_logo);


        //Button
        buttonEntrar                         = (Button) findViewById(R.id.button_cadastrosucesso_entrar);
        buttonNaoSouUmGarcom                 = (Button) findViewById(R.id.button_souumgarcom);
    }

}
