package com.suritec.servicocliente.service;

import java.util.List;
import java.util.Optional;

import com.suritec.servicocliente.model.entity.Telefone;

public interface TelefoneService {

	List<Telefone> salvar(List<Telefone> telefone);
	List<Telefone> atualizar(List<Telefone> telefone);
	void deletar(Long id);
	Optional<Telefone> obterPorId(Long idTelefone);
	
}
