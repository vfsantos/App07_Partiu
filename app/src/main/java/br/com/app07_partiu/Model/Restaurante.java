package br.com.app07_partiu.Model;

import java.io.Serializable;

public class Restaurante implements Serializable {

    private String cnpj;
    //	private int dia;
    private int qtdMesas;
    private String codigoComanda;
    private String razaoSocial;
    private String nomeFantasia;
    private String status;
    private String logo;
    private String descricao;
    private String hrEntrada;
    private String hrFim;
    private String especialidade;
    private double avaliacao;
    private Endereco endereco;

    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    //	public int getDia() {
//		return dia;
//	}
    public String getHrEntrada() {
        return hrEntrada;
    }
    public String getHrFim() {
        return hrFim;
    }
    //	public void setDia(int dia) {
//		this.dia = dia;
//	}
    public void setHrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
    }
    public void setHrFim(String hrFim) {
        this.hrFim = hrFim;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getCodigoComanda() {
        return codigoComanda;
    }
    public void setCodigoComanda(String codigoComanda) {
        this.codigoComanda = codigoComanda;
    }
    public String getRazaoSocial() {
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public int getQtdMesas() {
        return qtdMesas;
    }
    public void setQtdMesas(int qtdMesas) {
        this.qtdMesas = qtdMesas;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Restaurante() {
    }

    @Override
    public String toString() {
        String logoTemp, descrTemp;

        if (logo!=null) logoTemp = "codigo_logo_gigante";
        else logoTemp = "codigo_inexistente";

        return "Restaurante{" +
                "cnpj=" + cnpj +
                ", qtdMesas=" + qtdMesas +
                ", codigoComanda='" + codigoComanda + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", status='" + status + '\'' +
                ", logo='" + logoTemp + '\'' +
                ", descricao='" + descricao + '\'' +
                ", hrEntrada='" + hrEntrada + '\'' +
                ", hrFim='" + hrFim + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}