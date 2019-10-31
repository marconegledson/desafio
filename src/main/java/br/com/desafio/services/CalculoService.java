package br.com.desafio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.domain.entities.Calculo;
import br.com.desafio.domain.repositories.CalculoRepository;
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

	public Integer calcularDigitoUnico(Calculo calculo) {
		log.debug(">> calcularDigitoUnico [calculo={}]", calculo);
		int resultado = cache.containsKey(calculo) ? cache.get(calculo) : calculeAndSave(calculo);
		log.debug("<< calcularDigitoUnico [calculo={}]", calculo);
		return resultado;
	}
	
	private Integer calculeAndSave(Calculo calculo) {
		log.debug(">> calculeAndSave [calculo={}]", calculo);

		int resultado = 0;
		if(calculo.getUsuario() != null) {
			calculo.setUsuario(usuarioService.findById(calculo.getUsuario().getId()));
		}
		resultado = digitoUnicoService.calcularDigitoUnico(calculo.getEntrada(), calculo.getConcatenador());
		log.debug("<< calculeAndSave [resultado={}]", resultado);
		
		calculo.setResultado(resultado);
		calculo = repository.save(calculo);
		cache.put(calculo, calculo.getResultado());
		log.debug("<< calculeAndSave [resultado={}, calculo]", resultado, calculo);
		
		return resultado;
	}

}
