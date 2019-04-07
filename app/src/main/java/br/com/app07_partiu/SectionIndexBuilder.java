package br.com.app07_partiu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;

import br.com.app07_partiu.Model.Comanda;

public class SectionIndexBuilder {
    //cria um array de cabeçalhos de seção; países devem estar ordenados por nome
    public static Object[] buildSectionHeaders(Comanda[] comandas){
        ArrayList<String> resultado = new ArrayList<>();
        TreeSet<String> usados = new TreeSet<>();
        for(Comanda comanda:comandas){
            String letra = comanda.getCodigoComanda().substring(0,1);
            if((!usados.contains(letra))){
                resultado.add(letra);
            }
            usados.add(letra);
        }
        return resultado.toArray(new Object[0]);
    }
    //cria uma mapa para responder: posicao --> secao de dados ordenados pelo nome
    public static Hashtable<Integer, Integer> buildSectionForPositionMap(Comanda[] comandas){
        Hashtable<Integer, Integer> resultados = new Hashtable<>();
        TreeSet<String> usados = new TreeSet<>();

        int secao = -1;

        for(int i = 0; i < comandas.length; i++){
            String letra = comandas[i].getCodigoComanda().substring(0,1);

            if(!usados.contains(letra)){
                secao++;
                usados.add(letra);
            }
            resultados.put(i, secao);
        }
        return resultados;
    }

    //cria uma mapa para responder: secao --> posicao de dados ordenados pelo nome
    public static Hashtable<Integer, Integer> buildPositionForSectionMap(Comanda[] comandas){
        Hashtable<Integer, Integer> resultados = new Hashtable<>();
        TreeSet<String> usados = new TreeSet<>();

        int secao = -1;

        for(int i = 0; i < comandas.length; i++){
            String letra = comandas[i].getCodigoComanda().substring(0,1);

            if(!usados.contains(letra)){
                secao++;
                usados.add(letra);
                resultados.put(secao, i);
            }
        }
        return resultados;
    }
}

