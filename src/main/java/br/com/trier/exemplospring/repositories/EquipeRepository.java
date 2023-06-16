package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer>{

	List<Equipe> findByName(String name);
}
