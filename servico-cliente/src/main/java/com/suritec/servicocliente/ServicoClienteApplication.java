package com.suritec.servicocliente;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.suritec.servicocliente.model.entity.Usuario;
import com.suritec.servicocliente.model.repository.UsuarioRepository;

@SpringBootApplication
public class ServicoClienteApplication implements WebMvcConfigurer, ApplicationRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(ServicoClienteApplication.class, args);
	}
	
	@Autowired
	private UsuarioRepository repositoy;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		Usuario admin = Usuario.builder()
								.nome("Administrador")
								.login("admin")
								.senha("123456")
								.role("ADMIN")
								.build();
		if (repositoy.findByLogin(admin.getLogin()).isEmpty()) {
			repositoy.save(admin);
		}
		
		Usuario comum = Usuario.builder()
								.nome("Usuario Comum")
								.login("comum")
								.senha("123456")
								.role("COMUM")
								.build();
		if (repositoy.findByLogin(comum.getLogin()).isEmpty()) {
			repositoy.save(comum);
		}
	}

}
