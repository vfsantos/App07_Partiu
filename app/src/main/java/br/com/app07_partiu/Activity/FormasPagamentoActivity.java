package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.app07_partiu.R;

public class FormasPagamentoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formas_pagamento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch(view.getId()) {
            case R.id.radioButton_formasPagamento_credito:
                if (checked)
                    break;
            case R.id.radioButton_formasPagamento_debito:
                if (checked)
                    break;
            case R.id.radioButton_formasPagamento_dinheiro:
                if (checked)
                    break;
            case R.id.radioButton_formasPagamento_ticket:
                if (checked)
                    break;
        }
    }


}
