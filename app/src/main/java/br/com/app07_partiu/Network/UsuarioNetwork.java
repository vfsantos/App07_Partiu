package br.com.app07_partiu.Network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.app07_partiu.Model.Endereco;
import br.com.app07_partiu.Model.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsuarioNetwork {

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
        Endereco endereco = new Endereco();

        try {

            usuario.setId(objeto.getInt("id"));
            usuario.setTipo(objeto.getString("tipo"));
            usuario.setCpf(objeto.getString("cpf"));
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
            Log.d("TESTES", "Não acho usuario Login");
            return null;
        }

    }

    public static boolean getUsuario(String url, String variavel, String valor) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/getUsuario?variavel=" + variavel + "&valor=" + valor;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        JSONObject objeto = new JSONObject(resultado);

        Usuario usuario = new Usuario();

        try {

            usuario.setEmail(objeto.getString("email"));

            if (usuario.getEmail().equals("email")) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("TESTES", "Não acho usuario Login");
        }
        return false;

    }


    public static Usuario getUsuarioComEndereco(String url, String variavel, String valor) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/getUsuario?variavel=" + variavel + "&valor=" + valor;


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        JSONObject objeto = new JSONArray(resultado).getJSONObject(0);

        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco();

        try {

            usuario.setId(objeto.getInt("id"));
            usuario.setTipo(objeto.getString("tipo"));
            usuario.setCpf(objeto.getString("cpf"));
            usuario.setNome(objeto.getString("nome"));
            usuario.setDta_nascimento(objeto.getString("dta_nascimento"));
            usuario.setEmail(objeto.getString("email"));
            usuario.setDdd(objeto.getInt("ddd"));
            usuario.setTelefone(objeto.getInt("telefone"));
            usuario.setGenero((objeto.getString("genero").charAt(0)));
            usuario.setSenha(objeto.getString("senha"));
            try{


            endereco.setId(objeto.getJSONObject("endereco").getInt("id"));
            endereco.setLogradouro(objeto.getJSONObject("endereco").getString("logradouro"));
            endereco.setNumero(objeto.getJSONObject("endereco").getString("numero"));
            try{
                endereco.setComplemento(objeto.getJSONObject("endereco").getString("complemento"));
            }catch(Exception e){
                Log.d("NETWORK", "Complemento vazio");
                endereco.setComplemento("");
            }
            endereco.setBairro(objeto.getJSONObject("endereco").getString("bairro"));
            endereco.setCidade(objeto.getJSONObject("endereco").getString("cidade"));
            endereco.setUf(objeto.getJSONObject("endereco").getString("uf"));
            endereco.setCep(objeto.getJSONObject("endereco").getString("cep"));
            }catch(Exception e){
                Log.d("NETOWORK", "Endereco Vazio");
            }
            usuario.setEndereco(endereco);

            return usuario;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void criarCadastroCliente(String url,String tipo, String cpf, String nome, String dta_nascimento, String email, String ddd,
                                                  String telefone, String genero, String senha, String logradouro, String numero, String complemento,
                                                  String bairro, String cidade, String uf, String cep) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/createUsuario?tipo=" + tipo + "&cpf=" + cpf + "&nome=" + nome + "&dta_nascimento=" + dta_nascimento + "&email=" + email + "&ddd=" + ddd +
                "&telefone=" + telefone + "&genero=" + genero + "&senha=" + senha + "&logradouro=" + logradouro + "&numero=" + numero + "&complemento=" + complemento +
                "&bairro=" + bairro + "&cidade=" + cidade + "&uf=" + uf + "&cep=" + cep;
        Log.d("TESTI",url);
        Request request = new Request.Builder()
                .url(url)
                .build();


        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTI",resultado);
    }

    public static Usuario updateUsuario(String url, String id, String idEndereco, String tipo, String cpf, String nome, String dta_nascimento, String email, String ddd,
                              String telefone, String genero, String senha, String logradouro, String numero, String complemento,
                              String bairro, String cidade, String uf, String cep) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        url += "/updateUsuario?id=" + id + "&idEndereco=" + idEndereco + "&tipo=" + tipo + "&cpf=" + cpf + "&nome=" + nome + "&dta_nascimento=" + dta_nascimento +
                "&email=" + email + "&ddd=" + ddd + "&telefone=" + telefone + "&genero=" + genero + "&senha=" + senha + "&logradouro=" + logradouro + "&numero=" + numero +
                "&complemento=" + complemento + "&bairro=" + bairro + "&cidade=" + cidade + "&uf=" + uf + "&cep=" + cep;
        Log.d("TESTE",url);
        Request request = new Request.Builder()
                .url(url)
                .build();


        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        JSONObject objeto = new JSONObject(resultado);

        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco();

        try {

            usuario.setId(objeto.getInt("id"));
            usuario.setTipo(objeto.getString("tipo"));
            usuario.setCpf(objeto.getString("cpf"));
            usuario.setNome(objeto.getString("nome"));
            usuario.setDta_nascimento(objeto.getString("dta_nascimento"));
            usuario.setEmail(objeto.getString("email"));
            usuario.setDdd(objeto.getInt("ddd"));
            usuario.setTelefone(objeto.getInt("telefone"));
            usuario.setGenero((objeto.getString("genero").charAt(0)));
            usuario.setSenha(objeto.getString("senha"));
            try{


                endereco.setId(objeto.getJSONObject("endereco").getInt("id"));
                endereco.setLogradouro(objeto.getJSONObject("endereco").getString("logradouro"));
                endereco.setNumero(objeto.getJSONObject("endereco").getString("numero"));
                try{
                    endereco.setComplemento(objeto.getJSONObject("endereco").getString("complemento"));
                }catch(Exception e){
                    Log.d("NETWORK", "Complemento vazio");
                    endereco.setComplemento("");
                }
                endereco.setBairro(objeto.getJSONObject("endereco").getString("bairro"));
                endereco.setCidade(objeto.getJSONObject("endereco").getString("cidade"));
                endereco.setUf(objeto.getJSONObject("endereco").getString("uf"));
                endereco.setCep(objeto.getJSONObject("endereco").getString("cep"));
            }catch(Exception e){
                Log.d("NETOWORK", "Endereco Vazio");
            }
            usuario.setEndereco(endereco);

            return usuario;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
