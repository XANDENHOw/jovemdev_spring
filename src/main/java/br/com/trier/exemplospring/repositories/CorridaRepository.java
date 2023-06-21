package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Pista;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer>{

	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);
	
}
