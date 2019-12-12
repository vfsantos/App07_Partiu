package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.app07_partiu.R;
import br.com.app07_partiu.Util.Util;

public class Connection {

    //public static final String URL = "http://172.20.10.2:8080/partiu";
//    public static final String URL = "http://192.168.50.102:8080/partiu";
    public static final String URL = "http://10.0.2.2:8080/partiu";

//    public static final String URL = "http://192.168.43.121:8080/partiu";

    public static boolean isConnected(Context context, View viewSnackbar) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
        if (!connected) Util.showSnackbar(viewSnackbar, R.string.snackbar_erro_connection);
        return connected;
    }

}