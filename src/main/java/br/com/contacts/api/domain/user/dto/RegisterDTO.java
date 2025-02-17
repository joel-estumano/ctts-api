package br.com.contacts.api.domain.user.dto;

import br.com.contacts.api.auth.enums.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {}