package br.com.app07_partiu.Model;

import java.io.Serializable;
//TODO Apagar se tudo ocorrer bem em HomeGarcom Alert
public class Mesa implements Serializable {
    int numeroDaMesa;

    public int getNumeroDaMesa() {
        return numeroDaMesa;
    }

    public void setNumeroDaMesa(int numeroDaMesa) {
        this.numeroDaMesa = numeroDaMesa;
    }
}
