package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste busca usuario por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() { 
		User usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario test 1", usuario.getName());
		assertEquals("test@test.com", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste busca usuario por ID inválido")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdNonExistentTest() {
		User usuario = userService.findById(10);
		assertThat(usuario).isNull();
	}
	
	@Test
	@DisplayName("Teste insert usuario")
	void incluiTest() {
		var user = new User(null, "Test", "test4@test.com", "123");
		userService.salvar(user);
		user = userService.findById(1);
		assertThat(user).isNotNull();
		assertEquals(1, user.getId());
		assertEquals("Test", user.getName());
		assertEquals("test4@test.com", user.getEmail());
		assertEquals("123", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste update usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})

	void updateTest() {
	
		var user = new User(1, "altera", "altera", "altera");
		userService.update(user);
		user = userService.findById(1);
		assertThat(user).isNotNull();
		assertEquals(1, user.getId());
		assertEquals("altera", user.getName());
		assertEquals("altera", user.getEmail());
		assertEquals("altera", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste delete usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteTest() {
		userService.delete(2);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());		
	}

	@Test
	@DisplayName("Teste delete usuario invalido")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteNonExistentTest() {
		userService.delete(20);
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste listar todos os usuários")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste listar usuário por inicial nome")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findUserByNameStartsWithTest() {
		List<User> lista = userService.findByName("Usuario");
		assertEquals(2, lista.size());
		lista = userService.findByName("Usuario Test1");
		assertEquals(1, lista.size());
	}
}
