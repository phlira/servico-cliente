package com.suritec.servicocliente.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suritec.servicocliente.exception.ErroAutenticacaoException;
import com.suritec.servicocliente.model.entity.Usuario;
import com.suritec.servicocliente.model.repository.UsuarioRepository;
import com.suritec.servicocliente.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public Usuario autenticar(String login, String senha) {
		Optional<Usuario> usuario = repository.findByLogin(login);
		
		if (!usuario.isPresent()) {
			throw new ErroAutenticacaoException("Usuário não encontrado para o Login informado");
		}
		
		if (!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacaoException("Senha inválida.");
		}
		
		return usuario.get();
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

}
