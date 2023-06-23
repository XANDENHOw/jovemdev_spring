package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.domain.Piloto_Corrida;

@Repository
public interface Piloto_CorridaRepository extends JpaRepository<Piloto_Corrida, Integer>{

	List<Piloto_Corrida> findByPiloto(Piloto piloto);
	List<Piloto_Corrida> findByCorrida(Corrida corrida);
}
