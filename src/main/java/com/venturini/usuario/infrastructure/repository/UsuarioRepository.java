package com.venturini.usuario.infrastructure.repository;

import com.venturini.usuario.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    // Evita pegar informações nulas
    Optional<Usuario> findByEmail(String email);

    // deleta sem ter conflitos
    @Transactional
    void deleteByEmail(String email);
}
