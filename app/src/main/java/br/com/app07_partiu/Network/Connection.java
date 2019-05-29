package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class Connection {
    public static final String URL = "http://192.168.15.100:8080/partiu";

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
        if (!connected) makeToast(context);
        //TODO else if conexcao com o webservice
        return connected;
    }

    public static void makeToast(Context context) {
        Log.d("TESTES", "Connection time out");
        Toast.makeText(context, "Rede inativa!\nReconecte-se e tente novamente.", Toast.LENGTH_SHORT).show();
    }

}
