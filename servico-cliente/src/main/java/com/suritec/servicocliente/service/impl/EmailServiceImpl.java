package com.suritec.servicocliente.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.suritec.servicocliente.model.entity.Email;
import com.suritec.servicocliente.model.repository.EmailRepository;
import com.suritec.servicocliente.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	private EmailRepository repository;
	
	@Override
	public List<Email> salvar(List<Email> email) {
		return repository.saveAll(email);
	}

	@Override
	public List<Email> atualizar(List<Email> email) {
		Objects.requireNonNull(email.size() > 0);
		return repository.saveAll(email);
	}

	@Override
	public void deletar(Long id) {
		Objects.requireNonNull(id);
		repository.deleteById(id);
	}

	@Override
	public Optional<Email> obterPorId(Long idEmail) {
		return repository.findById(idEmail);
	}
}
