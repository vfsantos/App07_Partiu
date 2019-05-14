package br.com.app07_partiu.Util;

import java.text.DecimalFormat;

import br.com.app07_partiu.Model.ItemComandaGarcomConvertView;

public class Util {
    //Capitaliza corretamente uma String
    //Só funciona para palavras simples (ex. n funciona em nome completo)
    //java -> Java
    //jAvA -> Java
    public static String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String doubleToReal(double d){
        DecimalFormat df = new DecimalFormat("#.00");
        return "R$"+(df.format(d)).replace(".",",");
    }

}
