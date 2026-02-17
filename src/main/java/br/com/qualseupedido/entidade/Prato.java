package br.com.qualseupedido.entidade;

import java.io.Serializable;
import javax.persistence.*; // Tomcat 9 usa javax

@Entity
public class Prato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(length = 500)
    private String descricao;

    private Double preco; // Usando Double para facilitar o JSF

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    // Construtor vazio
    public Prato() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Chef getChef() { return chef; }
    public void setChef(Chef chef) { this.chef = chef; }
}
