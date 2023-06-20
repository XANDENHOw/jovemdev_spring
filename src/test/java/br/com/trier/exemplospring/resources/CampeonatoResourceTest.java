package br.com.trier.exemplospring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import br.com.trier.exemplospring.domain.Campeonato;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/campeonato.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_campeonato.sql")
@SpringBootTest(classes = ExemploSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Campeonato> getCampeonato(String url){
		return rest.getForEntity(url, Campeonato.class);
	}
	
	private ResponseEntity<List<Campeonato>> getCampeonatos(String url){
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Campeonato>>() {});
	}
	
	@Test
	@DisplayName("Cadastrar campeonato")
	@Sql(scripts = "classpath:/resources/sqls/limpa_campeonato.sql")
	void insertTest() {
		Campeonato camp = new Campeonato(null, "SDB", 2021);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos",
				HttpMethod.POST,
				requestEntity,
				Campeonato.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Campeonato campeonato = responseEntity.getBody();
		assertEquals("SDB", campeonato.getDescricao());
	}
	
	@Test
	@DisplayName("Buscar por id")
	void FindById() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonatos/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Campeonato camp = response.getBody();
		assertEquals("Fórmula 1", camp.getDescricao());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	void FindByIdNotFound() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonatos/10");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Lista todos campeonatos")
	void listAll() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonatos");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Campeonato> lista = response.getBody();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("atualiza campeonato")
	void update() {
		Campeonato camp = new Campeonato(3, "altera", 2005);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos/3",
				HttpMethod.PUT,
				requestEntity,
				Campeonato.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		camp = responseEntity.getBody();
		assertEquals("altera", camp.getDescricao());
	}
	
	@Test
	@DisplayName("deleta campeonato")
	void delete() {
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos/1",
				HttpMethod.DELETE,
				null,
				Campeonato.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<Campeonato> campResponse = getCampeonato("/campeonatos/1");
		assertEquals(HttpStatus.NOT_FOUND, campResponse.getStatusCode());
	}
	
	@Test
	@DisplayName("Busca campeonato por ano")
	void findByYear() {
		
	}
	
	@Test
	@DisplayName("Busca campeonato por ano entre intervalo")
	void findByYearBetween() {
		
	}
	
	@Test
	@DisplayName("Busca campeonato por descrição")
	void findByDescricao() {
		
	}
	
	@Test
	@DisplayName("Busca campeonato por descrição e ano")
	void findByDescricaoAndYear() {
		
	}
}
