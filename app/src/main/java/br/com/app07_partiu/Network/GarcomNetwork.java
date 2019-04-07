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

                try {
                    comanda.setId(item.getInt("id"));
                } catch (Exception e){
                    comanda.setId(0);
                }

                try {
                    comanda.setCodigoComanda(item.getString("codigocomanda"));
                } catch (Exception e){
                    comanda.setCodigoComanda("erro no carregamento");
                }

                try {
                    comanda.setMesa(item.getInt("mesa"));
                } catch (Exception e){
                    comanda.setMesa(0);
                }

                try {
                    comanda.setStatus(item.getString("status"));
                } catch (Exception e){
                    comanda.setStatus("erro no carregamento");
                }
                /*
                try {
                    comanda.setDataEntrada(item.getString("dataentrada"));
                } catch (Exception e){
                    comanda.setDataEntrada("erro no carregamento");
                }

                try {
                    comanda.setDataSaida(item.getString("datasaida"));
                } catch (Exception e){
                    comanda.setDataSaida("erro no carregamento");
                }
                */

                try {
                    comanda.setValorTotalComanda(item.getDouble("valortotalcomanda"));
                } catch (Exception e){
                    comanda.setValorTotalComanda(0.00);
                }

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
