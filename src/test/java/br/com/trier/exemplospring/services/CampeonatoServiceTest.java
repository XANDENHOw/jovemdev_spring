package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTests{
	
	@Autowired
	CampeonatoService campeonatoService;
	
	@Test
	@DisplayName("Teste busca campeonato por ID")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findById() {
		var campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Fórmula 1", campeonato.getDescricao());
		assertEquals(2005, campeonato.getYear());
	}
	
	@Test
	@DisplayName("Teste busca campeonato por ID inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdUnEx() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findById(20));
		assertEquals("Campeonato 20 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste insere Campeonato")
	void insertEquipe() {
		var campeonato = new Campeonato(1, "Super Drift Brasil", 2018);
		campeonatoService.salvar(campeonato);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Super Drift Brasil", campeonato.getDescricao());
		assertEquals(2018, campeonato.getYear());	
	}
	
	@Test
	@DisplayName("Teste deleta campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deletaCampeonato() {
		campeonatoService.delete(2);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2, lista.size());
		assertEquals(2015, lista.get(1).getYear());
	}
	
	@Test
	@DisplayName("Teste deleta campeonato inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deletaCampeonatoInexist() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.delete(20));
		assertEquals("Campeonato 20 não encontrado!", exception.getMessage());
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste atualiza campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateCamp() {
		var campeonato = new Campeonato(2, "Super Drift Brasil", 2020);
		campeonatoService.update(campeonato);
		assertThat(campeonato).isNotNull();
		assertEquals(2, campeonato.getId());
		assertEquals("Super Drift Brasil", campeonato.getDescricao());
		assertEquals(2020, campeonato.getYear());
	}
	
	@Test
	@DisplayName("Teste lista campeonatos")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listaTodosCampeonatos() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertThat(lista).isNotNull();
		assertEquals(lista.size(), 3);
		assertEquals("Fórmula 1", lista.get(0).getDescricao());
	}
	
	@Test
	@DisplayName("Teste busca campeonato por ano entre intervalo")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listaCampAnoBetweenTest() {
		var lista = campeonatoService.findByYearBetween(1990,2015);
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca campeonato por ano")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void buscaCampeonatoAnoTest() {
		var lista = campeonatoService.findByYear(2005);
		assertEquals(1, lista.size());
		assertEquals("Fórmula 1", lista.get(0).getDescricao());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca ano inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () ->campeonatoService.findByYear(1996));
		assertEquals("Ano 1996 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca campeonato por descrição")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void buscaCampDescTest() {
		var lista = campeonatoService.findByDescricaoContainsIgnoreCase("Fórmula 1");
		assertEquals("Fórmula 1", lista.get(0).getDescricao());
		lista = campeonatoService.findByDescricaoContainsIgnoreCase("mula");
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca descricao inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescNonExistentTest() {
		var lista = campeonatoService.findByDescricaoContainsIgnoreCase("campeonato");
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca campeonato por descrição e por ano")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void buscaCampDescAnoTest() {
		var lista = campeonatoService.findByDescricaoContainsIgnoreCaseAndYearEquals("Fórmula 1", 2005);
		assertEquals("Fórmula 1", lista.get(0).getDescricao());
		assertEquals(1, lista.get(0).getId());
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste lista todos sem nenhum")
	void listTodosErroTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.listAll());
		assertEquals("Nenhum campeonato encontrado", exception.getMessage());
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(0, lista.size());
	}
	
	
}
