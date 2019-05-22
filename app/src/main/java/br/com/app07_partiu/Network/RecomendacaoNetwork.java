package br.com.app07_partiu.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

                restaurante.setCnpj(objeto.getLong("cnpj"));
                restaurante.setQtdMesas(objeto.getInt("qtdMesas"));
                restaurante.setCodigoComanda(objeto.getString("codigoComanda"));
                restaurante.setRazaoSocial(objeto.getString("razaoSocial"));
                restaurante.setNomeFantasia(objeto.getString("nomeFantasia"));
                restaurante.setStatus(objeto.getString("status"));
                restaurante.setLogo(objeto.getString("logo"));
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
