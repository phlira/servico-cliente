package com.suritec.servicocliente.model.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.suritec.servicocliente.api.dto.TelefoneDTO;
import com.suritec.servicocliente.model.enuns.TipoTelefone;
import com.suritec.servicocliente.util.Utilitarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

	@Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	@Column private String numero;
	@Enumerated(value =  EnumType.STRING) @Column private TipoTelefone tipo;
	
	public static Set<Telefone> convert(List<TelefoneDTO> list) {
		Set<Telefone> telefones = new HashSet<Telefone>();
		telefones = list.stream().map(tel -> Telefone.builder()
										 .id(tel.getId())
										 .numero(Utilitarios.retirarMascaraTelefone(tel.getNumero()))
										 .tipo(TipoTelefone.valueOf(tel.getTipo()))
										 .build())
						 .collect(Collectors.toSet());
		return telefones;
	}
}
