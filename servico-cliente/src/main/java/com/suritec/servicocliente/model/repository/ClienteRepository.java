package com.suritec.servicocliente.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suritec.servicocliente.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByCpf(String cpf);

}
