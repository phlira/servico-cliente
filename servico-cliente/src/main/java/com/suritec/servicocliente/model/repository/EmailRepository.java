package com.suritec.servicocliente.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suritec.servicocliente.model.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
