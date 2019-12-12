package br.com.app07_partiu.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

import br.com.app07_partiu.Activity.LoginClienteActivity;
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
        List<Item> itensAll = new ArrayList<>(); //selecionados e nao selecionados
        List<Item> itensFim = new ArrayList<>(); //pagos

        try {
            for (Item i : itens) {

                //se existir idPedido no set, é necessario adcionar o novo usuario ao usuariosPedido do item correspondente
                if (idPedidos.contains(i.getIdPedido())) {
                    //pega o item que tem idPedido ==
                    for (Item item : itensAll) {
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

                    } else {

                        i.setUsuariosPedido(new ArrayList<Usuario>());
                    }
                    if (i.getStatusPedido().equals("P")) {
                        itensFim.add(i);
                    } else {
                        itensAll.add(i);
                    }

//                itensF.add(i);

                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

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

    public static void showManutencaoDialog(final Context context) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Em construção!");
        builder.setMessage("Feature planejada para o 2º Release");

        builder.setCancelable(true);

        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TESTES", "DialogClicked: No");
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public static void logoff(final Context context){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Sair");
        builder.setMessage("Deseja realmente sair?");

        builder.setCancelable(true);

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(context, LoginClienteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public static void comandaFechada(final Context context){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Comanda Fechada");
        builder.setMessage("Essa comanda não pode ser alterada pois já se encontra fechada.");

        builder.setCancelable(true);

        builder.setNegativeButton("Ok, entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public static void dataNascimentoInvalida(final Context context){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle("Data de nascimento inválida");
        builder.setMessage("A data de nascimento não pode ser maior que a atual. Por favor informe " +
                "uma data de nascimento válida pra continuar.");

        builder.setCancelable(true);

        builder.setNegativeButton("Ok, entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }




}
