package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.domain.Piloto_Corrida;

@Repository
public interface Piloto_CorridaRepository extends JpaRepository<Piloto_Corrida, Integer> {

	List<Piloto_Corrida> findByPiloto(Piloto piloto);

	List<Piloto_Corrida> findByCorrida(Corrida corrida);

	List<Piloto_Corrida> findByColocacao(Integer colocacao);

	List<Piloto_Corrida> findByColocacaoBetweenAndCorrida(Integer colocacao1, Integer colocacao2, Corrida corrida);

	List<Piloto_Corrida> findByColocacaoLessThanEqualAndCorrida(Integer colocacao, Corrida corrida);

	List<Piloto_Corrida> findByColocacaoGreaterThanEqualAndCorrida(Integer colocacao, Corrida corrida);

	List<Piloto_Corrida> findByColocacaoAndCorrida(Integer colocacao, Corrida corrida);
}
