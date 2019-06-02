package br.com.app07_partiu.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

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



}
