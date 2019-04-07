package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Comanda;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GarcomNetwork {



    public static Comanda[] buscarComandas(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Comanda> comandas = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for(int i = 0; i < vetor.length(); i++){
                JSONObject item = (JSONObject) vetor.get(i);
                Comanda comanda = new Comanda();

                comanda.setId(item.getInt("id"));
                comanda.setCodigoComanda(item.getString("codigocomanda"));
                comanda.setMesa(item.getInt("mesa"));
                comanda.setStatus(item.getString("status"));
                //comanda.setDataEntrada(item.getString(""));
                //comanda.setDataSaida();
                comanda.setValorTotalComanda(item.getDouble("valortotalcomanda"));

                comandas.add(comanda);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return comandas.toArray(new Comanda[0]);
    }

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
