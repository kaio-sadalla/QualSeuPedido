package br.com.qualseupedido.bean;

import br.com.qualseupedido.entidade.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private String email;
    private String senha;

    @Inject
    private SessionBean sessionBean;

    private EntityManagerFactory emf;

    private EntityManagerFactory getEmf() {
        if (this.emf == null) {
            // Se ele for nulo, tentamos criar. Se falhar aqui, o erro será específico.
            this.emf = Persistence.createEntityManagerFactory("QualSeuPedidoPU");
        }
        return this.emf;
    }

    public String logar() {
        EntityManager em = null;

        try {
            // 1. TENTATIVA DE CONEXÃO AGORA ESTÁ PROTEGIDA
            // Se o banco falhar aqui, ele cai no catch (Exception) lá embaixo
            em = getEmf().createEntityManager();

            // 2. Busca do usuário
            Usuario usuario = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();

            // 3. Sucesso
            sessionBean.setUsuarioLogado(usuario);
            return "home?faces-redirect=true";

        } catch (NoResultException e) {
            // CASO 1: Conectou, mas senha está errada
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "E-mail ou senha incorretos."));
            // Retornar null mantém na mesma página e atualiza o dialog
            return null;

        } catch (Exception e) {
            // CASO 2: Banco fora do ar, erro de rede, ou qualquer outro erro
            e.printStackTrace(); // Mostra o erro no console do servidor para você ver

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro no Sistema",
                            "Não foi possível conectar ao banco de dados."));

            // Retornar null mantém o dialog aberto para mostrar a mensagem vermelha
            return null;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
