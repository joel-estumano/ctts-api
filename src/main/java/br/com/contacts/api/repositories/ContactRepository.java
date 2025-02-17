package br.com.contacts.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contacts.api.domain.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, String> {
    Optional<Contact> findByCelular(String celular);

    List<Contact> findByUserId(String userId);
    List<Contact> findByUserIdAndNomeContainingIgnoreCase(String userId, String nome);  
}

