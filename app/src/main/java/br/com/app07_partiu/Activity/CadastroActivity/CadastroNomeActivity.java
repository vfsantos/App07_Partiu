package br.com.app07_partiu.Activity.CadastroActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.RestauranteNetwork;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class CadastroNomeActivity extends AppCompatActivity {

    public static final String CADASTRONOMECLIENTE = "br.com.app07_partiu.Activity.CadastroActivity.cadastronomecliente";
    public static final String CADASTRONOMEENDERECO = "br.com.app07_partiu.Activity.CadastroActivity.cadastronomeendereco";

    //Context
    private Context context;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewNome;
    private TextView textViewNomeComplemento;
    private TextView textViewTermosePoliticasPart1;
    private TextView textViewTermosePoliticasPart2;


    //EditText
    private EditText editTextNome;


    //Button
    private Button buttonVoltar;
    private Button buttonCriar;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToCadastroSucesso;
    private Intent intentCadastroCliente;


    //Objeto
    Usuario cadastroCliente;
    Endereco enderecoCliente;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nome);

        implementarComponentes();

        editTextNome.addTextChangedListener(nomeTextWatcher);

        intentCadastroCliente = getIntent();
        cadastroCliente = new Usuario();
        enderecoCliente = new Endereco();
        cadastroCliente = (Usuario) intentCadastroCliente.getSerializableExtra(CadastroCPFActivity.CADASTROCPF);
        enderecoCliente = (Endereco) intentCadastroCliente.getSerializableExtra(CadastroCPFActivity.CADASTROCPFENDERECO);
        System.out.println("Teste retorna valor da página anterior --->" +
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getEmail()+
                "\n "+cadastroCliente.getSenha()+
                "\n "+cadastroCliente.getDta_nascimento()+
                "\n "+cadastroCliente.getGenero()+
                "\n "+enderecoCliente.getCep()+
                "\n "+enderecoCliente.getLogradouro()+
                "\n "+enderecoCliente.getNumero()+
                "\n "+enderecoCliente.getComplemento()+
                "\n "+enderecoCliente.getBairro()+
                "\n "+enderecoCliente.getCidade()+
                "\n "+enderecoCliente.getUf()+
                "\n "+cadastroCliente.getDdd()+
                "\n "+cadastroCliente.getTelefone());
    }

    private TextWatcher nomeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nomeInput = editTextNome.getText().toString().trim();
            boolean nomeInputValidation = false;
            if (nomeInput.length() >=1 ) {
                buttonCriar.setEnabled(true);
                buttonCriar.setTextColor(getResources().getColor(R.color.rosa_100));
            } else{
                buttonCriar.setEnabled(false);
                buttonCriar.setTextColor(getResources().getColor(R.color.cinza_100));

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

    public void onClickCriarConta(View view) {
        cadastroCliente = new Usuario();
        String nomeInput = editTextNome.getText().toString();
        cadastroCliente.setEmail(nomeInput);

        criarUsuario(cadastroCliente.getTipo(), Integer.toString((int) cadastroCliente.getCpf()), cadastroCliente.getNome(), cadastroCliente.getDta_nascimento(), cadastroCliente.getEmail(), Integer.toString((int) cadastroCliente.getDdd()),
                Integer.toString((int) cadastroCliente.getTelefone()), Character.toString((char) cadastroCliente.getGenero()), cadastroCliente.getSenha(), enderecoCliente.getLogradouro(), enderecoCliente.getNumero(), enderecoCliente.getComplemento(),
                enderecoCliente.getBairro(), enderecoCliente.getCidade(), enderecoCliente.getUf(), enderecoCliente.getCep());

    }

    public void criarUsuario(final String tipo,final String cpf,final String nome, final String dta_nascimento, final String email, final String ddd,
                             final String telefone,final String genero, final String senha, final String logradouro, final String numero, final String complemento,
                             final String bairro, final String cidade, final String uf, final String cep) {

        usuario = new Usuario();
        intentToCadastroSucesso = new Intent(CadastroNomeActivity.this, CadastroSucesso.class);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            UsuarioNetwork.criarCadastroCliente(Connection.URL, tipo, cpf, nome, dta_nascimento, email, ddd,
                                    telefone, genero, senha, logradouro, numero, complemento, bairro, cidade, uf, cep);
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  startActivity(intentToCadastroSucesso);
                                              }
                                          }
                            );

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }).start();

    }


    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutVoltar        = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarnome_voltar);


        //TextView
        textViewTitulo                = (TextView) findViewById(R.id.textView_cadastrarnome_titulo);
        textViewNome                  = (TextView) findViewById(R.id.textview_cadastrarnome_nome);
        textViewNomeComplemento       = (TextView) findViewById(R.id.textview_cadastrarnome_complemento);
        textViewTermosePoliticasPart1 = (TextView) findViewById(R.id.textview_cadastrarnome_termosepoliticas_part1);
        textViewTermosePoliticasPart2 = (TextView) findViewById(R.id.textview_cadastrarnome_termosepoliticas_part2);


        //EditText
        editTextNome                  = (EditText) findViewById(R.id.edittext_cadastrarnome_nome);


        //Button
        buttonCriar                 = (Button) findViewById(R.id.button_cadastrarnome_criar);
    }

}
