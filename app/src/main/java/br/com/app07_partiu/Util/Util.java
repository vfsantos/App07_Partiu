package br.com.app07_partiu.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.R;

public class Util {
    //Capitaliza corretamente uma String
    //Só funciona para palavras simples (ex. n funciona em nome completo)
    //java -> Java
    //jAvA -> Java

    public static String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String doubleToReal(double d){
        if (d > 0){
            DecimalFormat df = new DecimalFormat("#.00");
            return "R$"+((df.format(d)).replace(".",","));
        }
        return "R$00,00";
    }

    public static Drawable getDrawable(Context context, String nome){

        Class<?> c = R.drawable.class;
        try {
            Field idField = c.getDeclaredField(nome);
            int id = idField.getInt(idField);
            return context.getResources().getDrawable(id, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static String nomeToNomeUltimo(String nome){
        // Pedro Gallon Alves -> Pedro Alves
        return nome.split(" ")[0] +" "+ nome.split(" ")[nome.split(" ").length-1];

    }

    public static String nomeToPrimeiroNome(String nome){
        // Pedro Gallon Alves -> Pedro
        return nome.split(" ")[0];

    }

    public static String nomeToNomeUltimoAbrev(String nome){
        // Pedro Gallon Alves -> Pedro Alves
        return nome.split(" ")[0] +" "+ nome.split(" ")[nome.split(" ").length-1].charAt(0);

    }

    public static Item[] formatItens(Item[] itens){

        Set idPedidos = new HashSet();
        List<Item> itensComeco = new ArrayList<>(); //selecionados e nao selecionados
        List<Item> itensFim = new ArrayList<>(); //pagos

        for (Item i : itens) {

            //se existir idPedido no set, é necessario adcionar o novo usuario ao usuariosPedido do item correspondente
            if (idPedidos.contains(i.getIdPedido())) {
                //pega o item que tem idPedido ==
                for (Item item : itensComeco) {
                    if (item.getIdPedido() == i.getIdPedido()) {
                        List<Usuario> us = item.getUsuariosPedido();
                        //adciona usuario e devolve ao item
                        Usuario u = new Usuario();
                        u.setId(i.getIdUsuario());
                        u.setNome(i.getNomeUsuario());
                        u.setEmail(i.getEmailUsuario());
                        u.setPorcPedido(i.getPorcPaga());
                        u.setStatusPedido(i.getStatusPedidoUsuario());
                        us.add(u);
                        item.setUsuariosPedido(us);
                    }
                }
                for (Item item : itensFim) {
                    if (item.getIdPedido() == i.getIdPedido()) {
                        List<Usuario> us = item.getUsuariosPedido();
                        //adciona usuario e devolve ao item
                        Usuario u = new Usuario();
                        u.setId(i.getIdUsuario());
                        u.setNome(i.getNomeUsuario());
                        u.setEmail(i.getEmailUsuario());
                        u.setPorcPedido(i.getPorcPaga());
                        u.setStatusPedido(i.getStatusPedidoUsuario());
                        us.add(u);
                        item.setUsuariosPedido(us);
                    }
                }

                // Se não existir idPedido, adciona direto na lista
            } else {
                idPedidos.add(i.getIdPedido());
                if (i.getNomeUsuario() != null) {
                    Usuario u = new Usuario();
                    u.setId(i.getIdUsuario());
                    u.setNome(i.getNomeUsuario());
                    u.setEmail(i.getEmailUsuario());
                    u.setPorcPedido(i.getPorcPaga());
                    u.setStatusPedido(i.getStatusPedidoUsuario());
                    List<Usuario> temp = new ArrayList<Usuario>();
                    temp.add(u);
                    i.setUsuariosPedido(temp);

                }else{

                    i.setUsuariosPedido(new ArrayList<Usuario>());
                }
                if (i.getStatusPedido().equals("P")){
                    itensFim.add(i);
                }else {
                    itensComeco.add(i);
                }

//                itensF.add(i);

            }

        }

        List<Item> itensAll = new ArrayList<>();
        itensAll.addAll(itensComeco);
        itensAll.addAll(itensFim);

        return itensAll.toArray(new Item[itensAll.size()]);

    }

    public static void showSnackbar(View view, String texto){
        Snackbar snackbarErroLogin = Snackbar.make(view, texto, Snackbar.LENGTH_SHORT);
        snackbarErroLogin.show();
    }

    public static void showSnackbar(View view, int idString){
        Snackbar snackbarErroLogin = Snackbar.make(view, idString, Snackbar.LENGTH_SHORT);
        snackbarErroLogin.show();
    }

}
