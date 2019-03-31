package br.com.app07_partiu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.telecom.RemoteConference;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {

    private ImageView imgeViewLogo;
    private ImageView imageViewOu;
    private AppCompatEditText editTextEmail;
    private EditText editTextSenha;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutSenha;
    private Button buttonEntrar;
    private Button buttonCadastrese;
    private Button buttonEsqueceuSuaSenha;
    public Intent intentLogin;
    public Intent intentCadastro;
    public Intent intentEsqueceuSuaSenha;


    public static final String URL = "https://xpto";
    public static final String ESTABELECIMENTO = "br.com.app07_partiu.estabelcimentos";
    public String categoria = "all";

    public Estabelecimento[] estabelecimentos;
    public Intent intent;
    public ProgressBar timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgeViewLogo = (ImageView) findViewById(R.id.image_view_login_logo);
        imageViewOu = (ImageView) findViewById(R.id.image_view_ou);
        editTextEmail = (AppCompatEditText) findViewById(R.id.edit_text_login_email);
        editTextSenha = (AppCompatEditText) findViewById(R.id.edit_text_login_senha);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.text_input_layout_login_email);
        textInputLayoutSenha = (TextInputLayout) findViewById(R.id.text_input_layout_login_senha);
        buttonEntrar = (Button) findViewById(R.id.button_login_entrar);
        buttonCadastrese = (Button) findViewById(R.id.button_login_cadastrarse);
        buttonEsqueceuSuaSenha = (Button) findViewById(R.id.button_esqueceu_sua_senha);

    }

    public void validateForm() {
        if(editTextEmail.getText().toString().isEmpty()) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Preencha com seu e-mail");
        } else {
            textInputLayoutEmail.setErrorEnabled(false);
        }

        if(editTextSenha.getText().toString().isEmpty()) {
            textInputLayoutSenha.setErrorEnabled(true);
            textInputLayoutSenha.setError("Preencha com sua senha");
        } else {
            textInputLayoutSenha.setErrorEnabled(false);
        }

    }

    public void onClickButtonLoginEntrar (View view) {
        validateForm();
    }

    public void onClickButtonLoginCadastrarse (View view) {
        intentCadastro = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intentCadastro);
    }

    public void onClickButtonLoginEsqueceuSuaSenha (View view) {
        intentEsqueceuSuaSenha = new Intent(MainActivity.this, EsqueceuSuaSenhaActivity.class);
        startActivity(intentEsqueceuSuaSenha);
    }

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

    */

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


}
