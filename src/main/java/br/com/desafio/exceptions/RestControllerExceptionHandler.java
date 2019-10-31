package br.com.desafio.exceptions;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler {

	private static final String ENTITY_NOT_FOUND 	= "Entity Not Found";
	private static final String USUARIO_NOT_FOUND 	= "Usuario Not Found";

	@ResponseBody
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ENTITY_NOT_FOUND)
	public String handleException(HttpServletRequest reqquest, EntityNotFoundException exception) {
		log.error(ENTITY_NOT_FOUND + " {}", exception.getMessage());
		return ENTITY_NOT_FOUND;
	}
	
	
	@ResponseBody
	@ExceptionHandler(UsuarioNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = USUARIO_NOT_FOUND)
	public String handleException(HttpServletRequest reqquest, UsuarioNotFoundException exception) {
		log.error(USUARIO_NOT_FOUND + " {}", exception.getMessage());
		return USUARIO_NOT_FOUND;
	}
	

}
