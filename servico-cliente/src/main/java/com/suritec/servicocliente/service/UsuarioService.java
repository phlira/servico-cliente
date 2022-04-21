package com.suritec.servicocliente.service;

import java.util.Optional;

import com.suritec.servicocliente.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar (String login, String senha);
	
	Optional<Usuario> obterPorId(Long id);
	
}
