package br.com.desafio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.desafio.services.UsuarioService;

@SpringBootTest
class DesafioApplicationTests {

	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testADigitoUnico() {
		BigInteger entrada = BigInteger.valueOf(9875L);
		Long concatenador = 4L;
		int resultado = usuarioService.calcularDigitoUnico(entrada, concatenador);
		assertTrue(resultado == 8);
	}
	
	@Test
	public void testBCrudInsertUsuario() {
		
	}
	
	@Test
	public void testCCrudUpdateUsuario() {
		
	}
	
	@Test
	public void testDCrudListUsuario() {
		
	}
	
	@Test
	public void testECrudDeleteUsuario() {
		
	}
	

}
