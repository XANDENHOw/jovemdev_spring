package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests{

	@Autowired
	PilotoService service;
	@Autowired
	PaisService paisService;
	@Autowired
	EquipeService equipeService;
	
	@Test
	@DisplayName("Teste busca piloto por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findById() {
		var piloto = service.findById(1);
		assertThat(piloto).isNotNull();
		assertEquals(1, piloto.getId());
		assertEquals("Piloto1", piloto.getName());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Piloto id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameTest() {
		var lista = service.findByName("Piloto1");
		assertEquals(1, lista.size());
		assertEquals("Redbull", lista.get(0).getEquipe().getName());
	}
	
	@Test
	@DisplayName ("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByName("Pablo"));
		assertEquals("Não há pilotos cadastrados com esse nome", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisTest() {
		var lista = service.findByPais(paisService.findById(1));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por pais inexisnte")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPais(paisService.findById(30)));
		assertEquals("Nenhum piloto encontrado do pais Portugal", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por Equipe")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeTest() {
		var lista = service.findByEquipe(equipeService.findById(1));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por Equipe inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByEquipe(equipeService.findById(5)));
		assertEquals("Equipe 5 não encontrada!", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void listAllPaisTest() {
		var lista = service.listAll();
		assertTrue(lista != null);
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Lista todos vazio")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void listAllPaisErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Nenhum piloto cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste cadastra piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void cadastraTest() {
		var piloto = new Piloto(null, "Piloto1", paisService.findById(1), equipeService.findById(1));
		service.salvar(piloto);
		var novoPiloto = service.findById(1);
		assertEquals("Redbull", novoPiloto.getEquipe().getName());
	}
	
	@Test
	@DisplayName ("Teste update piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void updateTest() {
		var piloto = new Piloto(1, "PilotoAlterado", paisService.findById(1), equipeService.findById(1));
		service.update(piloto);
		var novoPiloto = service.findById(1);
		assertEquals("PilotoAlterado", novoPiloto.getName());
	}
	
	@Test
	@DisplayName ("Teste delete piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deleteTest() {
		service.delete(1);
		var lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete piloto inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(9));
		assertEquals("Piloto id 9 não existe", exception.getMessage());
	}
}
