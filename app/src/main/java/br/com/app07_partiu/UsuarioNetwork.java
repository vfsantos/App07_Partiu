package br.com.app07_partiu;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;

public class UsuarioNetwork {



    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
