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

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemNetwork {

    public static List<Item> buscarItensCardapio(String url) throws IOException, JSONException {

        //converte o número da mesa de int para string para poder concatenar na url
        String urlGetItem = url + "/getPedidosComanda?idComanda=" + idComanda;
        OkHttpClient client = new OkHttpClient();
        List<Item> itens = new ArrayList<Item>();

        Request request = new Request.Builder()
                .url(urlGetItem)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);

        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject obj = (JSONObject) vetor.get(i);
                Item item = new Item();
                item.setId(obj.getInt("id"));
                item.setCnpjRestaurante(obj.getLong("cnpjRestaurante"));
                item.setCategoria(obj.getString("categoria"));
                item.setNome(obj.getString("nome"));
                item.setTipo(obj.getString("tipo"));
                item.setValor(obj.getDouble("valor"));
                item.setStatus(obj.getString("status"));
                Usuario usuario = new Usuario();
                usuario.setId(obj.getJSONObject("usuario").getInt("id"));
                usuario.setTipo(obj.getJSONObject("tipo").getString("tipo"));
                usuario.setCpf(obj.getJSONObject("cpf").getLong("cpf"));
                usuario.setNome(obj.getJSONObject("nome").getString("nome"));
                usuario.setDta_nascimento(obj.getJSONObject("dta_nascimento").getString("dta_nascimento"));
                usuario.setEmail(obj.getJSONObject("email").getString("email"));
                usuario.setDdd(obj.getJSONObject("ddd").getInt("ddd"));
                usuario.setTelefone(obj.getJSONObject("telefone").getInt("telefone"));
                usuario.setGenero((obj.getJSONObject("genero").getString("genero")).charAt(0));
                usuario.setSenha(obj.getJSONObject("senha").getString("senha"));
                item.setUsuario(usuario);
                item.setIdPedido(obj.getInt("idPedido"));
                item.setPorc_desconto(obj.getDouble("porc_desconto"));
                item.setIdComanda(obj.getString("idComanda"));
                item.setData(obj.getString("data"));
                item.setStatusPedido(obj.getString("statusPedido"));

                itens.add(item);

            }
            return itens;
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
