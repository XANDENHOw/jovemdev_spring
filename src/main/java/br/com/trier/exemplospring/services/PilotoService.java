package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.Equipe;
import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Piloto;

public interface PilotoService {
	
	Piloto salvar(Piloto piloto);
	List<Piloto> listAll();
	Piloto findById(Integer id);
	Piloto update(Piloto piloto);
	void delete(Integer id);
	List<Piloto> findByName(String name);
	List<Piloto> findByEquipe(Equipe equipe);
	List<Piloto> findByPais(Pais pais);
	
}
