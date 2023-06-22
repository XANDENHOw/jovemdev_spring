package br.com.trier.exemplospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Pista;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTests{
	
	@Autowired
	PistaService pistaService;
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdTest() {
		var pista = pistaService.findById(1);
		assertTrue(pista != null);
		assertEquals(50, pista.getTamanho());
		assertEquals(1, pista.getPais().getId());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findById(10));
		assertEquals("Pista id 10 não existe", exception.getMessage());
	} 
	
	@Test
	@DisplayName ("Teste busca por pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisTest() {
		Pais paisTeste = new Pais(1,"Brasil");
		var lista = pistaService.findByPais(paisTeste);
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisNoExistTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findByPais(paisService.findById(3)));
		assertEquals("Nenhuma pista cadastrada nesse país", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoTest() {
		var lista = pistaService.findByTamanho(7);
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getPais().getId());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho não existente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoNoExistTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findByTamanho(9));
		assertEquals("Nenhuma pista cadastrada com esse tamanho", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho entre intervalo")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoBetweenTest() {
		var lista = pistaService.findByTamanhoBetween(6, 11);
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(1).getPais().getId());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho entre intervalo Vazio")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoBetweenEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findByTamanhoBetween(100, 120));
		assertEquals("Nenhuma pista cadastrada com esse tamanho", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void listAllTest() {
		var lista = pistaService.listAll();
		assertTrue(lista != null);
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Lista todos vazio")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.listAll());
		assertEquals("Nenhuma pista cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste cadastra pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void cadastraPistaTest() {
		var pista = new Pista(null,paisService.findById(1),6);
		pistaService.salvar(pista);
		var pistaNova = pistaService.findById(1);
		assertEquals(6, pistaNova.getTamanho());
		assertEquals("Brasil", pistaNova.getPais().getName());
	}
	
	@Test
	@DisplayName("Teste cadastra pista tamanho invalido")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void cadastraPistaTamanhoErrorTest() {
		var pista = new Pista(null,paisService.findById(1),0);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> pistaService.salvar(pista));
		assertEquals("Tamanho inválido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste cadastra pista tamanho nulo")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void cadastraPistaTamanhoNullTest() {
		var pista = new Pista(null,paisService.findById(1),null);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> pistaService.salvar(pista));
		assertEquals("Tamanho inválido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste update pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void updatePistaTest() {
		var pista = new Pista(1,paisService.findById(1),6);
		pistaService.update(pista);
		var pistaNova = pistaService.findById(1);
		assertEquals(6, pistaNova.getTamanho());
		assertEquals("Brasil", pistaNova.getPais().getName());
	}
	
	@Test
	@DisplayName("Teste delete pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deletePistaTest() {
		pistaService.delete(1);
		var lista = pistaService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste delete pista inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deletePistaNoExistTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.delete(9));
		assertEquals("Pista id 9 não existe", exception.getMessage());
	}
}