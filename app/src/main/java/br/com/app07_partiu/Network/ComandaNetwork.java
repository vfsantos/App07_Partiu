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

    public static String selecionarPedidoUsuario(String url, int idPedido, int idUsuario, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/selecionarPedidoUsuario?idPedido=" + idPedido + "&idUsuario=" + idUsuario + "&idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", "selecionarPedidoUsuario: " + resultado);
        return resultado;
    }

    public static String deselecionarPedidoUsuario(String url, int idPedido, int idUsuario, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/deselecionarPedidoUsuario?idPedido=" + idPedido + "&idUsuario=" + idUsuario + "&idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", "selecionarPedidoUsuario: " + resultado);
        return resultado;
    }

    public static void insertUsuarioComanda(String url, int idUsuario, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/vincularUsuarioComanda?idUsuario=" + idUsuario + "&idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", "insertUsuarioComandaResult: " + resultado);
    }

    public static Item[] removerPedidoComanda(String url, int idPedido, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/removerPedidoComanda?idPedido=" + idPedido + "&idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();

        List<Item> itens = new ArrayList<>();

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
                    it.setPorcPaga(item.getDouble("porcPaga"));
                    it.setStatusPedidoUsuario(item.getString("statusPedidoUsuario"));
                } catch (JSONException e) {
//                    Log.d("TESTES", "Pedido sem usuario");
                }

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                itens.add(it);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itens.size() > 0) {
            return itens.toArray(new Item[0]);
        }
        return null;
    }

    public static String getDataAtualizacaoComanda(String url, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/getAtualizacaoComanda?idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
//        Log.d("TESTES", "DataAtualização: " + resultado);

        return resultado;
    }
    public static String finalizarItemPedidoUsuario(String url, int idUsuario, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/finalizarItemPedidoUsuario?idUsuario=" + idUsuario+"&idComanda="+idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", "DataAtualização: " + resultado);

        return resultado;
    }


    public static int[] getIdsUsuarioComanda(String url, int idComanda) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url += "/getIdsUsuarioComanda?idComanda=" + idComanda;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", "IdsUsuarios: " + resultado);

        List<Integer> idUsuario = new ArrayList<>();
        try {
            JSONArray vetor = new JSONArray(resultado);
            for (int i = 0; i < vetor.length(); i++) {
                idUsuario.add((Integer) vetor.get(i));
            }

            Object[] objects = idUsuario.toArray();
            int[] array = new int[objects.length];
            for (int i = 0; i < objects.length; i++) {
                array[i] = (int) objects[i];
            }
            return array;

        } catch (JSONException e) {
            e.printStackTrace();
            return new int[0];
        }
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
                    it.setPorcPaga(item.getDouble("porcPaga"));
                    it.setStatusPedidoUsuario(item.getString("statusPedidoUsuario"));
                } catch (JSONException e) {
                    Log.d("TESTES", "Pedido sem usuario");
                    e.printStackTrace();
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

    public static Item[] getPedidoUsuarioBydId(String url, int idPedido) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Item> itens = new ArrayList<>();
        url += "/getPedidoUsuarioBydId?idPedido=" + idPedido;

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
                    it.setPorcPaga(item.getDouble("porcPaga"));
                    it.setStatusPedidoUsuario(item.getString("statusPedidoUsuario"));
                } catch (JSONException e) {
                    Log.d("TESTES", "Pedido sem usuario");
                    e.printStackTrace();
                }
                itens.add(it);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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


    public static Comanda getComandaByCodigo(String url, String codigo) throws IOException, JSONException {

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

        Log.d("TESTES", "ComandaNetwork getComandaByCodigo.Comanda=" + comanda.toString());
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

    public static List<Item> createItemPedido(String url, Item[] itensAdicionar, int idComanda) throws IOException {

        int[] idItens = new int[itensAdicionar.length];
        String[] obsItens = new String[itensAdicionar.length];
        //TODO pegar observacao
        for (int i = 0; i < itensAdicionar.length; i++) {
            idItens[i] = itensAdicionar[i].getId();
            obsItens[i] = "";
        }

        url += "/createItemPedido?idComanda=" + idComanda;
        for (int id : idItens) {
            url += "&idItens=" + id;
        }

        for (String obs : obsItens) {
            if (obs == null || obs == "") {
                url += "&obsItens=" + "-";
            } else {
                url += "&obsItens=" + obs;
            }
        }

        Log.d("TESTES", "URL: " + url);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();
        Log.d("TESTES", resultado);

        List<Item> itens = new ArrayList<Item>();

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
                    it.setPorcPaga(item.getDouble("porcPaga"));
                    it.setStatusPedidoUsuario(item.getString("statusPedidoUsuario"));
                } catch (JSONException e) {
                    Log.d("TESTES", "Pedido sem usuario");
                }

                //adiciona cada objeto comanda recebido em um arraylist de comandas
                itens.add(it);
            }
            return itens;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Item[] getPedidosByUsuarioComanda(String url, int idComanda, int idUsuario) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Item> itens = new ArrayList<>();
        url += "/getPedidosByUsuario?idComanda=" + idComanda+"&idUsuario="+idUsuario;
        Log.d("TESTES", "URL="+url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String resultado = response.body().string();

        Log.d("TESTES", "getPedidosByUsuarioComanda="+resultado);

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
                    it.setPorcPaga(item.getDouble("porcPaga"));
                    it.setStatusPedidoUsuario(item.getString("statusPedidoUsuario"));
                } catch (JSONException e) {
                    Log.d("TESTES", "Pedido sem usuario");
                    e.printStackTrace();
                }
                //adiciona cada objeto comanda recebido em um arraylist de comandas
                itens.add(it);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itens.size() > 0) {
            return itens.toArray(new Item[0]);
        }
        return null;
    }
}