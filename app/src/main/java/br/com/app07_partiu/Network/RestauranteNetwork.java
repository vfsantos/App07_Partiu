package br.com.app07_partiu.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.Model.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestauranteNetwork {



 /*   {"cnpj": 2704394000356,
        "qtdMesas": 23,
        "codigoComanda": "OSA",
        "razaoSocial": "OUTBACK STEAKHOUSE",
        "nomeFantasia": "OUTBACK STEAKHOUSE ANÁLIA FRANCO",
        "status": "A",
        "logo": "iVBORw0KGrkJggg==",
            "descricao": "É o ambiente perfeito para juntar tosdo mundo! Quer ver? O cardápio inclui porções bem generosas, perfeitas para compartilhar! A incomparável Aussie Cheese Fries (Batata com bacon e queijo), a suculenta Ribs On The Barbie (nossa costelinha), nosso ícone Bloomin? Onion (cebola empanhada), pão australiano, chopp na caneca congelada e muitas outras opções vão acompanhar a sua visita. Happy hour, almoço, jantar, comemorações, fim de semana: venha para o Outback! "
}
*/


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

            restaurante.setCnpj(objeto.getLong("cnpj"));
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

}
