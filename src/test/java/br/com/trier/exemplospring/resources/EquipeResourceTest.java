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
import br.com.trier.exemplospring.domain.Equipe;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/equipe.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = ExemploSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<Equipe> getEquipe(String url){
		return rest.getForEntity(url, Equipe.class);
	}
	
	private ResponseEntity<List<Equipe>> getEquipes(String url){
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Equipe>>() {});
	}
	
	@Test
	@DisplayName("Buscar por id")
	void FindById() {
		ResponseEntity<Equipe> response = getEquipe("/equipes/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Equipe equipe = response.getBody();
		assertEquals("Redbull", equipe.getName());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	void FindByIdNotFound() {
		ResponseEntity<Equipe> response = getEquipe("/equipes/10");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar equipe")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		Equipe equipe = new Equipe(null, "EquipeNova");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipes",
				HttpMethod.POST,
				requestEntity,
				Equipe.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Equipe newEquipe = responseEntity.getBody();
		assertEquals("EquipeNova", newEquipe.getName());
	}
	
	@Test
	@DisplayName("atualiza equipe")
	void update() {
		Equipe equipe = new Equipe(2, "cadastra");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipes/2",
				HttpMethod.PUT,
				requestEntity,
				Equipe.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Equipe equipeAtualizada = responseEntity.getBody();
		assertEquals("cadastra", equipeAtualizada.getName());
	}
	
	@Test
	@DisplayName("Lista todas equipes")
	void listAll() {
		ResponseEntity<List<Equipe>> response = getEquipes("/equipes");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Equipe> lista = response.getBody();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("deleta equipe")
	void delete() {
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipes/1",
				HttpMethod.DELETE,
				null,
				Equipe.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<Equipe> userResponse = getEquipe("/equipes/1");
		assertEquals(HttpStatus.NOT_FOUND, userResponse.getStatusCode());
	}
	
	@Test
	@DisplayName("Busca equipe por nome")
	void findByName() {
		ResponseEntity<List<Equipe>> response = getEquipes("/equipes/name/redbull");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Equipe> equipe = new ArrayList<Equipe>();
		equipe = response.getBody();
		assertEquals(1, equipe.size());
		assertEquals("Redbull", equipe.get(0).getName());
	}
	
	
}
