package com.jakarinc.jakar.Domain;


import java.io.Serializable;

public class Imagem implements Serializable {
    private String salaoId;
    private String salaoNome;
    private String url;
    private String descricao;

    public Imagem() {
        salaoId = null;
        salaoNome = null;
        url = null;
    }

    public Imagem(String salaoId, String salaoNome, String url) {
        this.salaoId = salaoId;
        this.salaoNome = salaoNome;
        this.url = url;
        this.descricao = "";
    }

    public Imagem(String salaoId, String salaoNome, String url, String descricao) {
        this.salaoId = salaoId;
        this.salaoNome = salaoNome;
        this.url = url;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(String salaoId) {
        this.salaoId = salaoId;
    }

    public String getSalaoNome() {
        return salaoNome;
    }

    public void setSalaoNome(String salaoNome) {
        this.salaoNome = salaoNome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Imagem{" +
                "salaoId='" + salaoId + '\'' +
                ", salaoNome='" + salaoNome + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Imagem imagem = (Imagem) o;

        if (salaoId != null ? !salaoId.equals(imagem.salaoId) : imagem.salaoId != null)
            return false;
        if (salaoNome != null ? !salaoNome.equals(imagem.salaoNome) : imagem.salaoNome != null)
            return false;
        return url != null ? url.equals(imagem.url) : imagem.url == null;

    }

    @Override
    public int hashCode() {
        int result = salaoId != null ? salaoId.hashCode() : 0;
        result = 31 * result + (salaoNome != null ? salaoNome.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
