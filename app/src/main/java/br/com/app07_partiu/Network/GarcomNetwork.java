package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Comanda;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GarcomNetwork {

/*
        [
{
        "id": 0,
        "codigo": "OSA03",
        "status": "A",
        "mesa": 3,
        "dtaEntrada": "15/04/2019"
    },
    {
        "id": 0,
        "codigo": "OSA02",
        "status": "A",
        "mesa": 2,
        "dtaEntrada": "15/04/2019"
    }
        ]*/

    public static Comanda[] buscarComandas(String url, int idGarcom, char status) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Comanda> comandas = new ArrayList<>();
        url += "/getComandasByStatusAndId?idGarcom=" + idGarcom + "&status=" + status;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();


        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Comanda comanda = new Comanda();

                //pegar os itens do json e atribui a um objeto comanda
                comanda.setId(item.getInt("id"));
                comanda.setCodigoComanda(item.getString("codigo"));
                comanda.setStatus(item.getString("status"));
                comanda.setMesa(item.getInt("mesa"));
                comanda.setDataEntrada(item.getString("dtaEntrada"));
                //comanda.setDataSaida(item.getString("dtaSaida"));

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                Log.d("TESTES", comanda.toString());
                comandas.add(comanda);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return comandas.toArray(new Comanda[0]);
    }

    public static Comanda createComanda(String url, int idGarcom, int mesa) throws IOException, JSONException {

        /*{
            "id": 9,
                "codigo": "OSA12",
                "status": "A",
                "mesa": 12,
                "dtaEntrada": "19/04/2019"
        }*/

        //converte o número da mesa de int para sprint para poder concatenar na url
        String numeroMesa = String.valueOf(mesa);
        String urlGerarSenha = url + "/createComanda?idGarcom=" + idGarcom + "&mesa=" + mesa;
        OkHttpClient client = new OkHttpClient();
        Comanda comanda = new Comanda();

        Request request = new Request.Builder()
                .url(urlGerarSenha)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);
        JSONObject object = new JSONObject(resultado);
        try {
            comanda.setId(object.getInt("id"));
            comanda.setCodigoComanda(object.getString("codigo"));
            comanda.setStatus(object.getString("status"));
            comanda.setMesa(object.getInt("mesa"));
            comanda.setDataEntrada(object.getString("dataEntrada"));
            comanda.setDataSaida(object.getString("dataSaida"));
            return comanda;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* em desenvolvimento
    public static ArrayAdapter<String> buscarMesas(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<String> numeroMesas = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for(int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                String numeroMesa = null;

                //pegar os itens do json e atribui a um objeto comanda
                try {
                    numeroMesa = String.valueOf(item.getInt("numeromesa"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                numeroMesas.add(numeroMesa);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return numeroMesas.toArray(new String[0]);
    }

    */

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
