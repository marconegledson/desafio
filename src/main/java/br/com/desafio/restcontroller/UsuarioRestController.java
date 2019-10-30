package br.com.desafio.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.services.AbstractService;
import br.com.desafio.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController extends AbstractRestController<Usuario, Long>{
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected AbstractService<Usuario, Long> getService() {
		return service;
	}

}
