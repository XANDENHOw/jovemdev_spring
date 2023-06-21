package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Equipe;
import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Piloto;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer>{

	List<Piloto> findByName(String name);
	List<Piloto> findByPais(Pais pais);
	List<Piloto> findByEquipe(Equipe equipe);
}
