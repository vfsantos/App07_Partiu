package br.com.app07_partiu.Activity.CadastroActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class CadastroGeneroActivity extends AppCompatActivity {

    public static final String CADASTROGENERO = "br.com.app07_partiu.Activity.CadastroActivity.cadastrogenero";

    //Context
    private Context context;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewGenero;


    //Button
    private Button buttonVoltar;
    private Button buttonAvancar;
    private Button buttonSelectMasculino;
    private Button buttonSelectFeminino;
    private Button buttonSelectNaoBinario;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToCadastroEndereco;
    private Intent intentCadastroCliente;


    //Objeto
    Usuario cadastroCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_genero);


        implementarComponentes();
        buttonAvancar.setEnabled(true);
        intentCadastroCliente = getIntent();
        cadastroCliente = new Usuario();
        cadastroCliente = (Usuario) intentCadastroCliente.getSerializableExtra(CadastroDataNascimentoActivity.CADASTRODATANASCIMENTO);
        System.out.println("Teste retorna valor da página anterior --->" +
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getEmail()+
                "\n "+cadastroCliente.getSenha()+
                "\n "+cadastroCliente.getDta_nascimento());

        cadastroCliente.setGenero('m');
    }

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
            cadastroCliente.setGenero('m');


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
            cadastroCliente.setGenero('f');

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
            cadastroCliente.setGenero('n');
        } else {

        }
    }

    public void onClickSelectMasculino(View view) {
        selectGenero(true, false, false);
        buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
        buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
    }

    public void onClickSelectFeminino(View view) {
        selectGenero(false, true, false);
        buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
        buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
    }

    public void onClickSelectNaoBinario(View view) {
      selectGenero(false, false, true);
        buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
        buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
    }

    public void onClickVoltarMainCliente(View view) {
        finish();
    }

    public void onClickAvancarToEndereco(View view) {

        try{
            intentToCadastroEndereco = new Intent(this, CadastroEnderecoActivity.class);
            intentToCadastroEndereco.putExtra(CADASTROGENERO, cadastroCliente);
            startActivity(intentToCadastroEndereco);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            System.out.println("Teste retorna valor desta página --->" +
                    "\n "+cadastroCliente.getTipo()+
                    "\n "+cadastroCliente.getEmail()+
                    "\n "+cadastroCliente.getSenha()+
                    "\n "+cadastroCliente.getDta_nascimento()+
                    "\n "+cadastroCliente.getGenero());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void implementarComponentes() {


        //ConstraintLayout
        constraintLayoutVoltar      = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrarnome_voltar);


        //TextView
        textViewTitulo              = (TextView) findViewById(R.id.textView_cadastrargenero_titulo);
        textViewGenero              = (TextView) findViewById(R.id.textview_cadastrargenero_genero);


        //Button
        buttonAvancar                 = (Button) findViewById(R.id.button_cadastrargenero_avancar);
        buttonSelectMasculino         = (Button) findViewById(R.id.button_cadastrargenero_selectmasculino);
        buttonSelectFeminino          = (Button) findViewById(R.id.button_cadastrargenero_selectfeminino);
        buttonSelectNaoBinario        = (Button) findViewById(R.id.button_cadastrargenero_selectnaobinario);
    }

}
