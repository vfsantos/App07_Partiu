package br.com.app07_partiu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class LoginClienteActivity extends AppCompatActivity {


    //Toolbar
    private Toolbar toolbar;


    //Context
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cliente);
        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                break;
            case R.id.action_settings: {
                Util.logoff(context);
            }
            default:break;
        }
        return true;
    }


    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);                //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);                     //Ativar o botão
            getSupportActionBar().setTitle(R.string.title_activity_logingarcom);  //Titulo para ser exibido na sua Action Bar em frente à seta
        }
    }

    private void implementarComponentes() {
        //Toolbar
        toolbar            = (Toolbar) findViewById(R.id.toolbar);
    }

}
