package br.com.app07_partiu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.app07_partiu.R;

public class CodigoComandaClienteActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewDescricao;
    private EditText editTextCodigoComanda;
    private Button buttonEntrarComanda;
    public Intent intentComanda;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_comanda_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewTitulo = (TextView) findViewById(R.id.text_view_codigo_comanda_titulo);
        textViewDescricao = (TextView) findViewById(R.id.text_view_codigo_comanda_descricao);
        editTextCodigoComanda = (EditText) findViewById(R.id.edit_texte_codigo_comanda_codigo);
        buttonEntrarComanda = (Button) findViewById(R.id.button_codigo_comanda_entrar);
    }

    public void validateForm() {
        if(editTextCodigoComanda.getText().toString().isEmpty() || editTextCodigoComanda.getText().toString().length() < 4) {
            alertErro();
        } else {
            intentComanda = new Intent(CodigoComandaClienteActivity.this, ComandaActivity.class);
            startActivity(intentComanda);
        }

    }

    private void alertErro(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(R.string.title_alert_codigo_comanda_invalido);
        //define a mensagem
        builder.setMessage(R.string.subtitle1_alert_codigo_comanda_invalido);
        //define um botão como positivo
        builder.setPositiveButton(R.string.btn_alert_ok_entendi, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void onClickButtonEntrarComanda (View view) {
        validateForm();
    }

}
