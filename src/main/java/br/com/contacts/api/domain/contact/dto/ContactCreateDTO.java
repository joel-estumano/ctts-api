package br.com.contacts.api.domain.contact.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class ContactCreateDTO {
    private String nome;
    private String email;
    private String celular;
    private String telefone;
    private String userId;
}
