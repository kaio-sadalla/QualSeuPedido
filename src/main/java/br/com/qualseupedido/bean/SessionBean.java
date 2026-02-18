package br.com.qualseupedido.bean;

import br.com.qualseupedido.entidade.Usuario;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    private Usuario usuarioLogado;

    // O layout.xhtml chama isso para saber se mostra "Entrar" ou "Sair"
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    // O botão "Sair" do layout chama isso
    public String logout() {
        // Destrói a sessão (limpa carrinho, usuario logado, etc)
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        // Redireciona para home
        return "home.xhtml?faces-redirect=true";
    }
}
