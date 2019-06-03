package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.app07_partiu.R;

public class AvaliacaoActivity extends AppCompatActivity {

    //TextView
    private TextView textViewTitulo;
    private TextView textViewDescricao1;
    private TextView textViewDescricao2;
    private TextView textViewComentario;


    //Button
    private Button buttonAvaliar;
    private Button buttonFechar;


    //EditText
    private EditText editTextComentario;


    //RatingBar
    private RatingBar ratingBar;

    private float avaliacao;
    private String comentario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        implementarComponentes();
    }

    public double getAvalicao() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                avaliacao = rating;
            }
        });
        return avaliacao;
    }

    public String getComentario(){
        comentario = editTextComentario.getText().toString();
        return comentario;
    }

    public void onClickAvaliar(View view) {
        buttonAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvalicao();
                getComentario();
            }
        });
    }

    public void onClickfechar(View view){
        buttonFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void implementarComponentes() {
        //TextView
        textViewTitulo     = (TextView) findViewById(R.id.textView_avaliacao_titulo);
        textViewDescricao1 = (TextView) findViewById(R.id.textView_avaliacao_descritivo1);
        textViewDescricao2 = (TextView) findViewById(R.id.textView_avaliacao_descritivo2);
        textViewComentario = (TextView) findViewById(R.id.textView_avaliacao_comentario);


        //Button
        buttonAvaliar      = (Button) findViewById(R.id.button_avaliacao_avaliar);
        buttonFechar       = (Button) findViewById(R.id.button_avaliacao_fechar);


        //EditText
        editTextComentario = (EditText) findViewById(R.id.editText_avaliacao_comentario);


        //Rating
        ratingBar          = (RatingBar) findViewById(R.id.ratingBar);

    }



}
