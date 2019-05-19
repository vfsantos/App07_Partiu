package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.zip.Inflater;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Estabelecimento;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.RestauranteNetwork;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;

public class LoginActivity extends AppCompatActivity {

    //ImageView
    private ImageView imageViewLogo;

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
    public Intent intentListarComanda;
    public Intent intentLoginGarcom;
    public Intent intentLoginCliente;

    //Snackbar
    private Snackbar snackbarErroLogin;

    public static final String COMANDAS = "br.com.app07_partiu.LoginActivity.comandas";
    public static final String USUARIO = "br.com.app07_partiu.LoginActivity.usuario";
    public static final String RESTAURANTE = "br.com.app07_partiu.LoginActivity.restaurante";

    //Array
    ComandaConvertView[] comandas;
    Estabelecimento[] estabelecimentos;
    ComandaConvertView[] comandaConvertView;

    Context contexto;

    //Progressbar
    ProgressBar progressBarTime;

    private final boolean testeGarcom = true;
    Usuario usuario;
    Restaurante restaurante;

    //String
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        contexto = this;;

        //Setar um e-mail e senha fixo para texte
        if (testeGarcom){
            editTextEmail.setText("benjamin.bento@gmail.com");
            editTextSenha.setText("123");
        }
    }

    public void validarLogin() {
        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

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
                login(email, senha);

            }
        }
    }

    public void login(String email, String senha){
        final String enderecoEmailUsuario = email;
        final String senhaUsuario = senha;

        intentLoginGarcom = new Intent(this, HomeGarcomActivity.class);
        intentLoginCliente = new Intent(this, ExplorarClienteActivity.class);

        if(UsuarioNetwork.isConnected(this)) {

            //TODO consertar progressBarTime
            //TODO parte Cliente
            //progressBarTime.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                usuario = UsuarioNetwork.login(Connection.URL, enderecoEmailUsuario, senhaUsuario);
                                Log.d("TESTES", usuario.toString());


                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      if (usuario.getTipo().equals("garcom")){
                                                          getRestauranteByIdGarcom();
                                                      }else{
                                                          intentLoginCliente.putExtra(USUARIO, usuario);
                                                          startActivity(intentLoginCliente);
                                                      }
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("TESTES", "Retornou 'Usuario Inválido!'");
                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        snackbarErroLogin = Snackbar.make(findViewById(R.id.constraintLayoutLogin), R.string.snackbar_erro_login, Snackbar.LENGTH_LONG);
                                        snackbarErroLogin.show();

                                    }
                                });
                            }
                        }
                    }).start();


        } else {
            Toast.makeText(this, "Rede inativa", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButtonLoginEntrar (View view) {
        validarLogin();

    }

    public void onClickButtonLoginCadastrarse (View view) {
        intentCadastro = new Intent(LoginActivity.this, CadastroClienteActivity.class);
        startActivity(intentCadastro);
    }

    public void onClickButtonLoginEsqueceuSuaSenha (View view) {
        intentEsqueceuSuaSenha = new Intent(LoginActivity.this, EsqueceuSuaSenhaActivity.class);
        startActivity(intentEsqueceuSuaSenha);
    }




    public void listarComandas() {
        intentListarComanda = new Intent(this, HomeGarcomActivity.class);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comandas = ComandaNetwork.buscarComandas(Connection.URL, usuario.getId(), 'A');
                                Log.d("TESTES", comandas.toString());

                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intentListarComanda.putExtra(RESTAURANTE, restaurante);
                                                      intentListarComanda.putExtra(USUARIO, usuario);
                                                      intentListarComanda.putExtra(COMANDAS, comandas);
                                                      startActivity(intentListarComanda);
                                                      //TODO consertar progressBarTime
                                                      //progressBarTime.setVisibility(View.INVISIBLE);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

    }

    public void getRestauranteByIdGarcom() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            restaurante = RestauranteNetwork.getRestauranteByIdGarcom(Connection.URL, usuario.getId());

                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  listarComandas();
                                              }
                                          }
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

    }

    public void inicializarComponentes(){
        //ImageView
        imageViewLogo = (ImageView) findViewById(R.id.image_view_login_logo);

        //EditText
        editTextEmail = (EditText) findViewById(R.id.edit_text_login_email);
        editTextSenha = (EditText) findViewById(R.id.edit_text_login_senha);

        //TextInputLayout
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.text_input_layout_login_email);
        textInputLayoutSenha = (TextInputLayout) findViewById(R.id.text_input_layout_login_senha);

        //Button
        buttonEntrar = (Button) findViewById(R.id.button_login_entrar);
        buttonCadastrese = (Button) findViewById(R.id.button_login_cadastrarse);
        buttonEsqueceuSuaSenha = (Button) findViewById(R.id.button_esqueceu_sua_senha);

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
            intentCadastro = new Intent(LoginActivity.this, ErroConeccaoActivity.class);
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
            intentCadastro = new Intent(LoginActivity.this, ErroConeccaoActivity.class);
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
            intentCadastro = new Intent(LoginActivity.this, ErroConeccaoActivity.class);
            startActivity(intentCadastro);
        }
    }
*/

     /* public void validarUsuario(String email, String senha) {
        login(email,senha);
//        String emailCliente = "cliente@gmail.com";
//        String emailGarcom = "garcom@gmail.com";
//        String senhaCliente = "teste1";
//        String senhaGarcom = "teste2";
//
//
//
//        if(email.equals(emailCliente) && senha.equals(senhaCliente)){
//            textInputLayoutEmail.setErrorEnabled(false);
//            intentCodigoComandaCliente = new Intent(LoginActivity.this, CodigoComandaClienteActivity.class);
//            startActivity(intentCodigoComandaCliente);
//        } else {
//            textInputLayoutEmail.setErrorEnabled(true);
//            textInputLayoutEmail.setError("Usuário invalido");
//        }
//
//        if(email.equals(emailGarcom) && senha.equals(senhaGarcom)){
//            textInputLayoutEmail.setErrorEnabled(false);
////            listarComandas();
//
//        } else {
//            textInputLayoutEmail.setErrorEnabled(true);
//            textInputLayoutEmail.setError("Usuário invalido");
//        }
    }*/

}
