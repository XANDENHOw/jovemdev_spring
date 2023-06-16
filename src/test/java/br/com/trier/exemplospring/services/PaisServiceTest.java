package br.com.trier.exemplospring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{

	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste busca país por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findById() {
		
	}
}
