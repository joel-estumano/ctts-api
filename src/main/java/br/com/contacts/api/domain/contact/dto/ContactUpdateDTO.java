package br.com.contacts.api.domain.contact.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class ContactUpdateDTO {
    String nome;
    String email;
    String celular;
    String telefone;
}
