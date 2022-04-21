package com.suritec.servicocliente.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suritec.servicocliente.api.dto.TokenDTO;
import com.suritec.servicocliente.api.dto.UsuarioDTO;
import com.suritec.servicocliente.exception.ErroAutenticacaoException;
import com.suritec.servicocliente.model.entity.Usuario;
import com.suritec.servicocliente.service.JwtService;
import com.suritec.servicocliente.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/usuarios")
@RequiredArgsConstructor
@SuppressWarnings({ "rawtypes" })
public class UsuarioController {
	
	private final UsuarioService service;
	private final JwtService jwtService;
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getLogin(), dto.getSenha());
			String token = jwtService.gerarToken(usuarioAutenticado);
			TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getNome(), token);
			return ResponseEntity.ok(tokenDTO);
		} catch (ErroAutenticacaoException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
	

}
