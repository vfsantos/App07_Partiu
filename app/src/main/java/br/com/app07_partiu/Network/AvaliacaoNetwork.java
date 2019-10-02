package br.com.app07_partiu.Network;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AvaliacaoNetwork {


    public static void enviarFeedback(String url, int idCliente, int idComanda, int avEstabelecimento, int avFuncionario,
                                      String descEstabelecimento, String descFuncionario) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/createAvaliacao?idCliente=" + idCliente + "&idComanda=" + idComanda + "&avFuncionario=" + avFuncionario + "&avEstabelecimento=" +
                avEstabelecimento + "&descFuncionario=" + descFuncionario + "&descEstabelecimento=" + descEstabelecimento;
        Log.d("TESTI",url);
        Request request = new Request.Builder()
                .url(url)
                .build();


        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTI",resultado);
    }
}
