package br.com.app07_partiu.Activity.CadastroActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class CadastroDataNascimentoActivity extends AppCompatActivity {

    public static final String CADASTRODATANASCIMENTO = "br.com.app07_partiu.Activity.CadastroActivity.cadastrodatanascimento";

    //ImageView
    private ImageView imageViewVoltar;


    //ConstraintLayout
    private ConstraintLayout constraintLayoutVoltar;


    //TextView
    private TextView textViewTitulo;
    private TextView textViewDataDeNascimento;


    //EditText
    private EditText editTextDatePikerDataDeNascimento;


    //EditText
    private DatePickerDialog.OnDateSetListener datePikerDataDeNascimento;


    //Button
    private Button buttonVoltar;
    private Button buttonAvancar;


    //Intent
    private Intent intentToMainCliente;
    private Intent intentToCadastroGenero;
    private Intent intentCadastroCliente;


    //Objeto
    public Usuario cadastroCliente;


    //Context
    private Context context;


    //Snackbar
    private View viewSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_data_nascimento);
        implementarComponentes();

        intentCadastroCliente = getIntent();
        cadastroCliente       = new Usuario();
        cadastroCliente       = (Usuario) intentCadastroCliente.getSerializableExtra(CadastroSenhaActivity.CADASTROSENHA);
        System.out.println("Teste retorna valor da página anterior --->" +
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getEmail()+
                "\n "+cadastroCliente.getSenha());


        //botão avançar começa desabilitado
        buttonAvancar.setEnabled(false);
        buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));

        //pega dia mes e ano
        editTextDatePikerDataDeNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(                        CadastroDataNascimentoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        datePikerDataDeNascimento, ano, mes, dia);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        datePikerDataDeNascimento = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;


                System.out.println("pega a variável year: " + year);
                System.out.println("pega a variável month: " + month);
                System.out.println("pega a variável dayOfMonth: " + dayOfMonth);


                try {
                    Calendar cal = Calendar.getInstance();
                    System.out.println("Teste captura do ano: variável ano: " + year);
                    System.out.println("Texte captuira de ano: variável cal: " + cal.get(Calendar.YEAR));



                        editTextDatePikerDataDeNascimento.setText(dayOfMonth + "/" + month + "/" + year);
                        cadastroCliente.setDta_nascimento(dayOfMonth + "/" + month + "/" + year);
                        buttonAvancar.setEnabled(true);
                        buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
                        buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));


/*
                    //verfica se o ano é maior que o de hoje
                    if (year > cal.get(Calendar.YEAR)){
                        if (month<10){
                            System.out.println("teste do primeri if: " +year);

                            editTextDatePikerDataDeNascimento.setText(dayOfMonth+"/0"+month+"/"+year);
                            cadastroCliente.setDta_nascimento(dayOfMonth+"/0"+month+"/"+year);
                            buttonAvancar.setEnabled(false);
                            buttonAvancar.setBackground(getDrawable(R.drawable.button_branco_solid));
                            buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));
                        }else {
                            editTextDatePikerDataDeNascimento.setText(dayOfMonth + "/" + month + "/" + year);
                            cadastroCliente.setDta_nascimento(dayOfMonth + "/" + month + "/" + year);
                            buttonAvancar.setEnabled(true);
                            buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
                            buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
                        }
                    } else {

                        //verifica se o mes a maior que o de hoje
                        if (month > cal.get(Calendar.MONTH)){
                            if (month<10){
                                editTextDatePikerDataDeNascimento.setText(dayOfMonth+"/0"+month+"/"+year);
                                cadastroCliente.setDta_nascimento(dayOfMonth+"/0"+month+"/"+year);
                                buttonAvancar.setEnabled(false);
                                buttonAvancar.setBackground(getDrawable(R.drawable.button_branco_solid));
                                buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));
                            }else {
                                editTextDatePikerDataDeNascimento.setText(dayOfMonth + "/" + month + "/" + year);
                                cadastroCliente.setDta_nascimento(dayOfMonth + "/" + month + "/" + year);
                                buttonAvancar.setEnabled(true);
                                buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
                                buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
                            }
                        } else {

                            //verifica se o dia é maior que o de hoje


                            if (dayOfMonth > cal.get(Calendar.DAY_OF_MONTH)) {
                                if (month<10){
                                    editTextDatePikerDataDeNascimento.setText(dayOfMonth+"/0"+month+"/"+year);
                                    cadastroCliente.setDta_nascimento(dayOfMonth+"/0"+month+"/"+year);
                                    buttonAvancar.setEnabled(false);
                                    buttonAvancar.setBackground(getDrawable(R.drawable.button_branco_solid));
                                    buttonAvancar.setTextColor(getResources().getColor(R.color.cinza_100));
                                }else {
                                    editTextDatePikerDataDeNascimento.setText(dayOfMonth + "/" + month + "/" + year);
                                    cadastroCliente.setDta_nascimento(dayOfMonth + "/" + month + "/" + year);
                                    buttonAvancar.setEnabled(true);
                                    buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
                                    buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
                                }
                            } else {

                                if (month<10){
                                    editTextDatePikerDataDeNascimento.setText(dayOfMonth+"/0"+month+"/"+year);
                                    cadastroCliente.setDta_nascimento(dayOfMonth+"/0"+month+"/"+year);
                                }else {
                                    editTextDatePikerDataDeNascimento.setText(dayOfMonth + "/" + month + "/" + year);
                                    cadastroCliente.setDta_nascimento(dayOfMonth + "/" + month + "/" + year);
                                    buttonAvancar.setEnabled(true);
                                    buttonAvancar.setBackground(getDrawable(R.drawable.button_degrade_rosa_amarelo));
                                    buttonAvancar.setTextColor(getResources().getColor(R.color.branco_100));
                                }

                            }
                        }
                        }
                        */



                } catch (Exception e) {
                    Log.e("TESTES", "CadatroDataNascimento: Exception data de nacimento inválida'");
                    //Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_backend);
                    e.printStackTrace();
                }



            }
        };

    }



    public void onClickVoltarMainCliente(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    public void onClickAvancarToCadastroSenha (View view) {
        intentToCadastroGenero = new Intent(CadastroDataNascimentoActivity.this, CadastroGeneroActivity.class);
        intentToCadastroGenero.putExtra(CADASTRODATANASCIMENTO, cadastroCliente);
        startActivity(intentToCadastroGenero);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        System.out.println("Teste retorna valor da desta página --->" +
                "\n "+cadastroCliente.getTipo()+
                "\n "+cadastroCliente.getEmail()+
                "\n "+cadastroCliente.getSenha()+
                "\n "+cadastroCliente.getDta_nascimento());
    }



    private void implementarComponentes() {
        //ConstraintLayout
        constraintLayoutVoltar            = (ConstraintLayout) findViewById(R.id.constraintLayout_cadastrardatanascimento_voltar);


        //ImageView
        imageViewVoltar                   = (ImageView) findViewById(R.id.imageview_cadastrardatanascimento_voltar);


        //TextView
        textViewTitulo                    = (TextView) findViewById(R.id.textView_cadastrardatanascimento_titulo);
        textViewDataDeNascimento          = (TextView) findViewById(R.id.textview_cadastrardatanascimento_datanascimento);


        //EditText
        editTextDatePikerDataDeNascimento = (EditText) findViewById(R.id.datePicker_cadastrardatanascimento_datadenascimento);


        //Button
        buttonAvancar                     = (Button) findViewById(R.id.button_cadastrardatanascimento_avancar);
    }

}
