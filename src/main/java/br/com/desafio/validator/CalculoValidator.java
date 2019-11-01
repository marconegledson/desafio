package br.com.desafio.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.desafio.domain.entities.Calculo;
import br.com.desafio.exceptions.UsuarioNotFoundException;

@Component
public class CalculoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Calculo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Calculo calculo = (Calculo) target;
		if(calculo.getUsuario() != null && calculo.getUsuario().getId() == null) {
			throw new UsuarioNotFoundException();
		}
	}

}
