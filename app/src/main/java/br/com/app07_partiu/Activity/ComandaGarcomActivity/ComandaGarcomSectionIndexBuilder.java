package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;

import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;

public class ComandaGarcomSectionIndexBuilder {

    public static Object[] buildSectionHeaders(ItemComandaGarcomConvertView[] itens){
        ArrayList<String> resultado = new ArrayList<>();
        TreeSet<String> usados = new TreeSet<>();
        for(ItemComandaGarcomConvertView item:itens){
            String letra = item.getDescricao().substring(0,1);
            if((!usados.contains(letra))){
                resultado.add(letra);
            }
            usados.add(letra);
        }
        return resultado.toArray(new Object[0]);
    }

    public static Hashtable<Integer, Integer> buildSectionForPositionMap(ItemComandaGarcomConvertView[] itens){
        Hashtable<Integer, Integer> resultados = new Hashtable<>();
        TreeSet<String> usados = new TreeSet<>();

        int secao = -1;

        for(int i = 0; i < itens.length; i++){
            String letra = itens[i].getDescricao().substring(0,1);

            if(!usados.contains(letra)){
                secao++;
                usados.add(letra);
            }
            resultados.put(i, secao);
        }
        return resultados;
    }


    public static Hashtable<Integer, Integer> buildPositionForSectionMap(ItemComandaGarcomConvertView[] itens){
        Hashtable<Integer, Integer> resultados = new Hashtable<>();
        TreeSet<String> usados = new TreeSet<>();

        int secao = -1;

        for(int i = 0; i < itens.length; i++){
            String letra = itens[i].getDescricao().substring(0,1);

            if(!usados.contains(letra)){
                secao++;
                usados.add(letra);
                resultados.put(secao, i);
            }
        }
        return resultados;
    }
}

