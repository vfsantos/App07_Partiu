package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Item;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemNetwork {

    public static Item[] getItensCardapioGarcom(String url, String cnpj) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Item> itens = new ArrayList<>();
        url += "/getItensRestaurante?cnpj="+cnpj;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();


        try {
            JSONArray vetor = new JSONArray(resultado);
            Log.d("TESTES", "Carregando itens do restaurante "+cnpj);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Item itemCardapio = new Item();

                //pegar os itens do json e atribui a um objeto itemCardápio
                itemCardapio.setId(item.getInt("id"));
                itemCardapio.setCnpjRestaurante(item.getString("cnpjRestaurante"));
                itemCardapio.setCategoria(item.getString("categoria"));
                itemCardapio.setNome(item.getString("nome"));
                itemCardapio.setTipo(item.getString("tipo"));
                itemCardapio.setValor(item.getDouble("valor"));
                itemCardapio.setStatus(item.getString("status"));
                itemCardapio.setDetalhe(item.getString("detalhe"));

                //adiciona cada objeto comanda recebido em um arraylist de itens
                Log.d("TESTES", item.toString());

                itens.add(itemCardapio);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return itens.toArray(new Item[0]);
    }
}
