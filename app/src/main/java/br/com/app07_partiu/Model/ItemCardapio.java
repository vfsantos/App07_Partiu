package br.com.app07_partiu.Model;
//Lista os items do da comanda selecionada pelo  garcom

public class ItemCardapio {

    private String item;
    private String preco;
    private String quantidade;
    private String status;


    public ItemCardapio(String item, String  preco, String quantidade, String status) {

        this.item = item;
        this.preco = preco;
        this.quantidade = quantidade;
        this.status = status;
    }


    public void setItem(String item){
        this.item=item;
    }

    public void setPreco(String preco){
        this.preco = preco;
    }

    public void setQuantidade(String quantidade){
        this.quantidade = quantidade;
    }

    public void setStatus(String status){
        this.status = status;
    }


    public String getItem(){
        return item;
    }

    public String getPreco(){
        return preco;
    }

    public String getQuantidade(){
        return quantidade;
    }

    public String getStatus(){
        return status;
    }

}