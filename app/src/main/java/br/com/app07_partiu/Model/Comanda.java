package br.com.app07_partiu.Model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Date;

public class Comanda implements Serializable, Comparable {

    private int id;
    private String codigoComanda;
    private int mesa;
    private String status;
    private Date dataEntrada;
    private Date dataSaida;
    private double valorTotalComanda;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoComanda() {
        return codigoComanda;
    }

    public void setCodigoComanda(String codigoComanda) {
        this.codigoComanda = codigoComanda;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public double getValorTotalComanda() {
        return valorTotalComanda;
    }

    public void setValorTotalComanda(double valorTotalComanda) {
        this.valorTotalComanda = valorTotalComanda;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", codigoComanda='" + codigoComanda + '\'' +
                ", mesa=" + mesa +
                ", status='" + status + '\'' +
                ", dataEntrada=" + dataEntrada +
                ", dataSaida=" + dataSaida +
                ", valorTotalComanda=" + valorTotalComanda +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return 0;
        } else {
            /* java.text.Collator API 1.5
             * A classe Collator executa comparacao de sequencia de caracteres sensivel a localidade.
             * Voce usa essa classe para criar rotinas de busca e classificacao de texto em linguagem
             * natural.
             * Referencias:
             * https://docs.oracle.com/javase/7/docs/api/java/text/Collator.html
             * http://stackoverflow.com/questions/12889760/sort-list-of-strings-with-localization
             * */
            Comanda comanda = (Comanda) o;
            // Collator e uma classe abstrata. Utilize o seu factory para instanciar.
            Collator c = Collator.getInstance();
            // A atribuicao de pontos fortes aos recursos de linguagem depende da regiao.
            c.setStrength(Collator.PRIMARY);
            return c.compare(this.codigoComanda, comanda.getCodigoComanda());
            //usar o compareTo nao ordena corretamente caracteres acentuados
            //return getNome().compareTo(pais.getNome());
        }
    }


}
