package br.com.contacts.api.domain.contact;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.contacts.api.domain.user.User;
import java.time.LocalDateTime;

@Table(name = "coontato")
@Entity(name = "coontato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "contatoId")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String contatoId;
    
    @NotNull
    @Column(name = "contato_nome", length = 100)
    private String nome;
    
    @NotNull
    @Column(name = "contato_email", length = 255)
    private String email;
    
    @NotNull
    @Column(name = "contato_celular", length = 11)
    private String celular;
    
    @Column(name = "contato_telefone", length = 10)
    private String telefone;
    
    @Column(name = "contato_sn_favorito", length = 1)
    private String snFavorito;
    
    @Column(name = "contato_sn_ativo", length = 1)
    private String snAtivo;
    
    @Column(name = "contato_dh_cad")
    private LocalDateTime dhCad;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Contact(String nome, String email, String celular, String telefone, String snFavorito, String snAtivo, LocalDateTime dhCad, User user) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.telefone = telefone;
        this.snFavorito = snFavorito;
        this.snAtivo = snAtivo;
        this.dhCad = dhCad;
        this.user = user;
    }
}
