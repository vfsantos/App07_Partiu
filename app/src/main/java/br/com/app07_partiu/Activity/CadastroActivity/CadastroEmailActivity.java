package br.com.app07_partiu.Activity.CadastroActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class CadastroEmailActivity extends AppCompatActivity {

    public static final String CADASTROEMAIL = "br.com.app07_partiu.Activity.CadastroActivity.cadastroemail";

    //Context
    private Context context;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewEmail;
    private TextView textViewEmailComplemento;


    //EditText
    private EditText editTextEmail;


    //Button
    private Button buttonVoltar;
    private Button buttonAvancar;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToCadastroSenha;


    //Objeto
    public Usuario cadastroCliente;

    private boolean usuarioExiste;


    //Snackbar
    private View viewSnackbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_email);
        implementarComponentes();

        editTextEmail.addTextChangedListener(emailTextWatcher);
    }



    private TextWatcher emailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = editTextEmail.getText().toString().trim();
            boolean emailInputValidation = false;
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                buttonAvancar.setEnabled(true);
                buttonAvancar.setTextColor(getResources().getColor(R.color.rosa_100));
            } else{
                buttonAvancar.setEnabled(false);
                buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void onClickVoltarMainCliente(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    public void onClickAvancarToCadastroSenha(View view) {
        cadastroCliente = new Usuario();
        String emailInput = editTextEmail.getText().toString();
        cadastroCliente.setEmail(emailInput);
        cadastroCliente.setTipo("cliente");
        intentToCadastroSenha = new Intent(CadastroEmailActivity.this, CadastroSenhaActivity.class);
        intentToCadastroSenha.putExtra(CADASTROEMAIL, cadastroCliente);
        startActivity(intentToCadastroSenha);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        System.out.println("Teste retorna valor da desta página --->" +
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getEmail());
       // validarUsuarioExiste("email",emailInput);
    }

/*
    public void validarUsuarioExiste (final String variavel, final String valor) {

        if(Connection.isConnected(this, viewSnackbar)) {

            Thread cadastroEmailThreadThread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                usuarioExiste = UsuarioNetwork.getUsuario(Connection.URL, variavel, valor);
                                if (usuarioExiste == true){

                                    runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {

                                                          // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                                                          AlertDialog.Builder builder = new AlertDialog.Builder(CadastroEmailActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics
                                                          builder.setMessage(R.string.text1_comanda_codigo_da_mesa)
                                                                  .setTitle(R.string.text1_comanda_codigo_da_mesa);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                                                          AlertDialog dialog = builder.create();

                                                      }
                                                  }
                                    );

                                } else{

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try{
                                                cadastroCliente.setEmail(valor);
                                                intentToCadastroSenha = new Intent(CadastroEmailActivity.this, CadastroSenhaActivity.class);
                                                intentToCadastroSenha.putExtra(CADASTROEMAIL, cadastroCliente);
                                                startActivity(intentToCadastroSenha);
                                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },"CadastroEmailThread");
            cadastroEmailThreadThread.start();
        }
    }*/


    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutVoltar = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastraremail_voltar);


        //TextView
        textViewTitulo = (TextView) findViewById(R.id.textView_cadastraremail_titulo);
        textViewEmail = (TextView) findViewById(R.id.textview_cadastraremail_email);
        textViewEmailComplemento = (TextView) findViewById(R.id.textview_cadastraremail_complemento);


        //EditText
        editTextEmail = (EditText) findViewById(R.id.edittext_cadastraremail_email);


        //Button
        buttonAvancar = (Button) findViewById(R.id.button_cadastraremail_avancar);


        //Snackbar
        viewSnackbar = findViewById(R.id.cadastroEmailActivity);
    }
}


