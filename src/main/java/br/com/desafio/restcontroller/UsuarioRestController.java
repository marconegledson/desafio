package br.com.desafio.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.services.AbstractService;
import br.com.desafio.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController extends AbstractRestController<Usuario, Long>{
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected AbstractService<Usuario, Long> getService() {
		return service;
	}
	
	
	/**
	 * [POST] Cria ou atualiza um usuario criptografando o nome e senha
	 * @param entity a entidade no formato json
	 * @throws Exception 
	 */
	@PostMapping(value = "/criptografar")
	@ResponseStatus(code = HttpStatus.OK)
	public Usuario criptografar(@RequestBody Usuario usuario) throws Exception{
		log.debug(" >> criptografar [usuario={}] ", usuario);
		usuario = service.critografar(usuario);
		log.debug(" << criptografar [usuario={}] ", usuario);
		return usuario;
	}
	

}
