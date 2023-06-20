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
import br.com.trier.exemplospring.domain.Equipe;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findById(4));
		assertEquals("Equipe 4 não encontrada!", exception.getMessage());
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
	@DisplayName ("Teste cadastra equipe nome repetido")
	void salvarEquipeErrorTest() {
		equipeService.salvar(new Equipe(4, "McLaren"));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> equipeService.salvar(new Equipe(null, "McLaren")));
		assertEquals("Equipe já cadastrada: McLaren", exception.getMessage());
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
		assertEquals("Mercedes", equipe.getName());
	}
	
	@Test
	@DisplayName ("Teste update equipe nome ja existente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateEquipeErrorTest() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> equipeService.update(new Equipe(null, "Redbull")));
		assertEquals("Equipe já cadastrada: Redbull", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste update equipe inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateEquipeNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.update(new Equipe(6, "Lotus")));
		assertEquals("Equipe 6 não encontrada!", exception.getMessage());
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.delete(20));
		assertEquals("Equipe 20 não encontrada!", exception.getMessage());
		List<Equipe> lista = equipeService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNameNonExistent() {
		var lista = equipeService.findByNameContainsIgnoreCase("merca");
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste lista todos")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listaTodosTest() {
		var lista = equipeService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste lista todos vazio")
	void listaTodosErroTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.listAll());
		assertEquals("Nenhuma equipe encontrada", exception.getMessage());
	}
	
}
