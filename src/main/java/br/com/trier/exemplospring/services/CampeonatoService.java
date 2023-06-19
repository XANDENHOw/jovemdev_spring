package br.com.trier.exemplospring.services;

import java.util.List;


import br.com.trier.exemplospring.domain.Campeonato;

public interface CampeonatoService {

	Campeonato salvar(Campeonato campeonato);
	List<Campeonato> listAll();
	Campeonato findById(Integer id);
	Campeonato update(Campeonato campeonato);
	void delete(Integer id);
	List<Campeonato> findByYearBetween(Integer start, Integer end);
	List<Campeonato> findByYear(Integer year);
	List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao);
	List<Campeonato> findByDescricaoContainsIgnoreCaseAndYearEquals(String descricao, Integer year);
	
}
