package br.com.contacts.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.contacts.api.domain.contact.dto.ContactCreateDTO;
import br.com.contacts.api.domain.contact.dto.ContactDTO;
import br.com.contacts.api.domain.contact.dto.ContactUpdateActiveDTO;
import br.com.contacts.api.domain.contact.dto.ContactUpdateDTO;
import br.com.contacts.api.domain.contact.dto.ContactUpdateFavoritDTO;
import br.com.contacts.api.services.ContactService;
import br.com.contacts.api.exception.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<?> cadastrarContato(@RequestBody @Valid ContactCreateDTO contactCreateDTO) {
        try {
            if (contactService.verificarContatoExistente(contactCreateDTO.getCelular())) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.CONFLICT.value(),
                        "Contato já cadastrado com este número de celular.",
                        "Celular: " + contactCreateDTO.getCelular(), "/contatos");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
            }
            ContactDTO novoContact = contactService.cadastrarContato(contactCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoContact);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Erro ao cadastrar contato",
                    e.getMessage(), "/contatos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ContactDTO>> listarContatos(@PathVariable String userId,
            @RequestParam(value = "search", required = false) String search) {
        List<ContactDTO> contatos = contactService.listarContatos(search, userId);
        return ResponseEntity.ok(contatos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarContato(@PathVariable String id,
            @RequestBody @Valid ContactUpdateDTO contactUpdateDTO) {
        try {
            ContactDTO contatoAtualizado = contactService.atualizarContato(id, contactUpdateDTO);
            return ResponseEntity.ok(contatoAtualizado);
        } catch (EntityNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "Contato não encontrado",
                    e.getMessage(), "/contatos/" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Erro ao atualizar contato",
                    e.getMessage(), "/contatos/" + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<?> ativarContato(@PathVariable String id, @RequestBody ContactUpdateActiveDTO activeDTO) {
        try {
            String snAtivo = activeDTO.getSnAtivo();
            ContactDTO contatoAtualizado = contactService.ativarContato(id, snAtivo);
            return ResponseEntity.ok(contatoAtualizado);
        } catch (EntityNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "Contato não encontrado",
                    e.getMessage(), "/contatos/" + id + "/ativar");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (IllegalArgumentException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Parâmetro inválido",
                    e.getMessage(), "/contatos/" + id + "/ativar");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Erro ao atualizar contato",
                    e.getMessage(), "/contatos/" + id + "/ativar");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
    }

    @PatchMapping("/{id}/favorito")
    public ResponseEntity<?> favoritoContato(@PathVariable String id,
            @RequestBody ContactUpdateFavoritDTO favoritoDTO) {
        try {
            String snFavorito = favoritoDTO.getSnFavorito();
            ContactDTO contatoAtualizado = contactService.favoritoContato(id, snFavorito);
            return ResponseEntity.ok(contatoAtualizado);
        } catch (EntityNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "Contato não encontrado",
                    e.getMessage(), "/contatos/" + id + "/favorito");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (IllegalArgumentException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Parâmetro inválido",
                    e.getMessage(), "/contatos/" + id + "/favorito");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Erro ao atualizar contato",
                    e.getMessage(), "/contatos/" + id + "/favorito");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerContato(@PathVariable String id) {
        try {
            contactService.removerContato(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "Contato não encontrado",
                    e.getMessage(), "/contatos/" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Erro ao remover contato",
                    e.getMessage(), "/contatos/" + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
    }
}
