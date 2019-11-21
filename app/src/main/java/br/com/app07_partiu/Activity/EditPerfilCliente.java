package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.util.ByteBufferUtil;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;

import br.com.app07_partiu.Activity.CadastroActivity.CadastroEmailActivity;
import br.com.app07_partiu.Activity.HistoricoComandaActivity.HistoricoComandasActivity;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARCOMANDA;
import static br.com.app07_partiu.Activity.ExplorarClienteActivity.ExplorarClienteActivity.EXPLORARUSUARIO;

public class EditPerfilCliente extends AppCompatActivity {

    //Constraint
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutButtonVoltar;
    private ConstraintLayout constraintLayoutContentMeusDados;
    private ConstraintLayout constraintLayoutContentEndereco;
    private ConstraintLayout constraintLayoutNome;
    private ConstraintLayout constraintLayoutEmail;
    private ConstraintLayout constraintLayoutSenha;
    private ConstraintLayout constraintLayoutGenero;
    private ConstraintLayout constraintLayoutDataNascimento;
    private ConstraintLayout constraintLayoutCpf;
    private ConstraintLayout constraintLayoutTelefone;
    private ConstraintLayout constraintLayoutLogradouro;
    private ConstraintLayout constraintLayoutNumero;
    private ConstraintLayout constraintLayoutComplemento;
    private ConstraintLayout constraintLayoutCEP;
    private ConstraintLayout constraintLayoutBairro;
    private ConstraintLayout constraintLayoutCidade;
    private ConstraintLayout constraintLayoutEstado;
    private ConstraintLayout constraintLayoutBUttonSalvar;


    //ImagemView
    private ImageView imageViewVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewContentMeusDados;
    private TextView textViewContentEndereco;
    private TextView textViewNome;
    private TextView textViewSenha;
    private TextView textViewEmail;
    private TextView textViewDataNascimento;
    private TextView textViewGenero;
    private TextView textViewCPF;
    private TextView textViewTelefone;
    private TextView textViewCEP;
    private TextView textViewLogradouro;
    private TextView textViewNumero;
    private TextView textViewComplemento;
    private TextView textViewBairro;
    private TextView textViewCidade;
    private TextView textViewEstado;


    //EditText
    private EditText editTextNome;
    private EditText editTextSenha;
    private EditText editTextEmail;
    private EditText editTextDataNascimento;
    private EditText editTextCPF;
    private EditText editTextTelefone;
    private EditText editTextCEP;
    private EditText editTextLogradrouro;
    private EditText editTextNumero;
    private EditText editTextComplemento;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;


    //Button
    private Button buttonSalvar;
    private Button buttonSelectMasculino;
    private Button buttonSelectFeminino;
    private Button buttonSelectNaoBinario;


    //Context
    private Context context;


    //Intent
    private Intent intentPerfilCliente;


    //Objetos
    private Usuario usuario;
    private Endereco endereco;


    //Snackbar
    private View viewSnackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil_cliente);
        implementarComponentes();
        context = this;

        viewSnackbar = findViewById(R.id.edit_perfil_activity);

        intentPerfilCliente = getIntent();
        usuario = new Usuario();
        endereco = new Endereco();
        usuario.setEndereco(endereco);
        usuario = (Usuario) intentPerfilCliente.getSerializableExtra(PerfilClienteActivity.USUARIO);
        System.out.println("DADOS DO USUÁRIO ---> "+
                "\n "+usuario.getTipo()+
                "\n "+usuario.getNome()+
                "\n "+usuario.getEmail()+
                "\n "+usuario.getSenha()+
                "\n "+usuario.getCpf()+
                "\n "+usuario.getTelefone()+
                "\n "+usuario.getGenero()+
                "\n "+usuario.getDta_nascimento());

        System.out.println("DADOS DO ENDEREÇO ---> "+
                "\n "+usuario.getEndereco().getCep()+
                "\n "+usuario.getEndereco().getCep()+
                "\n "+usuario.getEndereco().getLogradouro()+
                "\n "+usuario.getEndereco().getNumero()+
                "\n "+usuario.getEndereco().getComplemento()+
                "\n "+usuario.getEndereco().getBairro()+
                "\n "+usuario.getEndereco().getCidade()+
                "\n "+usuario.getEndereco().getUf());

        retornaDadosNaTelas(usuario);
    }


    public void retornaDadosNaTelas(Usuario usuario){
        //dados do usuário
        editTextNome.setText(usuario.getNome());
        editTextEmail.setText(usuario.getEmail());
        editTextSenha.setText(usuario.getSenha());
        if(usuario.getGenero()=='F'){
            buttonSelectMasculino.setPressed(false);
            buttonSelectMasculino.setEnabled(true);
            buttonSelectFeminino.setPressed(true);
            buttonSelectFeminino.setEnabled(true);
            buttonSelectNaoBinario.setPressed(false);
            buttonSelectNaoBinario.setEnabled(true);
            buttonSelectMasculino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectFeminino.setBackground(getDrawable(R.drawable.button_select_branco_solid));
            buttonSelectNaoBinario.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectMasculino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectFeminino.setTextColor(getResources().getColor(R.color.rosa_100));
            buttonSelectNaoBinario.setTextColor(getResources().getColor(R.color.branco_100));
        } else if (usuario.getGenero()=='M'){
            buttonSelectMasculino.setPressed(true);
            buttonSelectMasculino.setEnabled(true);
            buttonSelectFeminino.setPressed(false);
            buttonSelectFeminino.setEnabled(true);
            buttonSelectNaoBinario.setPressed(false);
            buttonSelectNaoBinario.setEnabled(true);
            buttonSelectMasculino.setBackground(getDrawable(R.drawable.button_select_branco_solid));
            buttonSelectFeminino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectNaoBinario.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectMasculino.setTextColor(getResources().getColor(R.color.rosa_100));
            buttonSelectFeminino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectNaoBinario.setTextColor(getResources().getColor(R.color.branco_100));
        } else {
            buttonSelectMasculino.setPressed(false);
            buttonSelectMasculino.setEnabled(true);
            buttonSelectFeminino.setPressed(false);
            buttonSelectFeminino.setEnabled(true);
            buttonSelectNaoBinario.setPressed(true);
            buttonSelectNaoBinario.setEnabled(true);
            buttonSelectMasculino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectFeminino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectNaoBinario.setBackground(getDrawable(R.drawable.button_select_branco_solid));
            buttonSelectMasculino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectFeminino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectNaoBinario.setTextColor(getResources().getColor(R.color.rosa_100));
        }
        editTextDataNascimento.setText(usuario.getDta_nascimento());
        editTextCPF.setText(usuario.getCpf());
        Log.d("TESTE", usuario.getDdd()+""+usuario.getTelefone());
        editTextTelefone.setText(usuario.getDdd()+""+usuario.getTelefone());
//editTextTelefone.setText("0000");
        //dados do endereço
        try{
            editTextLogradrouro.setText(usuario.getEndereco().getLogradouro());
            editTextNumero.setText(usuario.getEndereco().getNumero());
            editTextCEP.setText(usuario.getEndereco().getCep());
            editTextComplemento.setText(usuario.getEndereco().getComplemento());
            editTextBairro.setText(usuario.getEndereco().getBairro());
            editTextCidade.setText(usuario.getEndereco().getCidade());
            editTextEstado.setText(usuario.getEndereco().getUf());
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void onClickVoltarEditarPerfil(View view){
        sairSemSalvar(context);
    }



    //genero
    public void selectGenero(boolean generoMasc, boolean generoFemi, boolean generoNaoB) {
        if(generoMasc == true && generoFemi == false && generoNaoB == false){
            buttonSelectMasculino.setPressed(true);
            buttonSelectMasculino.setEnabled(true);
            buttonSelectFeminino.setPressed(false);
            buttonSelectFeminino.setEnabled(true);
            buttonSelectNaoBinario.setPressed(false);
            buttonSelectNaoBinario.setEnabled(true);
            buttonSelectMasculino.setBackground(getDrawable(R.drawable.button_select_branco_solid));
            buttonSelectFeminino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectNaoBinario.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectMasculino.setTextColor(getResources().getColor(R.color.rosa_100));
            buttonSelectFeminino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectNaoBinario.setTextColor(getResources().getColor(R.color.branco_100));

            usuario.setGenero('m');


        } else if(generoMasc == false && generoFemi == true && generoNaoB == false) {
            buttonSelectMasculino.setPressed(false);
            buttonSelectMasculino.setEnabled(true);
            buttonSelectFeminino.setPressed(true);
            buttonSelectFeminino.setEnabled(true);
            buttonSelectNaoBinario.setPressed(false);
            buttonSelectNaoBinario.setEnabled(true);
            buttonSelectMasculino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectFeminino.setBackground(getDrawable(R.drawable.button_select_branco_solid));
            buttonSelectNaoBinario.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectMasculino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectFeminino.setTextColor(getResources().getColor(R.color.rosa_100));
            buttonSelectNaoBinario.setTextColor(getResources().getColor(R.color.branco_100));
            usuario.setGenero('f');

        } else if(generoMasc == false && generoFemi == false && generoNaoB == true){
            buttonSelectMasculino.setPressed(false);
            buttonSelectMasculino.setEnabled(true);
            buttonSelectFeminino.setPressed(false);
            buttonSelectFeminino.setEnabled(true);
            buttonSelectNaoBinario.setPressed(true);
            buttonSelectNaoBinario.setEnabled(true);
            buttonSelectMasculino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectFeminino.setBackground(getDrawable(R.drawable.button_select_branco_outline));
            buttonSelectNaoBinario.setBackground(getDrawable(R.drawable.button_select_branco_solid));
            buttonSelectMasculino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectFeminino.setTextColor(getResources().getColor(R.color.branco_100));
            buttonSelectNaoBinario.setTextColor(getResources().getColor(R.color.rosa_100));
            usuario.setGenero('n');
        } else {

        }
    }

    public void onClickSelectMasculinoEditarPerfil(View view) {
        selectGenero(true, false, false);
    }

    public void onClickSelectFemininoEditarPerfil(View view) {
        selectGenero(false, true, false);
    }

    public void onClickSelectNaoBinarioEditarPerfil(View view) {
        selectGenero(false, false, true);
    }


    public void sairSemSalvar(final Context context){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Sair");
        builder.setMessage("Todas as alteração serão perdidas. Deseja realmente saiar?");

        builder.setCancelable(true);

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onClickUpdateDados (View view) {

        String id = String.valueOf(usuario.getId());
        String idEndereco = String.valueOf(usuario.getEndereco().getId());
        String tipo = usuario.getTipo();
        String cpf = editTextCPF.getText().toString();
        String nome = editTextNome.getText().toString();
        String dta_nascimento = editTextDataNascimento.getText().toString();
        String email = editTextEmail.getText().toString();


        String telefoneCompleto = editTextTelefone.getText().toString();
        String ddd         = telefoneCompleto.substring(0,2);
        String telefone    = telefoneCompleto.substring(2,11);

//        String ddd         = "";
//        String telefone    = "";



        String genero = String.valueOf(usuario.getGenero());
        String senha = editTextSenha.getText().toString();
        String logradouro = editTextLogradrouro.getText().toString();
        String numero = editTextNumero.getText().toString();
        String complemento = editTextComplemento.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String estado = editTextEstado.getText().toString();
        String cep = editTextCEP.getText().toString();
        String cnpj = "";
        String status = usuario.getStatusPedido();
        updateDados(id, idEndereco, tipo, cpf, nome, dta_nascimento, email, ddd, telefone, genero, senha, logradouro, numero,
        complemento, bairro, cidade, estado, cep, cnpj,status);

    }


    public void updateDados(final String id,final String idEndereco,final String tipo,final String cpf,final String nome,final String dta_nascimento,final String email,final String ddd, final
                            String telefone,final String genero,final String senha,final String logradouro,final String numero,final String complemento, final
                            String bairro, final String cidade,final String uf,final String cep,final String cnpj,final String status) {

        if (Connection.isConnected(this, viewSnackbar)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                       UsuarioNetwork.updateUsuario(Connection.URL, id, idEndereco, tipo, cpf, nome, dta_nascimento, email, ddd, telefone,
                       genero, senha, logradouro, numero, complemento, bairro, cidade, uf, cep, cnpj, status);

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              finish();
                                          }
                                      }
                        );


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TESTES", "ExplorarCliente: IOException visualizarHistoricoComanda");
                        Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    private void implementarComponentes() {

        //ConstraintLayout button
        constraintLayoutButtonVoltar    = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_voltar);
        //constraintLayoutBUttonSalvar    = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_salvar);


        //ConstraintLayout contentmeusdados
        constraintLayoutContentMeusDados= (ConstraintLayout) findViewById(R.id.constraintLayout_editarPerfil_contentseusdados);
        constraintLayoutNome            = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_nome);
        constraintLayoutEmail           = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_email);
        constraintLayoutSenha           = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_senha);
        constraintLayoutGenero          = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_genero);
        constraintLayoutDataNascimento  = (ConstraintLayout) findViewById(R.id.constraintLayout_editarPerfil_datanascimento);
        constraintLayoutCpf             = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_cpf);
        constraintLayoutTelefone        = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_telefone);


        //ConstraintLayout contentendereco
        constraintLayoutContentEndereco = (ConstraintLayout) findViewById(R.id.constraintLayout_editarPerfil_contentendereco);
        constraintLayoutLogradouro      = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_logradouro);
        constraintLayoutNumero          = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_numero);
        constraintLayoutComplemento     = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_complemento);
        constraintLayoutCEP             = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_cep);
        constraintLayoutBairro          = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_bairro);
        constraintLayoutCidade          = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_cidade);
        constraintLayoutEstado          = (ConstraintLayout) findViewById(R.id.constraintLayout_editarperfil_estado);


        //TextView contentmeusdados
        textViewTitulo                  = (TextView) findViewById(R.id.textView_editarPerfil_titulo);
        textViewContentMeusDados        = (TextView) findViewById(R.id.textView_editarPerfil_contentmeusdados);
        textViewNome                    = (TextView) findViewById(R.id.textView_editarPerfil_nome);
        textViewSenha                   = (TextView) findViewById(R.id.textView_editarPerfil_senha);
        textViewEmail                   = (TextView) findViewById(R.id.textView_editarPerfil_email);
        textViewCPF                     = (TextView) findViewById(R.id.textView_editarPerfil_cpf);
        textViewTelefone                = (TextView) findViewById(R.id.textView_editarPerfil_telefone);
        textViewGenero                  = (TextView) findViewById(R.id.textView_editarPerfil_genero);
        textViewDataNascimento          = (TextView) findViewById(R.id.textview_editarperfil_datanascimento);


        //TextView contentendereco
        textViewContentEndereco         = (TextView) findViewById(R.id.textView_editarPerfil_contentendereco);
        textViewCEP                     = (TextView) findViewById(R.id.textView_editarPerfil_cep);
        textViewLogradouro              = (TextView) findViewById(R.id.textView_editarPerfil_logradouro);
        textViewNumero                  = (TextView) findViewById(R.id.textView_editarPerfil_numero);
        textViewComplemento             = (TextView) findViewById(R.id.textView_editarPerfil_complemento);
        textViewBairro                  = (TextView) findViewById(R.id.textview_editarperfil_bairro);
        textViewCidade                  = (TextView) findViewById(R.id.textView_editarPerfil_cidade);
        textViewEstado                  = (TextView) findViewById(R.id.textView_editarPerfil_telefone_estado);


        //EditText contentmeusdados
        editTextNome                    = (EditText) findViewById(R.id.editText_editPerfil_nome);
        editTextSenha                   = (EditText) findViewById(R.id.editText_editPerfil_senha);
        editTextEmail                   = (EditText) findViewById(R.id.editText_editPerfil_email);
        editTextTelefone                = (EditText) findViewById(R.id.editText_editPerfil_telefone);
        editTextCPF                     = (EditText) findViewById(R.id.editText_editPerfil_cpf);
        editTextDataNascimento          = (EditText) findViewById(R.id.edittext_editarperfil_datadenascimento);


        //EditText contentendereco
        editTextCEP                     = (EditText) findViewById(R.id.editText_editPerfil_cep);
        editTextLogradrouro             = (EditText) findViewById(R.id.editText_editPerfil_logradrouro);
        editTextNumero                  = (EditText) findViewById(R.id.editText_editPerfil_numero);
        editTextComplemento             = (EditText) findViewById(R.id.editText_editPerfil_complemento);
        editTextBairro                  = (EditText) findViewById(R.id.edittext_editarperfil_bairro);
        editTextCidade                  = (EditText) findViewById(R.id.editText_editPerfil_cidade);
        editTextEstado                  = (EditText) findViewById(R.id.editText_editPerfil_estado);


        //Button
       // buttonSalvar                    = (Button) findViewById(R.id.button_editarPerfil_salvar);
        buttonSelectMasculino             = (Button) findViewById(R.id.button_editarPerfil_masculino);
        buttonSelectFeminino              = (Button) findViewById(R.id.button_editarPerfil_feminino);
        buttonSelectNaoBinario            = (Button) findViewById(R.id.button_editarperfil_naobinario);


        //ImageView
        imageViewVoltar                 = (ImageView) findViewById(R.id.imageview_editarperfil_voltar);
    }

}
