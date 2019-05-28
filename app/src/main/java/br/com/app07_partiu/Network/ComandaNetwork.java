package br.com.app07_partiu.Network;

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
import br.com.app07_partiu.Model.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ComandaNetwork {

    public static ComandaConvertView[] buscarComandas(String url, int idGarcom, char status) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<ComandaConvertView> comandas = new ArrayList<>();
        url += "/getComandasByStatusAndId?idGarcom=" + idGarcom + "&status=" + status;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();


        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Comanda comanda = new Comanda();
                ComandaConvertView comandaConvertView = new ComandaConvertView();

                //pegar os itens do json e atribui a um objeto comanda
                comanda.setId(item.getInt("id"));
                comanda.setCodigoComanda(item.getString("codigo"));
                comanda.setStatus(item.getString("status"));
                comanda.setMesa(item.getInt("mesa"));
                comanda.setDataEntrada(item.getString("dtaEntrada"));
                //comanda.setDataSaida(item.getString("dtaSaida"));

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                Log.d("TESTES", comanda.toString());

                comandaConvertView.setId(comanda.getId());
                comandaConvertView.setCodigoComanda(comanda.getCodigoComanda());
                comandaConvertView.setValorTotalComanda("120,00");
                comandaConvertView.setMesa(String.valueOf(comanda.getMesa()));
                comandaConvertView.setDataEntrada(comanda.getDataEntrada());
                //comandaConvertView.setDataSaida(comanda.getDataSaida());
                comandaConvertView.setStatus(String.valueOf(comanda.getStatus()));


                comandas.add(comandaConvertView);
                System.out.println("Array de comandas " + comandas.toArray());

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return comandas.toArray(new ComandaConvertView[0]);
    }

    public static String selecionarPedido(String url, int idPedido, int idUsuario) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/selecionarPedido?idPedido=" + idPedido+"&idUsuario="+idUsuario;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES","SelecionarPedidoResult: "+resultado);
        return resultado;
    }

    public static String getDataAtualizacaoComanda(String url, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/getDataAtualizacaoComanda?idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES","DataAtualização: "+resultado);

        return resultado;
    }

    public static Item[] buscarPedidosComanda(String url, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Item> itens = new ArrayList<>();
        url += "/getPedidosComanda?idComanda=" + idComanda;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();


        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                JSONObject item = (JSONObject) vetor.get(i);
                Item it = new Item();

                //pegar os itens do json e atribui a um objeto comanda
                it.setId(item.getInt("id"));
                it.setCnpjRestaurante(item.getLong("cnpjRestaurante"));
                it.setCategoria(item.getString("categoria"));
                it.setNome(item.getString("nome"));
                it.setTipo(item.getString("tipo"));
                it.setValor(item.getDouble("valor"));
                it.setStatus(item.getString("status"));
                it.setIdPedido(item.getInt("idPedido"));
                it.setPorc_desconto(item.getInt("porc_desconto"));
                it.setData(item.getString("data"));
                it.setStatusPedido(item.getString("statusPedido"));
                try {
                    it.setIdUsuario(item.getInt("idUsuario"));
                    it.setNomeUsuario(item.getString("nomeUsuario"));
                    it.setEmailUsuario(item.getString("emailUsuario"));
                } catch (JSONException e) {
                    Log.d("TESTES", "Pedido sem usuario");
                }

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                itens.add(it);

            }


        } catch (JSONException e) {
            e.printStackTrace();
//            throw new IOException(e);
        }
        if (itens.size() > 0) {
            return itens.toArray(new Item[0]);
        }
        return null;
    }


    public static Comanda createComanda(String url, int idGarcom, int mesa) throws IOException, JSONException {

        //converte o número da mesa de int para string para poder concatenar na url
        String numeroMesa = String.valueOf(mesa);
        String urlGerarSenha = url + "/createComanda?idGarcom=" + idGarcom + "&mesa=" + mesa;
        OkHttpClient client = new OkHttpClient();
        Comanda comanda = new Comanda();

        Request request = new Request.Builder()
                .url(urlGerarSenha)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);
        JSONObject object = new JSONObject(resultado);
        try {
            comanda.setId(object.getInt("id"));
            comanda.setCodigoComanda(object.getString("codigo"));
            comanda.setStatus(object.getString("status"));
            comanda.setMesa(object.getInt("mesa"));
            comanda.setDataEntrada(object.getString("dtaEntrada"));


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return comanda;
    }


    public static Comanda getCodComanda(String url, String codigo) throws IOException, JSONException {

        //converte o número da mesa de int para string para poder concatenar na url
        String urlGetSenha = url + "/getComandaByCodigo?codigo=" + codigo;
        OkHttpClient client = new OkHttpClient();
        Comanda comanda = new Comanda();

        Request request = new Request.Builder()
                .url(urlGetSenha)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);
        JSONObject object = new JSONObject(resultado);
        try {
            comanda.setId(object.getInt("id"));
            comanda.setCodigoComanda(object.getString("codigo"));
            comanda.setStatus(object.getString("status"));
            comanda.setMesa(object.getInt("mesa"));
            comanda.setDataEntrada(object.getString("dtaEntrada"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return comanda;
    }

    public static Comanda getComandaById(String url, int id) throws IOException, JSONException {

        //converte o número da mesa de int para string para poder concatenar na url
        String urlGetSenha = url + "/getComandaById?idComanda=" + id;
        OkHttpClient client = new OkHttpClient();
        Comanda comanda = new Comanda();

        Request request = new Request.Builder()
                .url(urlGetSenha)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);
        JSONObject object = new JSONObject(resultado);
        try {
            comanda.setId(object.getInt("id"));
            comanda.setCodigoComanda(object.getString("codigo"));
            comanda.setStatus(object.getString("status"));
            comanda.setMesa(object.getInt("mesa"));
            comanda.setDataEntrada(object.getString("dtaEntrada"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return comanda;
    }

    public static List<Item> createItemPedido(String url, int[] idItens, String[] obsItens, int idComanda) throws IOException {

        String urlGetItem = url + "/createItemPedido?idComanda=" + idComanda;
        for (int id : idItens) {
            url += "&idItens=" + id;
        }

        for (String obs : obsItens){
            if (obs == null || obs ==""){
                url += "&obsItens=" + "-";
            }else{
                url += "&obsItens=" + obs;
            }
        }

        Log.d("TESTES", "URL: "+url);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlGetItem)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);

        List<Item> itens = new ArrayList<Item>();

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
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
