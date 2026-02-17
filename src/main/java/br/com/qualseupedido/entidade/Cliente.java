package br.com.qualseupedido.entidade;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="idUsuario")
public class Cliente extends Usuario {
    // Campos específicos do cliente virão aqui futuramente
}