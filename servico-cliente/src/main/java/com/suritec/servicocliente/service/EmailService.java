package com.suritec.servicocliente.service;

import java.util.List;
import java.util.Optional;

import com.suritec.servicocliente.model.entity.Email;

public interface EmailService {

	 List<Email> salvar( List<Email> email);
	 List<Email> atualizar( List<Email> email);
	void deletar(Long id);
	Optional<Email> obterPorId(Long idEmail);	
}
