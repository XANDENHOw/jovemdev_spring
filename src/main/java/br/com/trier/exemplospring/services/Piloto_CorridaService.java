package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.domain.Piloto_Corrida;

public interface Piloto_CorridaService {

	Piloto_Corrida insert(Piloto_Corrida pilotoCorrida);

	Piloto_Corrida update(Piloto_Corrida pilotoCorrida);

	void delete(Integer id);

	List<Piloto_Corrida> listAll();

	Piloto_Corrida findById(Integer id);

	List<Piloto_Corrida> findByPiloto(Piloto piloto);
	
	List<Piloto_Corrida> findByCorrida(Corrida corrida);
	
	List<Piloto_Corrida> findByColocacao(Integer colocacao);
	
	List<Piloto_Corrida> findByColocacaoBetweenAndCorrida(Integer colocacao1, Integer colocacao2, Corrida corrida);
	
	List<Piloto_Corrida> findByColocacaoLessThanEqualAndCorrida(Integer colocacao, Corrida corrida);
	
	List<Piloto_Corrida> findByColocacaoGreaterThanEqualAndCorrida(Integer colocacao, Corrida corrida);
	
	List<Piloto_Corrida> findByColocacaoAndCorrida(Integer colocacao, Corrida corrida);
}
