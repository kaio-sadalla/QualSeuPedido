package br.com.qualseupedido.entidade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="idUsuario")
public class Chef extends Usuario { // Extende Usuario (ver abaixo)

    private String especialidade;

    // Relacionamento: Um Chef tem vários Pratos
    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prato> cardapio = new ArrayList<>();

    public Chef() {}

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public List<Prato> getCardapio() { return cardapio; }
    public void setCardapio(List<Prato> cardapio) { this.cardapio = cardapio; }
}
