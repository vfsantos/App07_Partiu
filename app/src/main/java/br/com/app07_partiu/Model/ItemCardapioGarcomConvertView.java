package br.com.app07_partiu.Model;

import java.io.Serializable;

public class ItemCardapioGarcomConvertView implements Serializable {

    private int id;
    private String nomeItem;
    private String detalheItem;
    private String valorItem;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getDetalheItem() {
        return detalheItem;
    }

    public void setDetalheItem(String detalheItem) {
        this.detalheItem = detalheItem;
    }

    public String getValorItem() {
        return valorItem;
    }

    public void setValorItem(String valorItem) {
        this.valorItem = valorItem;
    }
}
