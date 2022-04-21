package com.suritec.servicocliente.api.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDTO {
	private Long id;
	@NotNull @Size(min = 3, max = 100) private String nome;
	@NotNull @CPF private String cpf; 
	@NotEmpty private EnderecoDTO endereco;
	@NotEmpty private List<TelefoneDTO> telefone;
	@NotEmpty private List<EmailDTO> email;
	private Long idUsuario;
}
