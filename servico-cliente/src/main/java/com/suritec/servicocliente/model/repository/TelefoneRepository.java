package com.suritec.servicocliente.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suritec.servicocliente.model.entity.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

}
