package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer>{

	List<Pais> findByName(String Nome);
	
}