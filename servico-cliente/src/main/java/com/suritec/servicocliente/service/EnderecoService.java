package com.suritec.servicocliente.service;

import java.util.Optional;

import com.suritec.servicocliente.model.entity.Endereco;

public interface EnderecoService{
	
	Endereco salvar(Endereco endereco);
	Endereco atualizar(Endereco endereco);
	void deletar(Long id);
	Optional<Endereco> obterPorId(Long idEndereco);

}
