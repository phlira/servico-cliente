package com.suritec.servicocliente.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.suritec.servicocliente.model.entity.Usuario;
import com.suritec.servicocliente.model.repository.UsuarioRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

	private UsuarioRepository repositorio;
	
	public SecurityUserDetailsService(UsuarioRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuarioEncontrado = repositorio
											.findByLogin(login)
											.orElseThrow(() -> new UsernameNotFoundException("Login não encontrado"));
		return User.builder()
						.username(usuarioEncontrado.getLogin())
						.password(usuarioEncontrado.getSenha())
						.roles(usuarioEncontrado.getRole())
						.build();
	}

}