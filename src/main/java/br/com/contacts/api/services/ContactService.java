package br.com.contacts.api.services;

import br.com.contacts.api.domain.contact.Contact;
import br.com.contacts.api.domain.contact.dto.ContactCreateDTO;
import br.com.contacts.api.domain.contact.dto.ContactDTO;
import br.com.contacts.api.domain.contact.dto.ContactUpdateDTO;
import br.com.contacts.api.domain.user.User;
import br.com.contacts.api.repositories.ContactRepository;
import br.com.contacts.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public ContactDTO cadastrarContato(ContactCreateDTO contactCreateDTO) {
        if (contactRepository.findByCelular(contactCreateDTO.getCelular()).isPresent()) {
            throw new RuntimeException("Contato já cadastrado com este número de celular.");
        }

        Optional<User> optionalUser = userRepository.findById(contactCreateDTO.getUserId());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        User user = optionalUser.get();
        Contact contact = new Contact(contactCreateDTO.getNome(), contactCreateDTO.getEmail(),
                contactCreateDTO.getCelular(), contactCreateDTO.getTelefone(), "N", "S", LocalDateTime.now(), user);
        contact = contactRepository.save(contact);
        return convertToDTO(contact);
    }

    // private static final String TEST_USER_ID = "a5ac2f19-32fe-4f61-ad4a-467cf9cbe08c"; // User ID constante para testes

    public List<ContactDTO> listarContatos(String search, String userId) {
        if (search == null || search.isEmpty()) {
            return contactRepository.findByUserIdOrderByNomeAsc(userId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return contactRepository.findByUserIdAndNomeContainingIgnoreCaseOrderByNomeAsc(userId, search).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public ContactDTO atualizarContato(String id, ContactUpdateDTO contactUpdateDTO) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contactExistente = optionalContact.get();
            contactExistente.setNome(contactUpdateDTO.getNome());
            contactExistente.setEmail(contactUpdateDTO.getEmail());
            contactExistente.setCelular(contactUpdateDTO.getCelular());
            contactExistente.setTelefone(contactUpdateDTO.getTelefone());
            contactExistente = contactRepository.save(contactExistente);
            return convertToDTO(contactExistente);
        } else {
            throw new RuntimeException("Contato não encontrado.");
        }
    }

    public ContactDTO ativarContato(String id, String snAtivo) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            contact.setSnAtivo(snAtivo);
            contact = contactRepository.save(contact);
            return convertToDTO(contact);
        } else {
            throw new RuntimeException("Contato não encontrado.");
        }
    }

    public ContactDTO favoritoContato(String id, String snFavorito) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            contact.setSnFavorito(snFavorito);
            contact = contactRepository.save(contact);
            return convertToDTO(contact);
        } else {
            throw new RuntimeException("Contato não encontrado.");
        }
    }

    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContatoId(contact.getContatoId());
        contactDTO.setNome(contact.getNome());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setCelular(contact.getCelular());
        contactDTO.setTelefone(contact.getTelefone());
        contactDTO.setSnFavorito(contact.getSnFavorito());
        contactDTO.setSnAtivo(contact.getSnAtivo());
        contactDTO.setDhCad(contact.getDhCad());
        contactDTO.setUserId(contact.getUser().getId());
        return contactDTO;
    }

    public void removerContato(String id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            contactRepository.deleteById(id);
        } else {
            throw new RuntimeException("Contato não encontrado.");
        }
    }

    public boolean verificarContatoExistente(String celular) {
        return contactRepository.findByCelular(celular).isPresent();
    }

}
