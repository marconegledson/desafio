package br.com.desafio;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio.domain.entities.Calculo;
import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.services.DigitoUnicoService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesafioApplicationTests {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private DigitoUnicoService digitoUnicoService;
	
	
	@LocalServerPort
	private int port;
	
	@Before
	public void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	@Test
	public void testADigitoUnico() {
		BigInteger entrada = BigInteger.valueOf(9875L);
		Long concatenador = 4L;
		int resultado = digitoUnicoService.calcularDigitoUnico(entrada, concatenador);
		assertTrue(resultado == 8);
	}
	
	@Test
	public void testBCrudInsertUsuario() throws JsonProcessingException {
		Usuario usuario = new Usuario();
		usuario.setNome("Marcone Gledson de Almeida");
		usuario.setEmail("marconegledson@gmail.com");
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(usuario))
			.post("/api/usuario/")
			.then().statusCode(201);
		
	}
	
	@Test
	public void testCCrudUpdateUsuario() throws JsonProcessingException {
		Usuario usuario = new Usuario();
		usuario.setNome("Marcone Gledson");
		usuario.setEmail("marconegledson@gmail.com");
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(usuario))
			.put("/api/usuario/{id}", 1)
			.then().statusCode(200);
		
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(usuario))
			.put("/api/usuario/{id}", 100)
			.then().statusCode(404);
		
	}
	
	@Test
	public void testDCrudListUsuario() {
		get("/api/usuario/")
			.then().statusCode(200)
			.and().contentType(ContentType.JSON)
			.and().body("nome", not(empty()));
	}
	
	@Test
	public void testECrudDeleteUsuario() {
		delete("/api/usuario/{id}", 1).then().statusCode(200);
		delete("/api/usuario/{id}", 12).then().statusCode(404);
	}
	
	@Test
	public void testFInsertCalculoComUsuario() throws JsonProcessingException {
		Usuario usuario = new Usuario();
		usuario.setNome("Marcone Gledson");
		usuario.setEmail("marconegledson@gmail.com");
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(usuario))
			.post("/api/usuario/")
			.then().statusCode(201);
		
		usuario.setId(1L);
		Calculo calculo = new Calculo();
		calculo.setEntrada(BigInteger.valueOf(12458L));
		calculo.setConcatenador(78L);
		calculo.setUsuario(usuario);
		
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(calculo))
			.post("/api/calculo/calcularDigitoUnico")
			.then().statusCode(200)
			.and().body(not(empty()));
		
	}
	
	@Test
	public void testGInsertCalculoSemUsuario() throws JsonProcessingException {
		Calculo calculo = new Calculo();
		calculo.setEntrada(BigInteger.valueOf(12458L));
		calculo.setConcatenador(78L);
		
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(calculo))
			.post("/api/calculo/calcularDigitoUnico")
			.then().statusCode(200)
			.and().body(not(empty()));
		
	}
	
	@Test
	public void testHCrudListCalculoByUsuario() throws JsonProcessingException {
		get("/api/calculo/usuario/{id}", 1L)
			.then().statusCode(200)
			.and().contentType(ContentType.JSON)
			.and().body(not(empty()));
		
	}
	
	@Test
	public void testICriptografarUsuario() throws JsonProcessingException {
		Usuario usuario = new Usuario();
		usuario.setNome("Marcone Gledson de Almeida");
		usuario.setEmail("marconegledson@gmail.com");
		given()
			.contentType(ContentType.JSON)
			.body(mapper.writeValueAsString(usuario))
			.post("/api/usuario/criptografar")
			.then().statusCode(200)
			.and().body("email", not(empty()))
			.and().body("nome", not(empty()));
	}
	
	

}
