package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{

	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste busca país por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findById() {
		var pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());		
	}
	
	@Test
	@DisplayName("Teste busca país por ID que não existe")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdInexist() {
		var pais = paisService.findById(10);
		assertThat(pais).isNull();
	}
	
	@Test
	@DisplayName("Teste inserir país")
	void insertPais() {
		var pais = new Pais(1, "Belgica");
		paisService.salvar(pais);
		pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Belgica", pais.getName());		
	}
	
	@Test
	@DisplayName("Teste update país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePais() {
		var pais = new Pais(1, "Belgica");
		paisService.update(pais);
		pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Belgica", pais.getName());
	}
	
	@Test
	@DisplayName("Teste deleta país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePais() {
		paisService.delete(2);
		List<Pais> lista = paisService.listAll();
		assertEquals(2, lista.size());
		assertEquals(3, lista.get(1).getId());
	}
	
	@Test
	@DisplayName("Teste deleta país inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePaisInexist() {
		paisService.delete(20);
		List<Pais> lista = paisService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca país por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllPaises() {
		List<Pais> lista = paisService.listAll();
		assertThat(lista).isNotNull();
		assertEquals(3, lista.size());
		assertEquals("França", lista.get(1).getName());		
	}
}
