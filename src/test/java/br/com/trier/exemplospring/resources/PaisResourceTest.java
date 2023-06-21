package br.com.trier.exemplospring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.exemplospring.ExemploSpringApplication;
import br.com.trier.exemplospring.domain.Pais;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pais.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = ExemploSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Pais> getPais(String url){
		return rest.getForEntity(url, Pais.class);
	}
	
	private ResponseEntity<List<Pais>> getPaises(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pais>>() {
		});
	}
	
	@Test
	@DisplayName("Busca país por nome")
	void findByName() {
		ResponseEntity<List<Pais>> response = getPaises("/pais/name/brasil");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Pais> pais = new ArrayList<Pais>();
		pais = response.getBody();
		assertEquals(1, pais.size());
		assertEquals("Brasil", pais.get(0).getName());
	}
	
	
	@Test
	@DisplayName("Buscar por id")
	void FindById() {
		ResponseEntity<Pais> response = getPais("/pais/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Pais pais = response.getBody();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	void FindByIdNotFound() {
		ResponseEntity<Pais> response = getPais("/pais/30");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar país")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		Pais pais = new Pais(null, "cadastra");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais",
				HttpMethod.POST,
				requestEntity,
				Pais.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Pais newPais = responseEntity.getBody();
		assertEquals("cadastra", newPais.getName());
	}
	
	@Test
	@DisplayName("atualiza país")//concertar isso
	void update() {
		Pais pais = new Pais(1, "altera");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1",
				HttpMethod.PUT,
				requestEntity,
				Pais.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		pais = responseEntity.getBody();
		assertEquals("altera", pais.getName());
	}
	
	@Test
	@DisplayName("deleta país")
	void delete() {
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1",
				HttpMethod.DELETE,
				null,
				Pais.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<Pais> paisResponse = getPais("/pais/1");
		assertEquals(HttpStatus.NOT_FOUND, paisResponse.getStatusCode());
	}
	
	
	@Test
	@DisplayName("Lista todos países")
	void listAll() {
		ResponseEntity<List<Pais>> response = getPaises("/pais");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Pais> lista = response.getBody();
		assertEquals(3, lista.size());
	}
	
}
