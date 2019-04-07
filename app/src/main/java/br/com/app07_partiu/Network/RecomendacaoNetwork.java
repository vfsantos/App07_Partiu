package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Estabelecimento;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecomendacaoNetwork {

    public static Estabelecimento[] buscaListaEstabelecimento(String url, String categoria) throws  IOException {

        String urlBuscaListaEstabelecimento = url+categoria;
        OkHttpClient client = new OkHttpClient();
        ArrayList<Estabelecimento> estabelecimentos = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url+categoria)
                .build();

        Response response = client.newCall(request).execute();

        String  resultado =  response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for(int i = 0; i < vetor.length(); i++){
                JSONObject item = (JSONObject) vetor.get(i);
                Estabelecimento estabelecimento = new Estabelecimento();

                estabelecimento.setId(item.getInt("id"));
                estabelecimento.setNome(item.getString("nome"));
                estabelecimento.setImagem(item.getString("imagem"));
                estabelecimentos.add(estabelecimento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw  new IOException(e);
        }

        return estabelecimentos.toArray(new Estabelecimento[0]);
    }

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
