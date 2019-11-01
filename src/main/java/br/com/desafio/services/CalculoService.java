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

	/**
	 * Retorna uma lista com todos os calculos do usuario
	 * @param idUsuario o id do usuario que contem os calculos
	 * @return uma lista de calculos
	 */
	public List<Calculo> findByUsuario(Long idUsuario) {
		return repository.findByUsuario(idUsuario);
	}

	/**
	 * Realiza o calculo do digito unico pela entidade de calculo
	 * @param calculo a entidade contendo os dados para o calculo
	 * @return o resultado do calculo
	 */
	public Integer calcularDigitoUnico(Calculo calculo) {
		log.debug(">> calcularDigitoUnico [calculo={}]", calculo);
		int resultado = cache.containsKey(calculo) ? cache.get(calculo) : calcular(calculo);
		log.debug("<< calcularDigitoUnico [calculo={}]", calculo);
		return resultado;
	}
	
	/**
	 * Calcula o digito unico com base no calculo
	 * @param calculo a entidade contendo os dados do calculo
	 * @return o resultado do calculo
	 */
	private Integer calcular(Calculo calculo) {
		log.debug(">> calculeAndSave [calculo={}]", calculo);

		int resultado = 0;
		if(calculo.getUsuario() != null) {
			calculo.setUsuario(usuarioService.findById(calculo.getUsuario().getId()));
		}
		resultado = digitoUnicoService.calcularDigitoUnico(calculo.getEntrada(), calculo.getConcatenador());
		log.debug("<< calculeAndSave [resultado={}]", resultado);
		
		calculo = salvar(calculo, resultado);
		cache.put(calculo, calculo.getResultado());
		log.debug("<< calculeAndSave [resultado={}, calculo]", resultado, calculo);
		
		return resultado;
	}

	/**
	 * Grava o resultado na base de dados
	 * @param calculo a entidade que realizou o calculo
	 * @param resultado o resultado do calculo
	 * @return a entidade salva
	 */
	private Calculo salvar(Calculo calculo, int resultado) {
		log.debug(">> salvar [calculo={}]", calculo);
		calculo.setResultado(resultado);
		calculo = repository.save(calculo);
		log.debug("<< salvar [calculo={}]", calculo);
		return calculo;
	}

}
