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
import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class CadastroSenhaActivity extends AppCompatActivity {

    public static final String CADASTROSENHA = "br.com.app07_partiu.Activity.CadastroActivity.cadastrosenha";

    //Context
    private Context context;


    //ImageView
    private ImageView imageViewVoltar;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewEmail;
    private TextView textViewEmailComplemento;


    //EditText
    private EditText editTextSenha;


    //Button
    private Button buttonVoltar;
    private Button buttonAvancar;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToCadastroDataNascimento;
    private Intent intentCadastroCliente;


    //Objeto
    public Usuario cadastroCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_senha);
        implementarComponentes();

        editTextSenha.addTextChangedListener(senhaTextWatcher);
        buttonAvancar.setEnabled(false);
        buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));


        intentCadastroCliente = getIntent();
        cadastroCliente = new Usuario();
        cadastroCliente = (Usuario) intentCadastroCliente.getSerializableExtra(CadastroEmailActivity.CADASTROEMAIL);
        System.out.println("Teste retorna valor da página anterior ---> "+
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getEmail());
    }


    private TextWatcher senhaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String senhaInput = editTextSenha.getText().toString().trim();
            boolean senhaInputValidation = false;
            if (senhaInput.length() >= 8) {
                buttonAvancar.setEnabled(true);
                buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
                buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
            } else {
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

    public void onClickToAvancarToCadastroDataNascimento(View view) {
        String senhaInput = editTextSenha.getText().toString();
        cadastroCliente.setSenha(senhaInput);
        try{
            intentToCadastroDataNascimento = new Intent(this, CadastroDataNascimentoActivity.class);
            intentToCadastroDataNascimento.putExtra(CADASTROSENHA, cadastroCliente);
            startActivity(intentToCadastroDataNascimento);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            System.out.println("Teste retorna valor da desta página --->" +
                    "\n "+cadastroCliente.getTipo()+
                    "\n "+cadastroCliente.getEmail()+
                    "\n "+cadastroCliente.getSenha());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutVoltar   = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarsenha_voltar);


        //ImageView
        imageViewVoltar          = (ImageView) findViewById(R.id.imageview_cadastrarsenha_voltar);


        //TextView
        textViewTitulo           = (TextView) findViewById(R.id.textView_cadastrarsenha_titulo);
        textViewEmail            = (TextView) findViewById(R.id.textview_cadastrarsenha_senha);
        textViewEmailComplemento = (TextView) findViewById(R.id.textview_cadastrarsenha_complemento);


        //EditText
        editTextSenha           = (EditText) findViewById(R.id.edittext_cadastrarsenha_senha);


        //Button
        buttonAvancar            = (Button) findViewById(R.id.button_cadastrarsenha_avancar);
    }

}
