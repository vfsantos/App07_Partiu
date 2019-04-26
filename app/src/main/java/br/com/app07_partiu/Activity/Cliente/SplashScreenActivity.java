package br.com.app07_partiu.Activity.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.app07_partiu.Activity.MainActivity;
import br.com.app07_partiu.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_acitivy);

        //chama o splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(homeIntent);
                //adiciona efeito de transição suave entre as activities
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }



}
