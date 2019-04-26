package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import br.com.app07_partiu.Activity.Cliente.CadastroActivity;
import br.com.app07_partiu.Activity.Cliente.CodigoComandaClienteActivity;
import br.com.app07_partiu.Activity.Cliente.EsqueceuSuaSenhaActivity;
import br.com.app07_partiu.Activity.Garcom.ListaComandasGarcomActivity;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Estabelecimento;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.RestauranteNetwork;
import br.com.app07_partiu.Network.UsuarioNetwork;
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
    public Intent intentLoginGarcom;
    public Intent intentLoginCliente;

    //public static final String URL = "http://10.0.2.2:8080/partiu"; //emulador
    //public static final String URL = "http://192.168.43.193:8080/partiu"; //
    public static final String URL = "http://10.71.204.149/partiu";

    public static final String COMANDAS = "br.com.app07_partiu.comandas";
    public static final String USUARIO = "br.com.app07_partiu.usuario";
    public static final String RESTAURANTE = "br.com.app07_partiu.restaurante";

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
        setContentView(R.layout.activity_main);

        //ImageView
        imageViewLogo = (ImageView) findViewById(R.id.image_view_login_logo);
        imageViewOu = (ImageView) findViewById(R.id.image_view_ou);

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

        //ProgressBar
        progressBarTime = (ProgressBar)findViewById(R.id.progress_bar_time);
        progressBarTime.setVisibility(View.INVISIBLE);

        contexto = this;

        //Setar um e-mail e senha fixo para texte
        if (testeGarcom){
            editTextEmail.setText("benjamin@gmail.com");
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

        intentLoginGarcom = new Intent(this, ListaComandasGarcomActivity.class);
        intentLoginCliente = new Intent(this, CodigoComandaClienteActivity.class);

        if(UsuarioNetwork.isConnected(this)) {

            //TODO consertar progressBarTime
            //TODO parte Cliente
            //progressBarTime.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                usuario = UsuarioNetwork.login(URL, enderecoEmailUsuario, senhaUsuario);
                                Log.d("TESTES", usuario.toString());


                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      if (usuario.getTipo().equals("garcom")){
                                                          intentLoginGarcom.putExtra(USUARIO, usuario);
                                                          startActivity(intentLoginGarcom);
                                                          getRestauranteByIdGarcom(usuario);
                                                      }else{
                                                          intentLoginCliente.putExtra(USUARIO, usuario);
                                                          startActivity(intentLoginCliente);
                                                          //progressBarTime.setVisibility(View.INVISIBLE);
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
                                        Toast.makeText(contexto, "Usuario Inválido!",Toast.LENGTH_LONG).show();
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
        intentCadastro = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intentCadastro);
    }

    public void onClickButtonLoginEsqueceuSuaSenha (View view) {
        intentEsqueceuSuaSenha = new Intent(MainActivity.this, EsqueceuSuaSenhaActivity.class);
        startActivity(intentEsqueceuSuaSenha);
    }




    public void listarComandas(final Usuario garcom, final Restaurante restaurante) {
        intent = new Intent(this, ListaComandasGarcomActivity.class);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                comandas = ComandaNetwork.buscarComandas(URL, garcom.getId(), 'A');

                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intent.putExtra(RESTAURANTE, restaurante);
                                                      intent.putExtra(COMANDAS, comandas);
                                                      intent.putExtra(USUARIO, garcom);
                                                      startActivity(intent);
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

    public void getRestauranteByIdGarcom(final Usuario garcom) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            restaurante = RestauranteNetwork.getRestauranteByIdGarcom(URL, garcom.getId());

                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  listarComandas(garcom, restaurante);
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
//            intentCodigoComandaCliente = new Intent(MainActivity.this, CodigoComandaClienteActivity.class);
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
