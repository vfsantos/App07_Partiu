package br.com.app07_partiu.Activity.PedidoSelecaoGarcomActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;

import br.com.app07_partiu.Model.ItemConvertView;

public class PedidoSelecaoGarcomSectionIndexBuilder {

    //cria um array de cabeçalhos de seção; países devem estar ordenados por nome
    public static Object[] buildSectionHeaders(ItemConvertView[] items){
        ArrayList<String> resultado = new ArrayList<>();
        TreeSet<String> usados = new TreeSet<>();
        for(ItemConvertView item:items){
            String letra = item.getNome().substring(0,1);
            if((!usados.contains(letra))){
                resultado.add(letra);
            }
            usados.add(letra);
        }
        return resultado.toArray(new Object[0]);
    }
    //cria uma mapa para responder: posicao --> secao de dados ordenados pelo nome
    public static Hashtable<Integer, Integer> buildSectionForPositionMap(ItemConvertView[] items){
        Hashtable<Integer, Integer> resultados = new Hashtable<>();
        TreeSet<String> usados = new TreeSet<>();

        int secao = -1;

        for(int i = 0; i < items.length; i++){
            String letra = items[i].getNome().substring(0,1);

            if(!usados.contains(letra)){
                secao++;
                usados.add(letra);
            }
            resultados.put(i, secao);
        }
        return resultados;
    }

    //cria uma mapa para responder: secao --> posicao de dados ordenados pelo nome
    public static Hashtable<Integer, Integer> buildPositionForSectionMap(ItemConvertView[] items){
        Hashtable<Integer, Integer> resultados = new Hashtable<>();
        TreeSet<String> usados = new TreeSet<>();

        int secao = -1;

        for(int i = 0; i < items.length; i++){
            String letra = items[i].getNome().substring(0,1);

            if(!usados.contains(letra)){
                secao++;
                usados.add(letra);
                resultados.put(secao, i);
            }
        }
        return resultados;
    }
}
