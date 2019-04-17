package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Estabelecimento;
import br.com.app07_partiu.Network.GarcomNetwork;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.R;

public class MainActivity extends AppCompatActivity {

    //ImageView
    private ImageView imageViewLogo;
    private ImageView imageViewOu;

    //EditText
    private EditText editTextEmail;
    private EditText editTextSenha;

    //TextInputLayout
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutSenha;

    //Button
    private Button buttonEntrar;
    private Button buttonCadastrese;
    private Button buttonEsqueceuSuaSenha;

    //Intent
    public Intent intentErro;
    public Intent intentCodigoComandaCliente;
    public Intent intentCadastro;
    public Intent intentEsqueceuSuaSenha;
    public Intent intentListaComandasGarcom;
    public Intent intent;


    public static final String URL = "http://10.0.2.2:3000/comandas";
    public static final String COMANDAS = "br.com.app07_partiu.comandas";
    public static final String ESTABELECIMENTOS = "br.com.app07_partiu.comandas";

    //Array
    Comanda[] comandas;
    Estabelecimento[] estabelecimentos;
    ComandaConvertView[] comandaConvertView;

    Context contexto;

    //Progressbar
    ProgressBar progressBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imageView
        imageViewLogo = (ImageView) findViewById(R.id.image_view_login_logo);
        imageViewOu = (ImageView) findViewById(R.id.image_view_ou);

        //editText
        editTextEmail = (EditText) findViewById(R.id.edit_text_login_email);
        editTextSenha = (EditText) findViewById(R.id.edit_text_login_senha);

        //textInputLayout
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.text_input_layout_login_email);
        textInputLayoutSenha = (TextInputLayout) findViewById(R.id.text_input_layout_login_senha);

        //button
        buttonEntrar = (Button) findViewById(R.id.button_login_entrar);
        buttonCadastrese = (Button) findViewById(R.id.button_login_cadastrarse);
        buttonEsqueceuSuaSenha = (Button) findViewById(R.id.button_esqueceu_sua_senha);

        //progressBar
        progressBarTime = (ProgressBar)findViewById(R.id.progress_bar_time);
        progressBarTime.setVisibility(View.INVISIBLE);

        contexto = this;

    }

    public void validarLogin() {
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        if(email.isEmpty()) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Preencha com seu e-mail");
        } else {
            textInputLayoutEmail.setErrorEnabled(false);

            if(senha.isEmpty() || senha.length() > 8) {
                textInputLayoutSenha.setErrorEnabled(true);
                textInputLayoutSenha.setError("Sua senha deve possuir mínimo 8 caracteres.");
            } else {
                textInputLayoutSenha.setErrorEnabled(false);
                validarUsuario(email, senha);

            }
        }
    }

    public void validarUsuario(String email, String senha) {
        String emailCliente = "cliente@gmail.com";
        String emailGarcom = "garcom@gmail.com";
        String senhaCliente = "teste1";
        String senhaGarcom = "teste2";

        if(email.equals(emailCliente) && senha.equals(senhaCliente)){
            textInputLayoutEmail.setErrorEnabled(false);
            intentCodigoComandaCliente = new Intent(MainActivity.this, CodigoComandaClienteActivity.class);
            startActivity(intentCodigoComandaCliente);
        } else {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Usuário invalido");
        }

        if(email.equals(emailGarcom) && senha.equals(senhaGarcom)){
            textInputLayoutEmail.setErrorEnabled(false);
            listarComandas();

        } else {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Usuário invalido");
        }

    }

    public void onClickButtonLoginEntrar (View view) {
        validarLogin();

    }

    public void onClickButtonLoginCadastrarse (View view) {
        intentCadastro = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intentCadastro);
    }

    public void onClickButtonLoginEsqueceuSuaSenha (View view) {
        intentEsqueceuSuaSenha = new Intent(MainActivity.this, EsqueceuSuaSenhaActivity.class);
        startActivity(intentEsqueceuSuaSenha);
    }

    public void listarComandas() {
        intent = new Intent(this, ListaComandasGarcomActivity.class);

        if(GarcomNetwork.isConnected(this)) {
            progressBarTime.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comandas = GarcomNetwork.buscarComandas(URL);

                                //insere no banco o que conseguiu
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intent.putExtra(COMANDAS, comandas);
                                                      startActivity(intent);
                                                      progressBarTime.setVisibility(View.INVISIBLE);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        } else {
            Toast.makeText(this, "Rede inativa. Usando armazenamento local.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    //Código abaixo é referente a parte de recomendação e nesse primeiro momento não deve ser considerado

    /*
    public void listarEstabelecimentosEmAlta(View view) {
        intent = new Intent(this, RecomendacaoActivity.class);
        if (RecomendacaoNetwork.isConnected(this)) {
            timer.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                estabelecimentos = RecomendacaoNetwork.buscaListaEstabelecimento(URL, "em_alta");
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intent.putExtra(ESTABELECIMENTO, estabelecimentos);
                                                      startActivity(intent);
                                                      timer.setVisibility(View.INVISIBLE);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        } else {
            intentCadastro = new Intent(MainActivity.this, ErroConeccaoActivity.class);
            startActivity(intentCadastro);
        }
    }



    public void listarEstabelecimentosSugestaoParaVoce(View view) {
        intent = new Intent(this, RecomendacaoActivity.class);
        if (RecomendacaoNetwork.isConnected(this)) {
            timer.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                estabelecimentos = RecomendacaoNetwork.buscaListaEstabelecimento(URL, "sugestao_para_voce");
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intent.putExtra(ESTABELECIMENTO, estabelecimentos);
                                                      startActivity(intent);
                                                      timer.setVisibility(View.INVISIBLE);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        } else {
            intentCadastro = new Intent(MainActivity.this, ErroConeccaoActivity.class);
            startActivity(intentCadastro);
        }
    }

    public void listarEstabelecimentosVisiteNovamente(View view) {
        intent = new Intent(this, RecomendacaoActivity.class);
        if (RecomendacaoNetwork.isConnected(this)) {
            timer.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                estabelecimentos = RecomendacaoNetwork.buscaListaEstabelecimento(URL, "visite_novamente");
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intent.putExtra(ESTABELECIMENTO, estabelecimentos);
                                                      startActivity(intent);
                                                      timer.setVisibility(View.INVISIBLE);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        } else {
            intentCadastro = new Intent(MainActivity.this, ErroConeccaoActivity.class);
            startActivity(intentCadastro);
        }
    }
*/

}
