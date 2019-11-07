package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestauranteNetwork {

    public static Restaurante getRestauranteByIdGarcom(String url, int idGarcom) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/getRestauranteByIdGarcom?idGarcom=" + idGarcom;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        JSONObject objeto = new JSONObject(resultado);

        Restaurante restaurante = new Restaurante();

        try {

            restaurante.setCnpj(objeto.getString("cnpj"));
            restaurante.setQtdMesas(objeto.getInt("qtdMesas"));
            restaurante.setCodigoComanda(objeto.getString("codigoComanda"));
            restaurante.setRazaoSocial(objeto.getString("razaoSocial"));
            restaurante.setNomeFantasia(objeto.getString("nomeFantasia"));
            restaurante.setStatus(objeto.getString("status"));
            restaurante.setLogo(objeto.getString("logo"));
            restaurante.setDescricao(objeto.getString("descricao"));

            Log.d("TESTES", restaurante.toString());

            return restaurante;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static Restaurante[] getRecomendacaoRestauranteAvaliado(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Restaurante> restaurantes = new ArrayList<>();
        url += "/recomendacao/getRecomendacaoRestauranteAvaliado";

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Restaurante restaurante = new Restaurante();
                Endereco endereco = new Endereco();
                Restaurante itemRestauranteConvertView = new Restaurante();

                //pegar os itens do json e atribui a um objeto restaurante
                restaurante.setCnpj(item.getString("cnpj"));
                restaurante.setNomeFantasia(item.getString("nomeFantasia"));
                restaurante.setLogo(null);
                restaurante.setAvaliacao(item.getString("avaliacao"));
                restaurante.setDescricao(item.getString("descricao"));
                endereco.setId(item.getJSONObject("endereco").getInt("id"));
                endereco.setLogradouro(item.getJSONObject("endereco").getString("logradouro"));
                endereco.setNumero(item.getJSONObject("endereco").getString("numero"));
                endereco.setComplemento(item.getJSONObject("endereco").getString("complemento"));
                endereco.setBairro(item.getJSONObject("endereco").getString("bairro"));
                endereco.setCidade(item.getJSONObject("endereco").getString("cidade"));
                endereco.setUf(item.getJSONObject("endereco").getString("uf"));
                endereco.setCep(item.getJSONObject("endereco").getString("cep"));


                //adiciona cada objeto comanda recebido em um arraylist de comandas
                itemRestauranteConvertView.setCnpj(restaurante.getCnpj());
                itemRestauranteConvertView.setNomeFantasia(restaurante.getNomeFantasia());
                itemRestauranteConvertView.setLogo(restaurante.getLogo());
                itemRestauranteConvertView.setAvaliacao(restaurante.getAvaliacao());
                itemRestauranteConvertView.setDescricao(restaurante.getDescricao());
                itemRestauranteConvertView.setEndereco(endereco);
                restaurantes.add(itemRestauranteConvertView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return restaurantes.toArray(new Restaurante[0]);
    }




}
