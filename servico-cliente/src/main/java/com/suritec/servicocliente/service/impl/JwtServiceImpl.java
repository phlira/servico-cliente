package com.suritec.servicocliente.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.suritec.servicocliente.model.entity.Usuario;
import com.suritec.servicocliente.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService{

	@Value("${jwt.expiracao}")
	private String expiracao;
	@Value("${jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	@Override
	public String gerarToken(Usuario usuario) {
		long exp = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
		Instant instant = dataHoraExpiracao.atZone( ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);

		return Jwts
					.builder()
					.setExpiration(data)
					.setSubject(usuario.getLogin())
					.claim("nome", usuario.getNome())
					.claim("idUsuario", usuario.getId())
					.claim("role", usuario.getRole())
					.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
					.compact();
	}

	@Override
	public Claims obterClaims(String token) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}

	@Override
	public boolean isTokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			Date expiration = claims.getExpiration();
			LocalDateTime dataHoraExpiracao = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(dataHoraExpiracao);
		} catch (ExpiredJwtException e) {
			return false;
		}
	}

	@Override
	public String obterLoginUsuario(String token) {
		Claims obterClaims = obterClaims(token);
		return obterClaims.getSubject();
	}

}
