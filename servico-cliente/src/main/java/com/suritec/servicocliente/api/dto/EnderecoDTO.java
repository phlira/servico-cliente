package com.suritec.servicocliente.api.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

	private Long id;
	@NotNull private String cep;
	@NotNull private String logradouro;
	@NotNull private String bairro;
	@NotNull private String cidade;
	@NotNull private String uf;
	private String complemento;
	
}
