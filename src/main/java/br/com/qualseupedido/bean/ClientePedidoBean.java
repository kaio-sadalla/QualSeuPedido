package br.com.qualseupedido.bean;

import br.com.qualseupedido.entidade.ItemPedido; // Usamos a classe nova
import br.com.qualseupedido.entidade.Pedido;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped; // Escopo de Sessão (mantém carrinho)
import javax.inject.Named;

@Named
@SessionScoped
public class ClientePedidoBean implements Serializable {

    // Lista de itens no carrinho (memória)
    private List<ItemPedido> carrinho = new ArrayList<>();

    private Pedido pedidoAtual;
    private boolean pedidoEnviado = false;
    private boolean pedidoAceito = false; // Controle para o chat

    // Adiciona ao carrinho (chamado pelo botão "Selecionar" no xhtml)
    public void adicionar(String nomePrato, Double preco) {
        carrinho.add(new ItemPedido(nomePrato, preco));
    }

    // Calcula o total dinamicamente
    public Double getTotal() {
        if (carrinho.isEmpty()) return 0.0;

        double soma = 0.0;
        for (ItemPedido item : carrinho) {
            if(item.getPreco() != null) {
                soma += item.getPreco();
            }
        }
        return soma;
    }

    // Finaliza o pedido
    public void enviarPedido() {
        if (carrinho.isEmpty()) return;

        // Cria o pedido
        String resumo = "Pedido com " + carrinho.size() + " itens. Total: R$ " + getTotal();
        this.pedidoAtual = new Pedido(resumo, "AGUARDANDO");

        // AQUI VOCÊ SALVARIA NO BANCO DE DADOS (JPA)
        // ex: dao.salvar(pedidoAtual);

        this.pedidoEnviado = true;

        // Limpa o carrinho após enviar
        this.carrinho.clear();
    }

    // Simula a verificação se o chef aceitou (botão "Atualizar Status")
    public void atualizarStatus() {
        if (pedidoAtual != null) {
            // Lógica simulada. Na prática, você consultaria o banco para ver se o status mudou
            // pedidoAtual = dao.buscarPorId(pedidoAtual.getId());

            // Se o status fosse mudado pelo ChefBean para "ACEITO":
            if ("ACEITO".equalsIgnoreCase(pedidoAtual.getStatus())) {
                this.pedidoAceito = true;
            }
        }
    }

    // Getters e Setters obrigatórios
    public List<ItemPedido> getCarrinho() { return carrinho; }
    public void setCarrinho(List<ItemPedido> carrinho) { this.carrinho = carrinho; }

    public Pedido getPedidoAtual() { return pedidoAtual; }
    public void setPedidoAtual(Pedido pedidoAtual) { this.pedidoAtual = pedidoAtual; }

    public boolean isPedidoEnviado() { return pedidoEnviado; }
    public void setPedidoEnviado(boolean pedidoEnviado) { this.pedidoEnviado = pedidoEnviado; }

    public boolean isPedidoAceito() { return pedidoAceito; }
}