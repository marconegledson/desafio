package br.com.desafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.domain.repositories.UsuarioRepository;
import br.com.desafio.utilities.Criptografia;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService extends AbstractService<Usuario, Long>{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	protected JpaRepository<Usuario, Long> getRepository() {
		return repository;
	}
	
	
	public Usuario critografar(Usuario usuario) throws Exception {
		log.debug(">> critografar [usuario={}]", usuario);
		Optional<Usuario> optional = usuario.getId() == null ? Optional.empty() : getRepository().findById(usuario.getId());
		log.debug("<< critografar [optional={}]", optional);
		if(optional.isPresent()) usuario = optional.get();
		Criptografia criptografia = new Criptografia();
		usuario.setEmail(criptografia.encrypt(usuario.getEmail()));
		usuario.setNome(criptografia.encrypt((usuario.getNome())));
		usuario = getRepository().save(usuario);
		log.debug("<< critografar [usuario={}]", usuario);
		return usuario;
	}
	

}
