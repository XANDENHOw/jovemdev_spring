package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Equipe;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTests{

	@Autowired
	EquipeService equipeService;
	
	@Test
	@DisplayName("Teste busca equipe por ID")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findById() {
		var equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("Redbull", equipe.getName());
	}
	
	@Test
	@DisplayName("Teste busca por ID inválido")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNoExistId() {
		var equipe = equipeService.findById(4);
		assertThat(equipe).isNull();
	}
	
	@Test
	@DisplayName("Teste insere equipe")
	void insertEquipeTest() {
		var equipe = new Equipe(null, "Mclaren");
		equipeService.salvar(equipe);
		equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("Mclaren", equipe.getName());
	}
		
	@Test
	@DisplayName("Teste update Equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateEquipTest() {
		var equipe = new Equipe(3, "McLaren");
		equipeService.update(equipe);
		equipe = equipeService.findById(3);
		assertThat(equipe).isNotNull();
		assertEquals(3, equipe.getId());
		assertEquals("McLaren", equipe.getName());
	}

	@Test
	@DisplayName("Teste deleta equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deletaEquipe() {
		equipeService.delete(1);
		List<Equipe> lista = equipeService.listAll();
		assertEquals(2, lista.size());
		assertEquals("Ferrari", lista.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste deleta equipe inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deletaNoExistEquipe() {
		equipeService.delete(20);
		List<Equipe> lista = equipeService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste deleta equipe inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listaTodasEquipes() {
		List<Equipe> lista = equipeService.listAll();
		assertEquals(3, lista.size());
	}
	
}
