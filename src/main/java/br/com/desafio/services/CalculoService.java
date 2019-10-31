package br.com.desafio.services;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.domain.entities.Calculo;
import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.domain.repositories.CalculoRepository;
import br.com.desafio.exceptions.UsuarioNotFoundException;
import br.com.desafio.utilities.Cache;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CalculoService extends AbstractService<Calculo, Long>{
	
	@Autowired
	private CalculoRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DigitoUnicoService digitoUnicoService;
	
	@Autowired
	private Cache<Calculo, Integer> cache;
	
	@Override
	protected JpaRepository<Calculo, Long> getRepository() {
		return repository;
	}

	public List<Calculo> findByUsuario(Long idUsuario) {
		return repository.findByUsuario(idUsuario);
	}

	public Calculo calcularDigitoUnico(@NotNull Calculo calculo) {
		log.debug(">> calcularDigitoUnico [calculo={}]", calculo);
		if(!cache.containsKey(calculo)) {
			if(calculo.getUsuario() == null || calculo.getUsuario().getId() == null) {
				throw new UsuarioNotFoundException();
			}else {
				Optional<Usuario> usuario = usuarioService.getById(calculo.getUsuario().getId());
				if(usuario.isPresent()) {
					int resultado = digitoUnicoService.calcularDigitoUnico(calculo.getEntrada(), calculo.getConcatenador());
					log.debug("<< calcularDigitoUnico [resultado={}]", resultado);
					calculo.setUsuario(usuario.get());
					calculo.setResultado(resultado);
					calculo = repository.save(calculo);
					cache.put(calculo, calculo.getResultado());
					log.debug("<< calcularDigitoUnico [resultado={}, calculo]", resultado, calculo);
				}else {
					throw new UsuarioNotFoundException();
				}
			}
			
		}
		log.debug("<< calcularDigitoUnico [calculo={}]", calculo);
		return calculo;
	}
	
	

}
