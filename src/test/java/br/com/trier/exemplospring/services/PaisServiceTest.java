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
import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{

	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste busca país por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findById() {
		var pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());		
	}
	
	@Test
	@DisplayName("Teste busca país por ID que não existe")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdInexist() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findById(10));
		assertEquals("País 10 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir país")
	void insertPais() {
		var pais = new Pais(null, "Belgica");
		paisService.salvar(pais);
		pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Belgica", pais.getName());		
	}
	
	@Test
	@DisplayName ("Teste inserir pais nome repetido")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void salvarPaisNameErrorTest() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> paisService.salvar(new Pais(null,"Brasil")));
		assertEquals("Pais já cadastrado: Brasil", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste update país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePais() {
		var pais = new Pais(1, "Belgica");
		paisService.update(pais);
		paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Belgica", pais.getName());
	}
	
	@Test
	@DisplayName ("Teste update pais com nome ja existente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisNameExistTest() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> paisService.update(new Pais(2,"Brasil")));
		assertEquals("Pais já cadastrado: Brasil", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste update pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisNonExistentrTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.update(new Pais(4,"Equador")));
		assertEquals("País 4 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deleta país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePais() {
		paisService.delete(2);
		List<Pais> lista = paisService.listAll();
		assertEquals(2, lista.size());
		assertEquals(3, lista.get(1).getId());
	}
	
	@Test
	@DisplayName("Teste deleta país inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePaisInexist() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.delete(20));
		assertEquals("País 20 não encontrado!", exception.getMessage());

		List<Pais> lista = paisService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca país por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllPaises() {
		List<Pais> lista = paisService.listAll();
		assertThat(lista).isNotNull();
		assertEquals(3, lista.size());
		assertEquals("França", lista.get(1).getName());		
	}
	
	@Test
	@DisplayName ("Busca por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameTest() {
		var lista = paisService.findByNameIgnoreCase("brasil");
		assertEquals(1, lista.size());
		assertEquals("Brasil", lista.get(0).getName());
		lista = paisService.findByNameContains("a");
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameNonExistentTest() {
		var lista = paisService.findByNameIgnoreCase("USA");
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listaTodosPaisesTest() {
		var lista = paisService.listAll();
		assertThat(lista != null);
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Lista todos vazio")
	void listaTodosPaisesNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.listAll());
		assertEquals("Nenhum pais encontrado", exception.getMessage());
	}
	
	
}
