package com.suritec.servicocliente.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.suritec.servicocliente.model.entity.Endereco;
import com.suritec.servicocliente.model.repository.EnderecoRepository;
import com.suritec.servicocliente.service.EnderecoService;

@Service
public class EnderecoServiceImpl implements EnderecoService{

	private EnderecoRepository repository;
	
	@Override
	public Endereco salvar(Endereco endereco) {
		return repository.save(endereco);
	}

	@Override
	public Endereco atualizar(Endereco endereco) {
		Objects.requireNonNull(endereco.getId());
		return repository.save(endereco);
	}

	@Override
	public void deletar(Long id) {
		Objects.requireNonNull(id);
		repository.deleteById(id);
	}

	@Override
	public Optional<Endereco> obterPorId(Long idEndereco) {
		return repository.findById(idEndereco);
	}

}
