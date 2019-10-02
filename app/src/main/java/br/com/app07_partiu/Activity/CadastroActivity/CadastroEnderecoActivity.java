package br.com.app07_partiu.Activity.CadastroActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class CadastroEnderecoActivity extends AppCompatActivity {

    public static final String CADASTROENDERECO = "br.com.app07_partiu.Activity.CadastroActivity.cadastroendereco";
    public static final String ENDERECO = "br.com.app07_partiu.Activity.CadastroActivity.endereco";

    //Context
    private Context context;


    //ImageView
    private ImageView imageViewVoltar;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;
    private ConstraintLayout constraintLayoutEndereco;
    private ConstraintLayout constraintLayoutComplemento;
    private ConstraintLayout constraintLayoutBairro;
    private ConstraintLayout constraintLayoutMunicipio;



    //TextView
    private TextView textViewTitulo;
    private TextView textViewCEP;
    private TextView textViewCEPcomplemento;
    private TextView textViewEndereco;
    private TextView textViewNumero;
    private TextView textViewComplemento;
    private TextView textViewBairro;
    private TextView textViewMunicipio;
    private TextView textViewUF;

    //EditText
    private EditText editTextCEP;
    private EditText editTextEndereco;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextComplemento;
    private EditText editTextMunicipio;
    private EditText editTextUF;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        implementarComponentes();

        intentCadastroCliente = getIntent();
        cadastroCliente = new Usuario();
        enderecoCliente = new Endereco();
        cadastroCliente = (Usuario) intentCadastroCliente.getSerializableExtra(CadastroGeneroActivity.CADASTROGENERO);
        System.out.println("Teste retorna valor da página anterior --->" +
                "\n " + cadastroCliente.getTipo() +
                "\n " + cadastroCliente.getEmail() +
                "\n " + cadastroCliente.getSenha() +
                "\n " + cadastroCliente.getDta_nascimento() +
                "\n " + cadastroCliente.getGenero());

        /*
        editTextEndereco.setEnabled(false);
        editTextBairro.setEnabled(false);
        editTextMunicipio.setEnabled(false);
        editTextUF.setEnabled(false);
        editTextEndereco.setBackground(getDrawable(R.drawable.edittext_cinza));
        editTextBairro.setBackground(getDrawable(R.drawable.edittext_cinza));
        editTextMunicipio.setBackground(getDrawable(R.drawable.edittext_cinza));
        editTextUF.setBackground(getDrawable(R.drawable.edittext_cinza));*/

        editTextCEP.addTextChangedListener(cepTextWatcher);


    }

    private TextWatcher cepTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cepInput = editTextCEP.getText().toString().trim();
            if (cepInput.length() < 8) {
                buttonAvancar.setEnabled(false);
                buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));
            } else {
                /*
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // limpa
                    editTextCEP.setText("");
                    editTextEndereco.setText("");
                    editTextNumero.setText("");
                    editTextComplemento.setText("");
                    editTextBairro.setText("");
                    editTextMunicipio.setText("");
                    editTextUF.setText("");

                    // cep
                    String cep = editTextCEP.getText().toString();

                    // verifica se o CEP é válido
                    Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
                    Matcher matcher = pattern.matcher(cep);

                    if (matcher.find()) {
                        new DownloadCEPTask().execute(cep);
                    } else {
                        //JOptionPane.showMessageDialog(null, "Favor informar um CEP válido!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        new AlertDialog.Builder(CadastroEnderecoActivity.this)
                                .setTitle("Aviso!")
                                .setMessage("Favor informar um CEP válido!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // nada
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(CadastroEnderecoActivity.this)
                            .setTitle("Sem Internet!")
                            .setMessage("Não tem nenhuma conexão de rede disponível!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // nada
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }*/

                buttonAvancar.setEnabled(true);
                buttonAvancar.setTextColor(getResources().getColor(R.color.rosa_100));

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

    public void onClickAvancarToCadastroCPF(View view) {
        String cepInput         = editTextCEP.getText().toString();
        String enderecoInput    = editTextEndereco.getText().toString();
        String numeroInput      = editTextNumero.getText().toString();
        String complementoInput = editTextComplemento.getText().toString();
        String bairroInput      = editTextBairro.getText().toString();
        String cidadeInput      = editTextMunicipio.getText().toString();
        String ufInput          = editTextUF.getText().toString();
        enderecoCliente.setCep(cepInput);
        enderecoCliente.setLogradouro(enderecoInput);
        enderecoCliente.setNumero(numeroInput);
        enderecoCliente.setComplemento(complementoInput);
        enderecoCliente.setBairro(bairroInput);
        enderecoCliente.setCidade(cidadeInput);
        enderecoCliente.setUf(ufInput);
        intentToCadastroCPF = new Intent(CadastroEnderecoActivity.this, CadastroCPFActivity.class);
        intentToCadastroCPF.putExtra(CADASTROENDERECO, cadastroCliente);
        intentToCadastroCPF.putExtra(ENDERECO, enderecoCliente);
        startActivity(intentToCadastroCPF);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
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
                "\n "+enderecoCliente.getUf());
    }

    private void implementarComponentes() {

        //ConstraintLayout
        constraintLayoutVoltar      = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarendereco_voltar);
        constraintLayoutEndereco    = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarendereco_endereco);
        constraintLayoutComplemento = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarendereco_complemento);
        constraintLayoutBairro      = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarendereco_bairro);
        constraintLayoutMunicipio   = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarendereco_municipio);


        //TextView
        textViewTitulo              = (TextView) findViewById(R.id.textView_cadastrarendereco_titulo);
        textViewCEP                 = (TextView) findViewById(R.id.textview_cadastrarendereco_cep);
        textViewCEPcomplemento      = (TextView) findViewById(R.id.textview_cadastrarendereco_cep_complemento);
        textViewEndereco            = (TextView) findViewById(R.id.textview_cadastrarendereco_endereco);
        textViewNumero              = (TextView) findViewById(R.id.textview_cadastrarendereco_numero);
        textViewComplemento         = (TextView) findViewById(R.id.textview_cadastrarendereco_complemento);
        textViewBairro              = (TextView) findViewById(R.id.textview_cadastrarendereco_bairro);
        textViewMunicipio           = (TextView) findViewById(R.id.textview_cadastrarendereco_municipio);
        textViewUF                  = (TextView) findViewById(R.id.textview_cadastrarendereco_uf);


        //EditText
        editTextCEP                 = (EditText) findViewById(R.id.edittext_cadastrarendereco_cep);
        editTextEndereco            = (EditText) findViewById(R.id.edittext_cadastrarendereco_endereco);
        editTextNumero              = (EditText) findViewById(R.id.edittext_cadastrarendereco_numero);
        editTextComplemento         = (EditText) findViewById(R.id.edittext_cadastrarendereco_complemento);
        editTextBairro              = (EditText) findViewById(R.id.edittext_cadastrarendereco_bairro);
        editTextMunicipio           = (EditText) findViewById(R.id.edittext_cadastrarendereco_municipio);
        editTextUF                  = (EditText) findViewById(R.id.edittext_cadastrarendereco_uf);


        //Button
        buttonAvancar               = (Button) findViewById(R.id.button_cadastrarendereco_avancar);

    }



}
