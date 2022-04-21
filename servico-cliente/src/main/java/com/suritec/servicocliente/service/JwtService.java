package com.suritec.servicocliente.service;

import com.suritec.servicocliente.model.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {

	String gerarToken(Usuario usuario);
	Claims obterClaims(String token) throws ExpiredJwtException;
	boolean isTokenValido(String token);
	String obterLoginUsuario(String token);
	
}
