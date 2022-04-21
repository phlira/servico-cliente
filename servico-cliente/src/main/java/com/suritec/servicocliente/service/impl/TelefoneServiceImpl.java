package com.suritec.servicocliente.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.suritec.servicocliente.model.entity.Telefone;
import com.suritec.servicocliente.model.repository.TelefoneRepository;
import com.suritec.servicocliente.service.TelefoneService;

@Service
public class TelefoneServiceImpl implements TelefoneService{

	private TelefoneRepository repository;
	
	@Override
	public List<Telefone> salvar(List<Telefone> telefones) {
		return repository.saveAll(telefones);
	}

	@Override
	public List<Telefone> atualizar(List<Telefone> telefones) {
		Objects.requireNonNull(telefones.size() > 0);
		return repository.saveAll(telefones);
	}

	@Override
	public void deletar(Long id) {
		Objects.requireNonNull(id);
		repository.deleteById(id);
	}

	@Override
	public Optional<Telefone> obterPorId(Long idTelefone) {
		return repository.findById(idTelefone);
	}

}