package br.com.qualseupedido.entidade;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    private String nome;
    private Double preco;

    public ItemPedido(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}