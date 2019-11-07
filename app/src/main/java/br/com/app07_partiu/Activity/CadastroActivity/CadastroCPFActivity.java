package br.com.app07_partiu.Activity.CadastroActivity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity;
import br.com.app07_partiu.Activity.HomeGarcomActivity.HomeGarcomActivity;
import br.com.app07_partiu.Activity.LoginClienteActivity;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Model.ValidaCPF;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.RestauranteNetwork;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class CadastroCPFActivity extends AppCompatActivity {

    public static final String CADASTROCPF = "br.com.app07_partiu.Activity.CadastroActivity.cpf";
    public static final String CADASTROCPFENDERECO = "br.com.app07_partiu.Activity.CadastroActivity.endereco";

    //Context
    private Context context;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewCPF;
    private TextView textViewCPFComplemento;
    private TextView textViewTelefone;


    //EditText
    private EditText editTextCPF;
    private EditText editTextTelefone;


    //Button
    private Button buttonVoltar;
    private Button buttonAvancar;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToCadastroCPF;
    private Intent intentCadastroCliente;


    //Objeto
    public Usuario cadastroCliente;
    public Endereco enderecoCliente;


    //boolean
    private boolean telefonePreenchido;
    private boolean cpfPreenchido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cpf);

        implementarComponentes();

        editTextCPF.addTextChangedListener(cpfTextWatcher );
        editTextTelefone.addTextChangedListener(tefelefoneTextWatcher);

        intentCadastroCliente = getIntent();
        cadastroCliente = new Usuario();
        enderecoCliente = new Endereco();
        cadastroCliente = (Usuario) intentCadastroCliente.getSerializableExtra(CadastroEnderecoActivity.CADASTROENDERECO);
        enderecoCliente = (Endereco) intentCadastroCliente.getSerializableExtra(CadastroEnderecoActivity.ENDERECO);
        System.out.println("Teste retorna valor da página anterior --->" +
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getCpf()+" cpf era aqi"+
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
                "\n "+enderecoCliente.getUf());

    }

    private TextWatcher cpfTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cpfInput = editTextCPF.getText().toString().trim();

            if (cpfInput.length() >=1 && cpfInput.length() <=9 ) {
                System.out.printf("%s\n", cpfInput);
                cpfPreenchido = true;

            } else{
                cpfPreenchido = false;


            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher tefelefoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String telefone = editTextTelefone.getText().toString().trim();

            if (telefone.length() >= 10) {
                telefonePreenchido = true;

            } else{
                telefonePreenchido = false;

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

    public void onClickToNome(View view) {
        String telefoneCompleto = editTextTelefone.getText().toString();
        String telefoneInput    = telefoneCompleto.substring(2,11);
        String dddInput         = telefoneCompleto.substring(0,2);

        System.out.println(">-------- telefonecompleto: " + editTextTelefone.getText().toString());
        System.out.println(">-------- ddd: " + dddInput);
        System.out.println(">-------- telefone: " + telefoneInput);
        cadastroCliente.setCpf(editTextCPF.getText().toString());
        Log.i("XXXXXX", cadastroCliente.getCpf());
        cadastroCliente.setDdd(Integer.parseInt(dddInput));
        cadastroCliente.setTelefone(Integer.parseInt(telefoneInput));

        intentToCadastroCPF = new Intent(this, CadastroNomeActivity.class);
        intentToCadastroCPF.putExtra(CADASTROCPF, cadastroCliente);
        intentToCadastroCPF.putExtra(CADASTROCPFENDERECO, enderecoCliente);

        System.out.println("Teste retorna valor desta página --->" +
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
                "\n "+cadastroCliente.getCpf()+
                "\n "+cadastroCliente.getDdd()+
                "\n "+cadastroCliente.getTelefone());

        startActivity(intentToCadastroCPF);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutVoltar = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastraremail_voltar);


        //TextView
        textViewTitulo = (TextView) findViewById(R.id.textView_cadastrarcpf_titulo);
        textViewCPF = (TextView) findViewById(R.id.textview_cadastrarcpf_cpf);
        textViewCPFComplemento = (TextView) findViewById(R.id.textview_cadastrarcpf_complemento);
        textViewTelefone = (TextView) findViewById(R.id.textview_cadastrarcpf_telefone);


        //EditText
        editTextCPF = (EditText) findViewById(R.id.edittext_cadastrarcpf_cpf);
        editTextTelefone = (EditText) findViewById(R.id.edittext_cadastrarcpf_telefone);


        //Button
        buttonAvancar = (Button) findViewById(R.id.button_cadastraremail_avancar);


    }


}
