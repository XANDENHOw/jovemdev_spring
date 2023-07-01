package br.com.trier.exemplospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.exemplospring.BaseTests;
import br.com.trier.exemplospring.domain.Piloto_Corrida;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class Piloto_CorridaServiceTest extends BaseTests{

	@Autowired 
	Piloto_CorridaService pilotoCorridaService;
	@Autowired 
	PilotoService pilotoService;
	@Autowired 
	CorridaService corridaService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByIdTest() {
		var piloto_corrida = pilotoCorridaService.findById(1);
		assertTrue(piloto_corrida != null);
		assertEquals(1, piloto_corrida.getPiloto().getId());
	}
	
	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findById(10));
		assertEquals("O piloto/corrida 10 não foi cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByCorridaTest() {
		var lista = pilotoCorridaService.findByCorrida(corridaService.findById(1));
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por corrida inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByCorridaNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByCorrida(corridaService.findById(4)));
		assertEquals("Corrida 4 não cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByPilotoTest() {
		var lista = pilotoCorridaService.findByPiloto(pilotoService.findById(1));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por piloto inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByPilotoNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByPiloto(pilotoService.findById(6)));
		assertEquals("Piloto 6 não cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoTest() {
		var lista = pilotoCorridaService.findByColocacao(1);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByColocacao(10));
		assertEquals("Colocação 10 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao e corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoECorridaTest() {
		var lista = pilotoCorridaService.findByColocacaoAndCorrida(1, corridaService.findById(1));
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao e corrida erro")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoECorridaErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByColocacaoAndCorrida(1, corridaService.findById(4)));
		assertEquals("Não foi possivel encontrar a colocação 1 na corrida 4", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao entre colocacao na corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoEntreTest() {
		var lista = pilotoCorridaService.findByColocacaoBetweenAndCorrida(1, 2, corridaService.findById(1));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao entre colocacao na corrida erro")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoEntreErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByColocacaoBetweenAndCorrida(1, 2, corridaService.findById(4)));
		assertEquals("Não foi possivel achar resultado entre as colocações 1 e 2 na corrida 4", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por colocar maior ou igual na corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoMaiorIgualTest() {
		var lista = pilotoCorridaService.findByColocacaoGreaterThanEqualAndCorrida(2, corridaService.findById(1));
		assertEquals(2, lista.size());
		assertEquals(2, lista.get(1).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por colocar maior ou igual na corrida erro")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoMaiorIgualErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByColocacaoGreaterThanEqualAndCorrida(2, corridaService.findById(4)));
		assertEquals("Não foi possivel encontrar a colocação maior ou igual a 2 na corrida 4", exception.getMessage());
	}
	
	
	
	@Test
	@DisplayName ("Teste busca por colocar menor ou igual na corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoMenorIgualTest() {
		var lista = pilotoCorridaService.findByColocacaoLessThanEqualAndCorrida(1, corridaService.findById(1));
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por colocar menor ou igual na corrida erro")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoMenorIgualErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.findByColocacaoLessThanEqualAndCorrida(2, corridaService.findById(4)));
		assertEquals("Não foi possivel encontrar a colocação menor ou igual a 2 na corrida 4", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Lista todos vazio")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void listAllErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.listAll());
		assertEquals("Nenhum piloto/corrida cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void listAllTest() {
		var lista = pilotoCorridaService.listAll();
		assertTrue(lista != null);
		assertEquals(6, lista.size());
	}
	
	@Test
	@DisplayName ("Teste cadastra piloto_corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void cadastraTest() {
		var piloto_corrida = new Piloto_Corrida(1,pilotoService.findById(1), corridaService.findById(2), 3);
		pilotoCorridaService.insert(piloto_corrida);
		var newPilotoCorrida = pilotoCorridaService.findById(1);
		assertEquals(3, newPilotoCorrida.getColocacao());
	}
	
	@Test
	@DisplayName ("Teste update piloto_corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void updateTest() {
		var piloto_corrida = new Piloto_Corrida (1,pilotoService.findById(1), corridaService.findById(2), 3);
		pilotoCorridaService.update(piloto_corrida);
		var newPilotoCorrida = pilotoCorridaService.findById(1);
		assertEquals(3, newPilotoCorrida.getColocacao());
	}
	
	@Test
	@DisplayName ("Teste delete piloto_corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void deleteTest() {
		pilotoCorridaService.delete(1);
		var lista = pilotoCorridaService.listAll();
		assertEquals(5, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete piloto_corrida inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoCorridaService.delete(10));
		assertEquals("O piloto/corrida 10 não foi cadastrado", exception.getMessage());
	}
	
}
