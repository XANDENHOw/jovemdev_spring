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
import br.com.trier.exemplospring.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = ExemploSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<UserDTO> getUser(String url){
		return rest.getForEntity(url, UserDTO.class);
	}
	
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
		});
	}
	
	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario test 1", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/30");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "cadastra", "cadastra", "cadastra");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("cadastra", user.getName());
	}
	
	@Test
	@DisplayName("Lista Todos usuários")
	public void listAll() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> lista = response.getBody();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("busca por nome usuários")
	public void findByName() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/usu");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		//List<UserDTO> lista = ;
		
	}
	
	@Test
	@DisplayName("atualiza usuários")
	public void update() {
		UserDTO dto = new UserDTO(1, "cadastra", "cadastra", "cadastra");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/1",
				HttpMethod.PUT,
				requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("cadastra", user.getName());
	}
	
	@Test
	@DisplayName("deleta usuários")
	public void delete() {
		//UserDTO dto = getUser("/usuarios/1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto ,headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/1",
				HttpMethod.DELETE,
				requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("cadastra", user.getName());
	}
}
