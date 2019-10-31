package br.com.desafio;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio.domain.entities.Usuario;
import br.com.desafio.services.DigitoUnicoService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DesafioApplicationTests {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private DigitoUnicoService digitoUnicoService;
	
	
	@LocalServerPort
	private int port;
	
	@Test
	void contextLoads() {
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
	

}
