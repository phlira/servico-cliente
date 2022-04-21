package com.suritec.servicocliente.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suritec.servicocliente.api.dto.ClienteDTO;
import com.suritec.servicocliente.exception.RegraNegocioException;
import com.suritec.servicocliente.model.entity.Cliente;
import com.suritec.servicocliente.model.entity.Usuario;
import com.suritec.servicocliente.service.ClienteService;
import com.suritec.servicocliente.service.UsuarioService;
import com.suritec.servicocliente.util.Utilitarios;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/clientes")
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes","unchecked"})
public class ClienteController {

	private final ClienteService service;
	private final UsuarioService uService;
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf
			) {
		String cpfFormatado = Utilitarios.retiraMascaraCpf(cpf);
		List<Cliente> clientes = service.buscar(Cliente.builder().nome(nome).cpf(cpfFormatado).build());
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("{id}")
	public ResponseEntity buscarPorId(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = service.obterPorId(id);
		if (!cliente.isPresent()) {
			return ResponseEntity.badRequest().body("Cliente não encontrado");
		}
		return ResponseEntity.ok(cliente.get());
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
		Optional<Usuario> usuario = uService.obterPorId(dto.getIdUsuario());
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Faltou enviar o Usuário");
		}
		return service.obterPorId(id).map(entity -> {
			try {
				Cliente cliente = Cliente.converter(dto, usuario.get());
				cliente.setId(entity.getId());
				service.atualizar(cliente);
				return ResponseEntity.ok(cliente);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Cliente não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		try {
			service.deletar(id);
			return ResponseEntity.ok("Cliente deletado com sucesso.");
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().body("Informe um id de cliente válido para excluir");
		}
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity salvar(@RequestBody ClienteDTO dto) {
		try {
			Optional<Usuario> usuario = uService.obterPorId(dto.getIdUsuario());
			if(!usuario.isPresent()) {
				return ResponseEntity.badRequest().body("Faltou enviar o Usuário");
			}
			Cliente cliente = Cliente.converter(dto, usuario.get());
			cliente = service.salvar(cliente);
			return ResponseEntity.ok(cliente);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
