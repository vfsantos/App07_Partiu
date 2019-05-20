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
import br.com.app07_partiu.Model.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsuarioNetwork {

//                {
//                    "id": 1,
//                        "tipo": "garcom",
//                        "cpf": 57731434885,
//                        "nome": "Benjamin Bento Isaac Ramos",
//                        "dta_nascimento": "08/09/1997",
//                        "email": "benjamin@gmail.com",
//                        "ddd": 11,
//                        "telefone": 27171119,
//                        "genero": "M",
//                        "senha": "123"
//                }

    public static Usuario login(String url, String email, String senha) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/login?email=" + email + "&senha=" + senha;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        JSONObject objeto = new JSONObject(resultado);

        Usuario usuario = new Usuario();

        try {

            usuario.setId(objeto.getInt("id"));
            usuario.setTipo(objeto.getString("tipo"));
            usuario.setCpf(objeto.getInt("cpf"));
            usuario.setNome(objeto.getString("nome"));
            usuario.setDta_nascimento(objeto.getString("dta_nascimento"));
            usuario.setEmail(objeto.getString("email"));
            usuario.setDdd(objeto.getInt("ddd"));
            usuario.setTelefone(objeto.getInt("telefone"));
            usuario.setGenero((objeto.getString("genero").charAt(0)));
            usuario.setSenha(objeto.getString("senha"));
            return usuario;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("TESTES", "Não conectou");
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
