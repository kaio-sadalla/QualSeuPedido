package br.com.qualseupedido.bean;

import br.com.qualseupedido.entidade.Chef;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.*;

@Named
@ViewScoped
public class HomeBean implements Serializable {

    private List<Chef> chefsDestaque = new ArrayList<>();

    // APENAS DECLARE. Não instancie aqui na "raiz".
    private EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        System.out.println(">>> Iniciando HomeBean...");
        try {
            // No Tomcat 9, precisamos garantir que o Hibernate seja carregado
            this.emf = Persistence.createEntityManagerFactory("QualSeuPedidoPU");
            System.out.println(">>> Conectado ao PU com sucesso!");
        } catch (Exception e) {
            System.err.println(">>> ERRO NO JPA DENTRO DO INIT:");
            e.printStackTrace(); // AGORA o erro vai aparecer no log
        }
    }

    public List<Chef> getChefsDestaque() { // Com S
        return chefsDestaque;
    }
}