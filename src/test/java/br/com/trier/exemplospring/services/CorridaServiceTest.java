package br.com.trier.exemplospring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests {

	@Autowired
	CorridaService service;
	@Autowired
	PistaService pistaService;
	@Autowired
	CampeonatoService campeonatoService;

	@Test
	@DisplayName("Teste busca corrida por ID")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findById() {
		var corrida = service.findById(1);
		assertThat(corrida).isNotNull();
		assertEquals(1, corrida.getId());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByIdTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Corrida id 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByNameTest() {
		var lista = service.findByPista(pistaService.findById(1));
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getPista().getId());
	}

	@Test
	@DisplayName("Teste busca por pista inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByNameNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPista(pistaService.findById(10)));
		assertEquals("Pista id 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por campeonato")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByPaisTest() {
		var lista = service.findByCampeonato(campeonatoService.findById(1));
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste busca por campeonato inexisnte")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByPaisErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,
				() -> service.findByCampeonato(campeonatoService.findById(30)));
		assertEquals("Campeonato 30 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByEquipeTest() {
		var lista = service.findByPista(pistaService.findById(1));
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste busca por pista inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void findByCorridaErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPista(pistaService.findById(5)));
		assertEquals("Pista id 5 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste cadastrar corrida")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void insertTest() {
		ZonedDateTime data = ZonedDateTime.parse("2009-07-22T12:00:00Z");
		var corrida = new Corrida(null, data, pistaService.findById(1), campeonatoService.findById(2));
		service.salvar(corrida);
		corrida = service.findById(1);
		assertEquals(1, corrida.getPista().getId());
	}

	@Test
	@DisplayName("Teste cadastrar corrida com ano diferente do ano do campeonato")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void insertDateDiferentCampTest() {
		ZonedDateTime data = ZonedDateTime.parse("2019-07-22T12:00:00Z");
		var corrida = new Corrida(null, data, pistaService.findById(1), campeonatoService.findById(2));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> service.salvar(corrida));
		assertEquals("O ano da corrida não pode ser diferente do ano do campeonato!", exception.getMessage());
	}

	@Test
	@DisplayName("Teste cadastrar corrida com data nula")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void insertDateNullTest() {
		var corrida = new Corrida(null, null, pistaService.findById(1), campeonatoService.findById(2));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> service.salvar(corrida));
		assertEquals("A data da corrida não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void updateTest() {
		ZonedDateTime data = ZonedDateTime.parse("2005-07-22T12:00:15Z");
		var corrida = new Corrida(1, data, pistaService.findById(1), campeonatoService.findById(1));
		service.update(corrida);
		corrida = service.findById(1);
		assertEquals(data, corrida.getDate());

	}

	@Test
	@DisplayName("Teste atualizar pista com data nula")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void updateDateNullTest() {
		var corrida = new Corrida(1, null, pistaService.findById(1), campeonatoService.findById(1));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> service.update(corrida));
		assertEquals("A data da corrida não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar pista com data diferente do ano do campeonato")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void updateDateDifCampTest() {
		ZonedDateTime data = ZonedDateTime.parse("2009-07-22T12:00:15Z");
		var corrida = new Corrida(1, data, pistaService.findById(1), campeonatoService.findById(1));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> service.update(corrida));
		assertEquals("O ano da corrida não pode ser diferente do ano do campeonato!", exception.getMessage());
	}

	@Test
	@DisplayName("Lista todos")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void listAllTest() {
		var lista = service.listAll();
		assertTrue(lista != null);
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("Lista todos vazio")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	void listAllErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há corridas cadastradas", exception.getMessage());
	}

	@Test
	@DisplayName("Teste delete corrida")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void deleteTest() {
		service.delete(1);
		var lista = service.listAll();
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste delete corrida inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(9));
		assertEquals("Corrida id 9 não existe", exception.getMessage());
	}
}
