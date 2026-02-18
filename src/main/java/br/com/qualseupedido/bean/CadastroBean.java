package br.com.qualseupedido.bean;

import br.com.qualseupedido.entidade.Chef;
import br.com.qualseupedido.entidade.Cliente;
import br.com.qualseupedido.entidade.Usuario;
import org.primefaces.model.file.UploadedFile;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

// VOLTANDO PARA O CLÁSSICO (Seguro contra conflito de versões)
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name = "cadastroBean")
@ViewScoped
public class CadastroBean implements Serializable {

    private Usuario usuario = new Usuario();

    // Inicializando para evitar NullPointerException
    private Chef chefInfo = new Chef();

    private String tipoUsuario;
    private String confirmacaoSenha;
    private UploadedFile arquivoFoto;

    public void cadastrar() {
        System.out.println("--- INICIANDO CADASTRO ---"); // Debug no console

        if (usuario.getSenha() != null && !usuario.getSenha().equals(confirmacaoSenha)) {
            adicionarErro("As senhas não coincidem!");
            return;
        }

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("QualSeuPedidoPU");
            em = emf.createEntityManager();

            String fotoBase64 = null;

            // Lógica de Validação da Imagem
            if (arquivoFoto != null && arquivoFoto.getContent() != null && arquivoFoto.getContent().length > 0) {
                byte[] conteudo = arquivoFoto.getContent();
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(conteudo));

                if (img != null) {
                    if (img.getWidth() > 2000 || img.getHeight() > 2000) { // Aumentei um pouco a tolerância
                        adicionarErro("A imagem é muito grande! Use uma menor que 2000px.");
                        return;
                    }
                }
                fotoBase64 = Base64.getEncoder().encodeToString(conteudo);
            }

            em.getTransaction().begin();

            if ("CHEF".equals(tipoUsuario)) {
                // Copia os dados do usuario genérico para o objeto Chef
                chefInfo.setNome(usuario.getNome());
                chefInfo.setEmail(usuario.getEmail());
                chefInfo.setSenha(usuario.getSenha());
                chefInfo.setFotoBase64(fotoBase64);

                // A especialidade já foi preenchida pelo JSF via Setters
                em.persist(chefInfo);
            } else {
                Cliente cliente = new Cliente();
                cliente.setNome(usuario.getNome());
                cliente.setEmail(usuario.getEmail());
                cliente.setSenha(usuario.getSenha());
                cliente.setFotoBase64(fotoBase64);
                em.persist(cliente);
            }

            em.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cadastro realizado!"));

            limparFormulario();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            adicionarErro("Erro interno: " + e.getMessage());
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    private void limparFormulario() {
        this.usuario = new Usuario();
        this.chefInfo = new Chef();
        this.tipoUsuario = null;
        this.arquivoFoto = null;
        this.confirmacaoSenha = null;
    }

    private void adicionarErro(String resumo) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", resumo));
        FacesContext.getCurrentInstance().validationFailed();
    }

    // --- GETTERS E SETTERS ---

    // AQUI ESTAVA O PROBLEMA: Faltava o getChefInfo para o JSF ler
    public Chef getChefInfo() {
        return chefInfo;
    }

    public void setChefInfo(Chef chefInfo) {
        this.chefInfo = chefInfo;
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getConfirmacaoSenha() { return confirmacaoSenha; }
    public void setConfirmacaoSenha(String confirmacaoSenha) { this.confirmacaoSenha = confirmacaoSenha; }

    public UploadedFile getArquivoFoto() { return arquivoFoto; }
    public void setArquivoFoto(UploadedFile arquivoFoto) { this.arquivoFoto = arquivoFoto; }
}