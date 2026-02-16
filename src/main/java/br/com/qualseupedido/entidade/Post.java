package br.com.qualseupedido.entidade;

import javax.persistence.*; // Ou javax.persistence em versões mais antigas

import javax.persistence.TemporalType;
import java.io.Serializable;

@Entity
@Table(name = "tab_menu") // Observar que não é o nome da classe!

public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento
    private Long id;

    @Temporal(TemporalType.DATE)
    private String date;

    @Column(columnDefinition = "TEXT")
    private String body;

    /*E ainda preciso de uma para guardar uma lista de arquivos codificados em base64*/

    // 1. Construtor vazio (obrigatório pela JPA)
    public void post() {
    }

    // 2. Getters e Setters (necessários para o JSF acessar os dados)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ... continue com os outros getters e setters
}