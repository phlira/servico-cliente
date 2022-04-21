package com.suritec.servicocliente.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suritec.servicocliente.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByLogin(String login);
}
