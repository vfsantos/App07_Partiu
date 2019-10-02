package br.com.app07_partiu.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.CadastroActivity.CadastroEnderecoActivity;
import br.com.app07_partiu.Activity.CadastroActivity.CadastroNomeActivity;
import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Estabelecimento;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Model.ValidaCPF;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.RecomendacaoNetwork;
import br.com.app07_partiu.Network.RestauranteNetwork;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class LoginClienteActivity extends AppCompatActivity {


    public static final String COMANDAS                            = "br.com.app07_partiu.LoginActivity.comandas";
    public static final String USUARIO                             = "br.com.app07_partiu.LoginActivity.usuario";
    public static final String RESTAURANTE                         = "br.com.app07_partiu.LoginActivity.restaurante";
    public static final String RECOMENDACOES_DIASEMANA             = "br.com.app07_partiu.LoginActivity.recomendacoesDiaSemana";
    public static final String RECOMENDACOES_MAISVISITADOS         = "br.com.app07_partiu.LoginActivity.recomendacoesMaisVisitados";
    public static final String RECOMENDACOES_ESPECIALIDADEUSUARIO  = "br.com.app07_partiu.LoginActivity.recomendacoesEspecialidadeUsuario";
    public static final String RECOMENDACOES_VISITADOSRECENTEMENTE = "br.com.app07_partiu.LoginActivity.recomendacoesVisitadosRecentemente";
    public static final String RECOMENDACOES_RESTAURANTEAVALIADO   = "br.com.app07_partiu.LoginActivity.recomendacoesRestauranteAvaliado";


    //Context
    private Context contexto;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;



    //ImageView
    private ImageView imageViewLogo;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewEmail;
    private TextView textViewSenha;
    private TextView textViewEmailComplemento;
    private TextView textViewSenhaComplemento;


    //EditText
    private EditText editTextEmail;
    private EditText editTextSenha;


    //Button
    private Button buttonVoltar;
    private Button buttonEntrar;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToExplore;
    public Intent intentListarComanda;
    public Intent intentListarRecomendacoes;


    //Snackbar
    private Snackbar snackbarErroLogin;


    //Array - Item do carrossel de recomeendção
    private Restaurante[] recomendacaoAvaliacao;
    private Restaurante[] recomendacaoDia;
    private Restaurante[] recomendacaoEmAlta;
    private Restaurante[] recomendacaoEspecialidade;
    private Restaurante[] recomendacaoRecente;


    //Array
    ComandaConvertView[] comandas;
    Estabelecimento[]    estabelecimentos;
    ComandaConvertView[] comandaConvertView;


    //Progressbar
    private final boolean testeGarcom  = false;
    private final boolean testeCliente = true;


    //Objeto
    Usuario     usuario;
    Restaurante restaurante;


    //String
    private String email;
    private String senha;


    //View
    private View viewSnackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cliente);
        implementarComponentes();

        contexto = this;
        viewSnackbar = findViewById(R.id.loginActivityView);


        //Setar um e-mail e senha fixo para texte
        if (testeGarcom){
            editTextEmail.setText("benjamin.bento@gmail.com");
        }
        if (testeCliente){
            editTextEmail.setText("brenda.mariah@gmail.com");
        }
        editTextSenha.setText("123");


    }


    public void validarLogin() {
        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

        if(email.isEmpty()) {
            textViewEmailComplemento.setText("Preencha com seu e-mail");
            buttonEntrar.setEnabled(true);
        } else {

            if(senha.isEmpty()) {
                textViewSenhaComplemento.setText("Insira a senha");
            } else {
                login(email, senha);

            }
        }
    }


    //TODO disable botão Login
    public void onClickButtonLoginEntrar(View view) {
        validarLogin();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        buttonEntrar.setEnabled(true);
    }


    public void login(final String email, final String senha){

        if(Connection.isConnected(this, viewSnackbar)) {

            buttonEntrar.setEnabled(false);

            intentListarComanda = new Intent(this, HomeGarcomActivity.class);
            intentListarRecomendacoes = new Intent(this, ExplorarClienteActivity.class);
            final ProgressDialog mProgressDialog = ProgressDialog.show(this, null,"Carregando informações ;)", true);
            Thread loginThread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                usuario = UsuarioNetwork.login(Connection.URL, email, senha);
                                Log.d("TESTES", usuario.toString());
                                if (usuario.getTipo().equals("garcom")){

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
                                                          try {
                                                              Thread.sleep(500);
                                                          } catch (InterruptedException e) {
                                                              e.printStackTrace();
                                                          }
                                                          mProgressDialog.dismiss();
                                                      }
                                                  }
                                    );

                                }else{
//                                    recomendacaoAvaliacao       = RecomendacaoNetwork.getRecomendacaoRestauranteAvaliado(Connection.URL);
//                                    recomendacaoDia             = RecomendacaoNetwork.getRecomendacaoDiaSemana(Connection.URL);
//                                    recomendacaoEmAlta          = RecomendacaoNetwork.getRecomendacaoMaisVisitados(Connection.URL);
//                                    recomendacaoEspecialidade   = RecomendacaoNetwork.getRecomendacaoEspecialidadeUsuario(Connection.URL, usuario.getId());
//                                    recomendacaoRecente         = RecomendacaoNetwork.getRecomendacaoVisitadoRecentemente(Connection.URL, usuario.getId());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            intentListarRecomendacoes.putExtra(USUARIO, usuario);
//                                            intentListarRecomendacoes.putExtra(RECOMENDACOES_RESTAURANTEAVALIADO, recomendacaoAvaliacao);
//                                            intentListarRecomendacoes.putExtra(RECOMENDACOES_DIASEMANA, recomendacaoDia);
//                                            intentListarRecomendacoes.putExtra(RECOMENDACOES_MAISVISITADOS, recomendacaoEmAlta);
//                                            intentListarRecomendacoes.putExtra(RECOMENDACOES_ESPECIALIDADEUSUARIO, recomendacaoEspecialidade);
//                                            intentListarRecomendacoes.putExtra(RECOMENDACOES_VISITADOSRECENTEMENTE, recomendacaoRecente);
                                            startActivity(intentListarRecomendacoes);
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            mProgressDialog.dismiss();


                                        }
                                    });
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("TESTES", "LoginActivity: IOException login");
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);


                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      buttonEntrar.setEnabled(true);
                                                      mProgressDialog.dismiss();
                                                  }
                                              }
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("TESTES", "Retornou 'Usuario Inválido!'");
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_login);
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      buttonEntrar.setEnabled(true);
                                                      mProgressDialog.dismiss();
                                                  }
                                              }
                                );
                            }


                        }
                    },"LoginThread");

            loginThread.start();
        }else buttonEntrar.setEnabled(true);
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
                                              }
                                          }
                            );

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("TESTES", "LoginActivity: IOException getRestauranteComandasGarcom");
                            Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        buttonEntrar.setEnabled(true);

                    }

                }).start();

    }

    public void getRecomendacoesCliente() {
        intentListarRecomendacoes = new Intent(this, ExplorarClienteActivity.class);
        if (Connection.isConnected(this, viewSnackbar)) {
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
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("TESTES", "LoginActivity: IOException getRecomendacoesCLiente");
                                Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                            }

                        }
                    }).start();
        }else buttonEntrar.setEnabled(true);
    }

    public void onClickVoltarMainCliente(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutVoltar   = (ConstraintLayout) findViewById(R.id.constraintLayout_logincliente_voltar);


        //TextView
        textViewTitulo           = (TextView) findViewById(R.id.textView_logincliente_titulo);
        textViewEmail            = (TextView) findViewById(R.id.textview_logincliente_email);
        textViewSenha            = (TextView) findViewById(R.id.textview_logincliente_senha);
        textViewEmailComplemento = (TextView) findViewById(R.id.textView_logincliente_emailcomplemento);
        textViewSenhaComplemento = (TextView) findViewById(R.id.textView_logincliente_senhacomplemento);


        //EditText
        editTextEmail            = (EditText) findViewById(R.id.edittext_logincliente_email);
        editTextSenha            = (EditText) findViewById(R.id.edittext_logincliente_senha);


        //Button
        buttonEntrar              = (Button) findViewById(R.id.button_logincliente_entrar);


    }

}
