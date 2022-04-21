package com.suritec.servicocliente.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suritec.servicocliente.exception.RegraNegocioException;
import com.suritec.servicocliente.model.entity.Cliente;
import com.suritec.servicocliente.model.entity.Endereco;
import com.suritec.servicocliente.model.entity.Telefone;
import com.suritec.servicocliente.model.repository.ClienteRepository;
import com.suritec.servicocliente.model.repository.EmailRepository;
import com.suritec.servicocliente.model.repository.TelefoneRepository;
import com.suritec.servicocliente.service.ClienteService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private TelefoneRepository telRepository;
	@Autowired
	private EmailRepository emailRepository;
	
	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {
		cliente.setDataCadastro(LocalDate.now());
		validar(cliente);
		validarSeClienteExiste(cliente);
		return repository.save(cliente); 
	}

	private void validarSeClienteExiste(Cliente cliente) {
		boolean existe = repository.existsByCpf(cliente.getCpf());
		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este CPF.");
		}
	}

	@Override
	@Transactional
	public Cliente atualizar(Cliente cliente) {
		Objects.requireNonNull(cliente.getId());
		validar(cliente);
		Cliente recuperado = repository.getById(cliente.getId());
		recuperado.setDataAtualizacao(LocalDate.now());
		recuperado = atualizar(recuperado, cliente);
		return repository.save(recuperado);
	}

	private Cliente atualizar(Cliente recuperado, Cliente cliente) {
		recuperado.setNome(cliente.getNome());
		recuperado.setEndereco(Endereco.atualizarEndereco(recuperado.getEndereco(), cliente.getEndereco()));
		telRepository.deleteAll(recuperado.getTelefone());
		recuperado.setTelefone(cliente.getTelefone());
		emailRepository.deleteAll(recuperado.getEmail());
		recuperado.setEmail(cliente.getEmail());
		return recuperado;
	}

	@Override
	@Transactional
	public void deletar(Long id) {
		Objects.requireNonNull(id);
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscar(Cliente clienteFiltro) {
		Example example = Example
								.of(clienteFiltro, 
									ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(StringMatcher.CONTAINING)
									);
		return repository.findAll(example);
	}

	@Override
	public Optional<Cliente> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	private void validar(Cliente cliente) {
		if (cliente.getNome() == null || cliente.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um nome válido");
		}
		if (cliente.getNome().length() < 3 || cliente.getNome().length() > 100) {
			throw new RegraNegocioException("O campo nome não pode ter menos que 3 caracteres e não pode ultrapassar 100 caracteres");
		}
		if (cliente.getCpf() == null || cliente.getCpf().trim().equals("")) {
			throw new RegraNegocioException("Informe um CPF válido");
		}
		if (cliente.getEndereco() == null) {
			throw new RegraNegocioException("Informe o Endereço.");
		} else {
			validarEndereco(cliente.getEndereco());
		}
		if (cliente.getTelefone() == null || cliente.getTelefone().isEmpty()) {
			throw new RegraNegocioException("Informe pelo menos 1 telefone válido");
		} else {
			validarTelefone(cliente.getTelefone());
		}
		if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
			throw new RegraNegocioException("Informe pelo menos 1 email válido");
		}
		cliente.getEmail().forEach(email -> {
			if (!isValidEmailAddressRegex(email.getEmail())) {
				throw new RegraNegocioException("O email: " + email.getEmail() + " é um email inválido.");
			}
		});
		
		
	}
	
	private void validarTelefone(Set<Telefone> telefone) {
		telefone.forEach(tel -> {
			validandoOTelefone(tel);
		});
	}

	private void validandoOTelefone(Telefone tel) {
		if (tel.getNumero() == null || tel.getNumero().isEmpty()) {
			throw new RegraNegocioException("Numero do Telefone é um campo obrigatório");
		}
		if (tel.getTipo() == null) {
			throw new RegraNegocioException("Tipo do Telefone é um campo obrigatório");
		}
	}

	//Obrigatório preenchimento de CEP, logradouro, bairro, cidade e uf
	private void validarEndereco(Endereco endereco) {
		if (endereco.getCep() == null  || endereco.getCep().isEmpty()) {
			throw new RegraNegocioException("CEP é um campo obrigatório");
		}
		if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()) {
			throw new RegraNegocioException("Logradouro é um campo obrigatório");
		}
		if (endereco.getBairro() == null || endereco.getBairro().isEmpty()) {
			throw new RegraNegocioException("Bairro é um campo obrigatório");
		}
		if (endereco.getCidade() == null || endereco.getCidade().isEmpty()) {
			throw new RegraNegocioException("Cidade é um campo obrigatório");
		}
		if (endereco.getUf() == null || endereco.getUf().isEmpty()) {
			throw new RegraNegocioException("UF é um campo obrigatório");
		}
	}

	private boolean isValidEmailAddressRegex(String email) {
	    boolean isEmailIdValid = false;
	    if (email != null && email.length() > 0) {
	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(email);
	        if (matcher.matches()) {
	            isEmailIdValid = true;
	        }
	    }
	    return isEmailIdValid;
	}
	
}
