package br.com.desafio.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.domain.entities.Calculo;
import br.com.desafio.services.AbstractService;
import br.com.desafio.services.CalculoService;
import br.com.desafio.validator.CalculoValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/calculo")
public class CalculoRestController extends AbstractRestController<Calculo, Long>{
	
	@Autowired
	private CalculoService service;
	
	@Autowired
	private CalculoValidator validator;
	
	@InitBinder("calculo")
	private void initBinder(WebDataBinder binder){
		binder.addValidators(validator);
	}
	
	@Override
	protected AbstractService<Calculo, Long> getService() {
		return service;
	}
	
	@PostMapping("/calcularDigitoUnico")
	@ResponseStatus(code = HttpStatus.OK)
	public Integer calcularDigitoUnico(@Valid @RequestBody Calculo calculo) throws Exception {
		log.debug(">> calcularDigitoUnico [calculo={}]", calculo);
		Integer resultado = service.calcularDigitoUnico(calculo);
		log.debug("<< calcularDigitoUnico [calculo={}, resultado={}]", calculo, resultado);
		return resultado;
	}
	
	@GetMapping("/usuario/{idUsuario}")
	public List<Calculo> buscarPorUsuario(@PathVariable(value = "idUsuario", required = true) Long idUsuario) {
		log.debug(">> buscarPorUsuario [idUsuario={}]", idUsuario);
		List<Calculo> calculos = service.findByUsuario(idUsuario);
		log.debug("<< buscarPorUsuario [calculos={}]", calculos);
		return calculos;
	}

}
