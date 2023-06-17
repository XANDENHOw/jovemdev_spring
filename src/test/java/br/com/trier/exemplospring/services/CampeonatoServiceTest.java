package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Campeonato;
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
		assertEquals(2005, campeonato.getAno());
	}
	
	@Test
	@DisplayName("Teste busca campeonato por ID inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdUnEx() {
		var campeonato = campeonatoService.findById(20);
		assertThat(campeonato).isNull();
	}
	
	@Test
	@DisplayName("Teste insere Campeonato")
	void insertEquipe() {
		var campeonato = new Campeonato(1, "Super Drift Brasil", 2018);
		campeonatoService.salvar(campeonato);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Super Drift Brasil", campeonato.getDescricao());
		assertEquals(2018, campeonato.getAno());	
	}
	
	@Test
	@DisplayName("Teste deleta campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deletaCampeonato() {
		campeonatoService.delete(2);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2, lista.size());
		assertEquals(2015, lista.get(1).getAno());
	}
	
	@Test
	@DisplayName("Teste deleta campeonato inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deletaCampeonatoInexist() {
		campeonatoService.delete(20);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(3, lista.size());
		assertEquals(2015, lista.get(2).getAno());
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
		assertEquals(2020, campeonato.getAno());
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
	
}
