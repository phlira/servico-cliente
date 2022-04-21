package com.suritec.servicocliente.service;

import java.util.List;
import java.util.Optional;

import com.suritec.servicocliente.model.entity.Cliente;

public interface ClienteService {

	Cliente salvar(Cliente cliente);
	Cliente atualizar(Cliente cliente);
	void deletar(Long id);
	List<Cliente> buscar(Cliente cliente);
	Optional<Cliente> obterPorId(Long id);
	
}
