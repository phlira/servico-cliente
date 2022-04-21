package com.suritec.servicocliente.model.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.suritec.servicocliente.api.dto.ClienteDTO;
import com.suritec.servicocliente.util.Utilitarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente", schema= "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	@Column @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;	
	@Column private String nome;
	@Column private String cpf;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id") 
	private Endereco endereco;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "cliente_telefone", schema = "clientes", joinColumns = {@JoinColumn(name = "cliente_id")}, inverseJoinColumns = {@JoinColumn(name = "telefone_id")})
	private Set<Telefone> telefone;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "cliente_email", schema = "clientes" , joinColumns = {@JoinColumn(name = "cliente_id")}, inverseJoinColumns = {@JoinColumn(name = "email_id")})
	private Set<Email> email;
	
	@Column(name = "data_cadastro") @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) private LocalDate dataCadastro;
	@Column(name = "data_ultima_atualizacao") @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) private LocalDate dataAtualizacao;
	
	@ManyToOne @JoinColumn(name = "id_usuario") private Usuario usuario;
	
	public static Cliente converter(ClienteDTO dto, Usuario usuario) {
		return Cliente
					.builder()
					.id(dto.getId())
					.nome(dto.getNome())
					.cpf(Utilitarios.retiraMascaraCpf(dto.getCpf()))
					.endereco(Endereco.convert(dto.getEndereco()))
					.telefone(Telefone.convert(dto.getTelefone()))
					.email(Email.convert(dto.getEmail()))
					.usuario(usuario)
					.build();
	}
}
