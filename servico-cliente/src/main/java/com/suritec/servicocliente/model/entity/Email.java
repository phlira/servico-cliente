package com.suritec.servicocliente.model.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.suritec.servicocliente.api.dto.EmailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {

	@Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	@Column private String email;
	
	public static Set<Email> convert(List<EmailDTO> list) {
		return list.stream().map(e -> Email.builder()
											.id(e.getId())
											.email(e.getEmail())
											.build())
							.collect(Collectors.toSet());
	}
	
}
