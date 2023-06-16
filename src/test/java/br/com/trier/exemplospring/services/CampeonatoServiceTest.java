package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
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
}
