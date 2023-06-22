package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Pista;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer>{

	List<Pista> findByTamanhoBetween(Integer tamMinimo, Integer tamMaximo);
	List<Pista> findByTamanho(Integer tamanho);
	List<Pista> findByPais(Pais pais);
	
}
