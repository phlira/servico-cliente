package com.suritec.servicocliente.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario", schema= "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	@Column private String nome;
	@Column	private String login;
	@Column @JsonIgnore private String senha;
	@Column private String role;
	
}
