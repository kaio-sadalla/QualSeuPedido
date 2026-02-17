package br.com.qualseupedido.entidade;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Pedido implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String status; // "AGUARDANDO", "ACEITO", "RECUSADO"

    // Construtores
    public Pedido() {}

    public Pedido(String descricao, String status) {
        this.descricao = descricao;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}