package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Pista;

public interface CorridaService {

	Corrida salvar(Corrida corrida);
	Corrida update(Corrida corrida);
	Corrida findById(Integer id);
	void delete(Integer id);
	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);
	List<Corrida> listAll();
	
}
