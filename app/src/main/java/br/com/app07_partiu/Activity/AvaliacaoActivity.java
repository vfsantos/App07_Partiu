package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.BatchUpdateException;

import br.com.app07_partiu.Activity.CadastroActivity.CadastroNomeActivity;
import br.com.app07_partiu.Activity.CadastroActivity.CadastroSucesso;
import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.Activity.ItemComandaDetalheClienteActivity.ItemComandaDetalheClienteActivity;
import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.AvaliacaoNetwork;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.Network.UsuarioNetwork;
import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class AvaliacaoActivity extends AppCompatActivity {


    //TextView
    private TextView textViewTitulo;
    private TextView textViewAtendimento;
    private TextView textViewAtendimentoDescricao;
    private TextView textViewGarcom;
    private TextView textViewGarcomComentario;
    private TextView textViewEstabelecimento;
    private TextView textViewEstabelecimentoComentario;


    //Constranint
    private ConstraintLayout constraintLayoutHeader;
    private ConstraintLayout constraintLayoutAtendimento;
    private ConstraintLayout constraintLayoutCardGarcom;
    private ConstraintLayout constraintLayoutCardEstabelecimento;
    private ConstraintLayout constraintLayoutButtonFechar;


    //ImagemView
    private ImageView imageViewFechar;


    //Ratting
    private RatingBar ratingBarGarcom;
    private RatingBar ratingBarEstabelecimento;


    //EditText
    private EditText editTextGarcomComentario;
    private EditText editTextEstabelecimentoComentario;


    //Button
    private Button buttonAvaliar;


    //float
    private float avaliacaoGarcom;
    private float avaliacaoEstabelecimento;


    //String
    private String comentarioGarcom;
    private String comentarioEstabelecimento;


    //Context
    private Context context;


    //View
    private View viewSnackbar;


    //Intent
    private Intent intentToObrigadoPorAvaliar;
    private Intent intentFormaDePagamento;


    //Objetos
    private Usuario usuario;
    private Comanda comanda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);
        implementarComponentes();
        viewSnackbar = findViewById(R.id.activityAvaliacao);


        usuario = new Usuario();
        comanda = new Comanda();
        intentFormaDePagamento = getIntent();
        usuario = (Usuario) intentFormaDePagamento.getSerializableExtra(PagamentoConfirmadoActivity.USUARIO);
        comanda = (Comanda) intentFormaDePagamento.getSerializableExtra(PagamentoConfirmadoActivity.COMANDA);
        //teste objeto usuario e comanda
        System.out.println("idCliente: "+usuario.getId());
        System.out.println("idComanda: "+comanda.getId());

    }


    public double getAvalicaoGarcom() {
        ratingBarGarcom.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                avaliacaoGarcom = rating;
            }
        });
        return avaliacaoGarcom;
    }


    public String getComentarioGarcom(){
        comentarioGarcom = editTextGarcomComentario.getText().toString();
        return comentarioGarcom;
    }


    public double getAvalicaoEstabelecimento() {
        ratingBarEstabelecimento.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                avaliacaoEstabelecimento = rating;
            }
        });
        return avaliacaoEstabelecimento;
    }


    public String getComentarioEstabelecimento(){
        comentarioEstabelecimento = editTextEstabelecimentoComentario.getText().toString();
        return comentarioEstabelecimento;
    }


    public void enviarFeedback(final int idCliente, final int idComanda, final int avEstabelecimento, final int avFuncionario,
                               final String descEstabelecimento, final String descFuncionario) {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AvaliacaoNetwork.enviarFeedback(Connection.URL, idCliente, idComanda, avFuncionario, avEstabelecimento, descFuncionario, descEstabelecimento);

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




    public void onClickAvaliar(View view) {
        //int
        int idCliente;
        int idComanda;
        int avGarcom;
        int avEstabelecimento;


        //String
        String descGarcom;
        String descEstabelecimento;


        idCliente           = usuario.getId();
        idComanda           = comanda.getId();

        avGarcom            = (int) getAvalicaoGarcom();
        avEstabelecimento   = (int) getAvalicaoEstabelecimento();
        descGarcom          = (String) getComentarioGarcom();
        descEstabelecimento = (String) getComentarioEstabelecimento();


        //teste feedback
        System.out.println("idCliente: "+idCliente);
        System.out.println("idComanda: "+idComanda);
        System.out.println("avGarcom: "+avGarcom);
        System.out.println("avEstabelecimento: "+avEstabelecimento);
        System.out.println("descGarcom: "+descGarcom);
        System.out.println("descGarcom: "+descEstabelecimento);


        enviarFeedback(idCliente, idComanda, avGarcom, avEstabelecimento, descGarcom, descEstabelecimento);
        setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
    }


    public void onClickFechar(View view){
        setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
        finish();
    }




    private void implementarComponentes() {

        //TextView
        textViewTitulo                      = (TextView) findViewById(R.id.textView_avaliacao_tituloPagina);
        textViewAtendimento                 = (TextView) findViewById(R.id.textView_avaliacao_atendimentotitulo);
        textViewAtendimentoDescricao        = (TextView) findViewById(R.id.textView_avaliacao_atendimentodescricao);
        textViewGarcom                      = (TextView) findViewById(R.id.textview_avaliacao_garcom);
        textViewGarcomComentario            = (TextView) findViewById(R.id.textview_avaliacao_garcomcomentario);
        textViewEstabelecimento             = (TextView) findViewById(R.id.textview_avaliacao_estabelecimento);
        textViewEstabelecimentoComentario   = (TextView) findViewById(R.id.textview_avaliacao_estabelecimentocomentario);


        //Constraint
        constraintLayoutHeader              = (ConstraintLayout) findViewById(R.id.constraintLayouta_avaliacao_header);
        constraintLayoutAtendimento         = (ConstraintLayout) findViewById(R.id.constraintLayouta_avaliacao_atendimento);
        constraintLayoutCardGarcom          = (ConstraintLayout) findViewById(R.id.constraintLayout_avaliacao_card_garcom);
        constraintLayoutCardEstabelecimento = (ConstraintLayout) findViewById(R.id.constraintLayout_avaliacao_card_estabelecimento);
        constraintLayoutButtonFechar        = (ConstraintLayout) findViewById(R.id.constraintLayout_avaliacao_fechar);


        //ImageView
        imageViewFechar                     = (ImageView) findViewById(R.id.imageview_codigoComanda_fechar);


        //RatingBar
        ratingBarGarcom                     = (RatingBar) findViewById(R.id.ratingBar_avaliacao_garcom);
        ratingBarEstabelecimento            = (RatingBar) findViewById(R.id.ratingBar_avaliacao_estabelecimento);


        //EditText
        editTextGarcomComentario            = (EditText) findViewById(R.id.editText_avaliacao_garcom);
        editTextEstabelecimentoComentario   = (EditText) findViewById(R.id.editText_avaliacao_estabelecimento);


        //Button
        buttonAvaliar                       = (Button) findViewById(R.id.button_codigoComandaEntrar);
    }



}
