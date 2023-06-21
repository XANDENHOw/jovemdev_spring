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
public class PilotoServiceTest extends BaseTests{

	@Autowired
	PilotoService service;
	
	@Test
	@DisplayName("Teste busca piloto por ID")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findById() {
		var piloto = service.findById(1);
		assertThat(piloto).isNotNull();
		assertEquals(1, piloto.getId());
		assertEquals("Piloto1", piloto.getName());
	}
}
