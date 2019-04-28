package br.com.app07_partiu.Activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import br.com.app07_partiu.R;

public class LoadingActivity extends AppCompatActivity {

    private ImageView imageViewLogo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        imageViewLogo = (ImageView) findViewById(R.id.imageView_logo_loading);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_loading);

    }

}
