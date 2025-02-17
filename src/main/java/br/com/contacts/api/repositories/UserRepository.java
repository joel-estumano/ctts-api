package br.com.contacts.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.contacts.api.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByEmail(String email);
}
