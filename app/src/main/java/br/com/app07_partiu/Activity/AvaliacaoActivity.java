package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.app07_partiu.Activity.ComandaMesaCliente.ComandaMesaClienteActivity;
import br.com.app07_partiu.R;

public class AvaliacaoActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar toolbar;

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
        implementarComponentes();

        //Toolbar
        setUpToolbar();
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                break;
            default:break;
        }
        return true;
    }


    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
            getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
            getSupportActionBar().setTitle(R.string.textview_avaliacao_titulopagina);     //Titulo para ser exibido na sua Action Bar em frente à seta
        }
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
//                getAvalicao();
//                getComentario();
        setResult(ComandaMesaClienteActivity.RESULT_PEDIDOSFINALIZADOS);
        finish();
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
        //Toolbar
        toolbar            = (Toolbar) findViewById(R.id.toolbar);

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
