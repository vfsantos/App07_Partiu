package br.com.app07_partiu.Model;

public class ItemCardapioGarcomConvertView {

    private String nomeItem;
    private String detalheItem;
    private String valorItem;

    public ItemCardapioGarcomConvertView(String nomeItem, String detalheItem, String valorItem) {
        this.nomeItem = nomeItem;
        this.detalheItem = detalheItem;
        this.valorItem = valorItem;
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
