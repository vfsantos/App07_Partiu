package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Restaurante;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecomendacaoNetwork {

    public static Restaurante[] getRecomendacaoDiaSemana(String url) throws IOException {
        url += "/recomendacao/getRecomendacaoDiaSemana";
        return getRestaurantes(url);
    }

    public static Restaurante[] getRecomendacaoMaisVisitados(String url) throws IOException {
        url += "/recomendacao/getRecomendacaoMaisVisitados";
        return getRestaurantes(url);
    }

    public static Restaurante[] getRecomendacaoVisitadoRecentemente(String url, int idUsuario) throws IOException {
        url += "/recomendacao/getRecomendacaoVisitadoRecentemente?idUsuario="+idUsuario;
        return getRestaurantes(url);
    }

    public static Restaurante[] getRecomendacaoEspecialidadeUsuario(String url, int idUsuario) throws IOException {
        url += "/recomendacao/getRecomendacaoEspecialidadeUsuario?idUsuario="+idUsuario;
        return getRestaurantes(url);
    }
    public static Restaurante[] getRecomendacaoRestauranteAvaliado(String url) throws IOException {
        url += "/recomendacao/getRecomendacaoRestauranteAvaliado";
        return getRestaurantes(url);
    }

    //TODO adicionar ENDERECO
    /*endereco.setId(item.getJSONObject("endereco").getInt("id"));
                endereco.getLogradouro(item.getJSONObject("endereco").getString("logradouro"));
                endereco.setNumero(item.getJSONObject("endereco").getString("numero"));
                endereco.setComplemento(item.getJSONObject("endereco").getString("complemento"));
                endereco.setBairro(item.getJSONObject("endereco").getString("bairro"));
                endereco.setCidade(item.getJSONObject("endereco").getString("cidade"));
                endereco.setUf(item.getJSONObject("endereco").getString("uf"));
                endereco.setCep(item.getJSONObject("endereco").getString("cep"));
                */

    private static Restaurante[] getRestaurantes(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Restaurante> restaurantes = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject objeto = (JSONObject) vetor.get(i);
                Restaurante restaurante = new Restaurante();
                Endereco endereco = new Endereco();

                restaurante.setCnpj(objeto.getLong("cnpj"));
                restaurante.setQtdMesas(objeto.getInt("qtdMesas"));
                restaurante.setCodigoComanda(objeto.getString("codigoComanda"));
                restaurante.setRazaoSocial(objeto.getString("razaoSocial"));
                restaurante.setNomeFantasia(objeto.getString("nomeFantasia"));
                restaurante.setStatus(objeto.getString("status"));
                restaurante.setEndereco(endereco);
                endereco.setId(objeto.getJSONObject("endereco").getInt("id"));
                endereco.setLogradouro(objeto.getJSONObject("endereco").getString("logradouro"));
                endereco.setNumero(objeto.getJSONObject("endereco").getString("numero"));
                endereco.setComplemento(objeto.getJSONObject("endereco").getString("complemento"));
                endereco.setBairro(objeto.getJSONObject("endereco").getString("bairro"));
                endereco.setCidade(objeto.getJSONObject("endereco").getString("cidade"));
                endereco.setUf(objeto.getJSONObject("endereco").getString("uf"));
                endereco.setCep(objeto.getJSONObject("endereco").getString("cep"));

                //TODO reparar imagens
                restaurante.setLogo(objeto.getString("logo"));
//                restaurante.setLogo(null);
                restaurante.setDescricao(objeto.getString("descricao"));

                //TODO pegar horarios

                restaurantes.add(restaurante);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return restaurantes.toArray(new Restaurante[0]);
    }

}
