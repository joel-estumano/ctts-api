package br.com.contacts.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.com.contacts.api.auth.TokenService;
import br.com.contacts.api.domain.user.User;
import br.com.contacts.api.domain.user.dto.AuthenticationDTO;
import br.com.contacts.api.domain.user.dto.LoginResponseDTO;
import br.com.contacts.api.domain.user.dto.RegisterDTO;
import br.com.contacts.api.exception.ErrorDetails;
import br.com.contacts.api.repositories.UserRepository;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", e.getMessage(), "/auth/login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        } catch (DisabledException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", e.getMessage(), "/auth/login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", e.getMessage(), "/auth/login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Email já cadastrado", "Email: " + data.email(), "/auth/register");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword);
        this.repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
