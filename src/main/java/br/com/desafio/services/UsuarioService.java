package br.com.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.domain.repositories.UsuarioRepository;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService extends AbstractService<Usuario, Long>{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	protected JpaRepository<Usuario, Long> getRepository() {
		return repository;
	}
	
	

}
