package br.com.app07_partiu.Model;

import java.io.Serializable;

public class Mesa implements Serializable {
    int numeroDaMesa;

    public int getNumeroDaMesa() {
        return numeroDaMesa;
    }

    public void setNumeroDaMesa(int numeroDaMesa) {
        this.numeroDaMesa = numeroDaMesa;
    }
}
