package br.com.contacts.api.domain.contact.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class ContactDTO {
    private String contatoId;
    private String nome;
    private String email;
    private String celular;
    private String telefone;
    private String snFavorito;
    private String snAtivo;
    private LocalDateTime dhCad;
    private String userId;
}
