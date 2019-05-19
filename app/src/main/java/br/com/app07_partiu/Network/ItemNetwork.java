package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.ComandaConvertView;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.ItemCardapioGarcomConvertView;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;
import br.com.app07_partiu.Model.ItemConvertView;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemNetwork {

    public static ItemConvertView[] getItensCardapio(String url, long cnpj) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<ItemConvertView> itens = new ArrayList<>();
        url += "/getItensRestaurante?cnpj="+cnpj;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();


        try {
            JSONArray vetor = new JSONArray(resultado);
            Log.d("TESTES", "Carregando itens do restaurante");
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Item itemCardapio = new Item();
                ItemConvertView itemConvertView = new ItemConvertView();

                //pegar os itens do json e atribui a um objeto itemCardápio
                itemCardapio.setId(item.getInt("id"));
                itemCardapio.setCnpjRestaurante(item.getLong("cnpjRestaurante"));
                itemCardapio.setCategoria(item.getString("categoria"));
                itemCardapio.setNome(item.getString("nome"));
                itemCardapio.setTipo(item.getString("tipo"));
                itemCardapio.setValor(item.getDouble("valor"));
                itemCardapio.setStatus(item.getString("status"));

                //adiciona cada objeto comanda recebido em um arraylist de itens
                Log.d("TESTES", item.toString());

                itemConvertView.setId(itemCardapio.getId());
                itemConvertView.setCategoria(itemCardapio.getCategoria());
                itemConvertView.setNome(itemCardapio.getNome());
                itemConvertView.setValor(String.valueOf(itemCardapio.getValor()));
                itemConvertView.setTipo(itemCardapio.getTipo());

                itens.add(itemConvertView);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return itens.toArray(new ItemConvertView[0]);
    }

    public static ItemCardapioGarcomConvertView[] getItensCardapioGarcom(String url, long cnpj) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<ItemCardapioGarcomConvertView> itens = new ArrayList<>();
        url += "/getItensRestaurante?cnpj="+cnpj;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();


        try {
            JSONArray vetor = new JSONArray(resultado);
            Log.d("TESTES", "Carregando itens do restaurante");
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Item itemCardapio = new Item();
                ItemCardapioGarcomConvertView itemCardapioGarcomConvertView = new ItemCardapioGarcomConvertView();

                //pegar os itens do json e atribui a um objeto itemCardápio
                itemCardapio.setId(item.getInt("id"));
                itemCardapio.setCnpjRestaurante(item.getLong("cnpjRestaurante"));
                itemCardapio.setCategoria(item.getString("categoria"));
                itemCardapio.setNome(item.getString("nome"));
                itemCardapio.setTipo(item.getString("tipo"));
                itemCardapio.setValor(item.getDouble("valor"));
                itemCardapio.setStatus(item.getString("status"));

                //adiciona cada objeto comanda recebido em um arraylist de itens
                Log.d("TESTES", item.toString());

                itemCardapioGarcomConvertView.setId(itemCardapio.getId());
                itemCardapioGarcomConvertView.setNomeItem(itemCardapio.getNome());
                itemCardapioGarcomConvertView.setDetalheItem("Lorem ipsum");
                itemCardapioGarcomConvertView.setValorItem(String.valueOf(itemCardapio.getValor()));

                itens.add(itemCardapioGarcomConvertView);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return itens.toArray(new ItemCardapioGarcomConvertView[0]);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
