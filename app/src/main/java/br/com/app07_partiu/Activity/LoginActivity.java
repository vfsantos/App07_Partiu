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
import br.com.app07_partiu.Model.ItemRestauranteConvertView;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.RecomendacaoNetwork;
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
    public Intent intentListarRecomendacoes;


    //Snackbar
    private Snackbar snackbarErroLogin;

    public static final String COMANDAS = "br.com.app07_partiu.LoginActivity.comandas";
    public static final String USUARIO = "br.com.app07_partiu.LoginActivity.usuario";
    public static final String RESTAURANTE = "br.com.app07_partiu.LoginActivity.restaurante";
    public static final String RECOMENDACOES_DIASEMANA = "br.com.app07_partiu.LoginActivity.recomendacoesDiaSemana";
    public static final String RECOMENDACOES_MAISVISITADOS = "br.com.app07_partiu.LoginActivity.recomendacoesMaisVisitados";
    public static final String RECOMENDACOES_ESPECIALIDADEUSUARIO = "br.com.app07_partiu.LoginActivity.recomendacoesEspecialidadeUsuario";
    public static final String RECOMENDACOES_VISITADOSRECENTEMENTE = "br.com.app07_partiu.LoginActivity.recomendacoesVisitadosRecentemente";
    public static final String RECOMENDACOES_RESTAURANTEAVALIADO = "br.com.app07_partiu.LoginActivity.recomendacoesRestauranteAvaliado";


    //Array - Item do carrossel de recomeendção
    private Restaurante[] recomendacaoAvaliacao;
    private Restaurante[] recomendacaoDia;
    private Restaurante[] recomendacaoEmAlta;
    private Restaurante[] recomendacaoEspecialidade;
    private Restaurante[] recomendacaoRecente;


    //Array
    ComandaConvertView[] comandas;
    Estabelecimento[] estabelecimentos;
    ComandaConvertView[] comandaConvertView;

    Context contexto;

    //Progressbar
    ProgressBar progressBarTime;

    private final boolean testeGarcom = true;
    private final boolean testeCliente = false;

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

        if (testeCliente){
            editTextEmail.setText("brenda.mariah@gmail.com");
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

    public void onClickButtonLoginEntrar (View view) {
        validarLogin();
    }

    public void login(String email, String senha){
        final String enderecoEmailUsuario = email;
        final String senhaUsuario = senha;

        //intentLoginGarcom = new Intent(this, HomeGarcomActivity.class);
        //intentLoginCliente = new Intent(this, ExplorarClienteActivity.class);

        if(Connection.isConnected(this)) {
            //TODO consertar progressBarTime
//            progressBarTime.setVisibility(View.VISIBLE);
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
                                                          getRestauranteComandasGarcom();
                                                      }else{
                                                          getRecomendacoesCliente();
                                                      }
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "Erro no webservice ou na conexão");
                                snackbarErroLogin = Snackbar.make(findViewById(R.id.constraintLayoutLogin), "Erro no webservice ou na conexão", Snackbar.LENGTH_LONG);
                                snackbarErroLogin.show();

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


        }
    }

    public void getRestauranteComandasGarcom() {
        intentListarComanda = new Intent(this, HomeGarcomActivity.class);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            restaurante = RestauranteNetwork.getRestauranteByIdGarcom(Connection.URL, usuario.getId());
                            comandas = ComandaNetwork.buscarComandas(Connection.URL, usuario.getId(), 'A');
                            Log.d("TESTES", comandas.toString());
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  intentListarComanda.putExtra(USUARIO, usuario);

                                                  intentListarComanda.putExtra(RESTAURANTE, restaurante);
                                                  intentListarComanda.putExtra(COMANDAS, comandas);
                                                  startActivity(intentListarComanda);
                                                  //TODO consertar progressBarTime
                                                  //progressBarTime.setVisibility(View.INVISIBLE);
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

    public void getRecomendacoesCliente() {
        intentListarRecomendacoes = new Intent(this, ExplorarClienteActivity.class);
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //TODO ver se a performance disso é tão ruim quanto parece
                                recomendacaoAvaliacao       = RecomendacaoNetwork.getRecomendacaoRestauranteAvaliado(Connection.URL);
                                recomendacaoDia             = RecomendacaoNetwork.getRecomendacaoDiaSemana(Connection.URL);
                                recomendacaoEmAlta          = RecomendacaoNetwork.getRecomendacaoMaisVisitados(Connection.URL);
                                recomendacaoEspecialidade   = RecomendacaoNetwork.getRecomendacaoEspecialidadeUsuario(Connection.URL, usuario.getId());
                                recomendacaoRecente         = RecomendacaoNetwork.getRecomendacaoVisitadoRecentemente(Connection.URL, usuario.getId());

                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intentListarRecomendacoes.putExtra(USUARIO, usuario);

                                                      intentListarRecomendacoes.putExtra(RECOMENDACOES_RESTAURANTEAVALIADO, recomendacaoAvaliacao);
                                                      intentListarRecomendacoes.putExtra(RECOMENDACOES_DIASEMANA, recomendacaoDia);
                                                      intentListarRecomendacoes.putExtra(RECOMENDACOES_MAISVISITADOS, recomendacaoEmAlta);
                                                      intentListarRecomendacoes.putExtra(RECOMENDACOES_ESPECIALIDADEUSUARIO, recomendacaoEspecialidade);
                                                      intentListarRecomendacoes.putExtra(RECOMENDACOES_VISITADOSRECENTEMENTE, recomendacaoRecente);

                                                      startActivity(intentListarRecomendacoes);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                Log.d("TESTES", "Erro no getRecomendacoesCliente");
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }
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

}
