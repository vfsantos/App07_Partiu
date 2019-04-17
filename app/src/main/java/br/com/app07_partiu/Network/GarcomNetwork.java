package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
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
            for(int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Comanda comanda = new Comanda();

                //pegar os itens do json e atribui a um objeto comanda
                try {
                    comanda.setId(item.getInt("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setCodigoComanda(item.getString("codigocomanda"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setMesa(item.getInt("mesa"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setDataEntrada(Timestamp.valueOf(item.getString("dataentrada")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setDataSaida(Timestamp.valueOf(item.getString("datasaida")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setStatus(item.getString("status"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                comandas.add(comanda);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return comandas.toArray(new Comanda[0]);
    }

    public static Comanda gerarNovaComanda(String url, int mesa) throws  IOException {

        //converte o número da mesa de int para sprint para poder concatenar na url
        String numeroMesa = String.valueOf(mesa);
        String urlGerarSenha = url+"gera_nova_comanda?numeromesa="+numeroMesa;
        OkHttpClient client = new OkHttpClient();
        Comanda comanda = new Comanda();

        Request request = new Request.Builder()
                .url(urlGerarSenha)
                .build();

        Response response = client.newCall(request).execute();
        String  resultado =  response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for(int i = 0; i < vetor.length(); i++){
                JSONObject item = (JSONObject) vetor.get(i);

                try {
                    comanda.setId(item.getInt("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setCodigoComanda(item.getString("codigoComanda"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setMesa(item.getInt("mesa"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setDataEntrada(Timestamp.valueOf(item.getString("dataEntrada")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setDataSaida(Timestamp.valueOf(item.getString("dataSaida")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    comanda.setStatus(item.getString("status"));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw  new IOException(e);
        }

        return comanda;
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

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
