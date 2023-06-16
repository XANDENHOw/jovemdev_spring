package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.Equipe;

public interface EquipeService {
	
	Equipe salvar(Equipe equipe);
	List<Equipe> listAll();
	Equipe findById(Integer id);
	Equipe update(Equipe equipe);
	void delete(Integer id);
	
}
