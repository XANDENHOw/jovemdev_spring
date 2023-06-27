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

import br.com.trier.exemplospring.ExemploSpringApplication;
import br.com.trier.exemplospring.domain.Pista;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = ExemploSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PistaResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Pista> getPista(String url){
		return rest.getForEntity(url, Pista.class);
	}
	
	private ResponseEntity<List<Pista>> getPistas(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pista>>() {
		});
	}

	@Test
	@DisplayName("teste Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testFindByID() {
		ResponseEntity<Pista> response = getPista("/pistas/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Pista pista = response.getBody();
		assertEquals("10", pista.getTamanho());
		ResponseEntity<Pista> responseEntity = rest.getForEntity("/pistas/1", Pista.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("teste Buscar por tamanho")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testFindByTamanho() {
		ResponseEntity<List<Pista>> response = getPistas("/pistas/tamanho/10");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Pista> lista = new ArrayList<Pista>();
		lista = response.getBody();
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("teste Buscar por tamanho entre")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testFindByTamanhoEntre() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<Pista>> responseEntity = rest.exchange("/pistas/tamanho/entre/1/9", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Pista>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Pista> lista = responseEntity.getBody();
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("teste Buscar por pais")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testFindByPais() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<Pista>> responseEntity = rest.exchange("/pistas/pais/1", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Pista>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Pista> lista = responseEntity.getBody();
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("teste listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testListAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<Pista>> responseEntity = rest.exchange("/pistas", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Pista>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Pista> lista = responseEntity.getBody();
		assertEquals(4, lista.size());
	}

	@Test
	@DisplayName("teste cadastra pista")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testCadastra() {
		Pista pista = new Pista();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pista> requestEntity = new HttpEntity<>(pista, headers);

		ResponseEntity<Pista> responseEntity = rest.exchange("/pista", HttpMethod.POST, requestEntity, Pista.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Pista pistaNova = responseEntity.getBody();
		assertEquals(null, pistaNova.getPais());
	}

	@Test
	@DisplayName("teste update pista")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testUpdate() {
		Pista pista = new Pista();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pista> requestEntity = new HttpEntity<>(pista, headers);

		ResponseEntity<Pista> responseEntity = rest.exchange("/pista/1", HttpMethod.PUT, requestEntity, Pista.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Pista pistaNova = responseEntity.getBody();
		assertEquals(null, pistaNova.getPais());
	}

	@Test
	@DisplayName("teste delete pista")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testDelete() {
		ResponseEntity<Void> responseEntity = rest.exchange("/pais/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("teste delete pista inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	@Sql(scripts = "classpath:/resources/sqls/pista.sql")
	public void testDeleteNotFoundPais() {
		ResponseEntity<Void> responseEntity = rest.exchange("/pais/10", HttpMethod.DELETE, null, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
