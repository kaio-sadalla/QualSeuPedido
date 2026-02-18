package br.com.qualseupedido.bean;

import br.com.qualseupedido.entidade.Prato; // Você precisará criar a entidade Prato simples
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class PratoBean implements Serializable {

    private String nome;
    private String descricao;
    private Double preco; // Usando Double para simplificar, mas BigDecimal é melhor

    private List<Prato> pratos = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Carregar pratos do banco aqui se necessário
    }

    public void adicionarPrato() {
        // Lógica simples para testar visualmente
        Prato p = new Prato();
        p.setNome(this.nome);
        p.setDescricao(this.descricao);
        p.setPreco(this.preco); // Certifique-se que sua entidade Prato tem Double ou converta

        pratos.add(p);

        // Limpar campos
        this.nome = "";
        this.descricao = "";
        this.preco = null;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    public List<Prato> getPratos() { return pratos; }
}