package br.com.app07_partiu.Model;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;


public class Estabelecimento implements Serializable {
    private int id;
    private String nome;
    private String imagem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}

