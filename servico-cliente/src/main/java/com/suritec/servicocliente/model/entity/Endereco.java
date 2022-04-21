package com.suritec.servicocliente.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.suritec.servicocliente.api.dto.EnderecoDTO;
import com.suritec.servicocliente.util.Utilitarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endereco", schema= "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	
	@Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	@Column private String cep;
	@Column private String logradouro;
	@Column private String bairro;
	@Column private String cidade;
	@Column private String uf;
	@Column private String complemento;
	
	public static Endereco convert(EnderecoDTO dto) {
		Endereco endereco =  Endereco.builder()
						.id(dto.getId())
						.cep(Utilitarios.retirarMascaraCep(dto.getCep()))
						.logradouro(dto.getLogradouro())
						.bairro(dto.getBairro())
						.cidade(dto.getCidade())
						.uf(dto.getUf())
						.complemento(dto.getComplemento())
						.build();
		return endereco;
	}

	public static Endereco atualizarEndereco(Endereco recuperado, Endereco atualizar) {
		recuperado.setCep(atualizar.getCep());
		recuperado.setLogradouro(atualizar.getLogradouro());
		recuperado.setBairro(atualizar.getBairro());
		recuperado.setCidade(atualizar.getCidade());
		recuperado.setUf(atualizar.getUf());
		recuperado.setComplemento(atualizar.getComplemento());
		return recuperado;
	}

}
