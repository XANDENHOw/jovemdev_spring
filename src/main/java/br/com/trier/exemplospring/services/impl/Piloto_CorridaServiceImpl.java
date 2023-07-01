package br.com.trier.exemplospring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.domain.Piloto_Corrida;
import br.com.trier.exemplospring.repositories.Piloto_CorridaRepository;
import br.com.trier.exemplospring.services.Piloto_CorridaService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class Piloto_CorridaServiceImpl implements Piloto_CorridaService{

	@Autowired
	private Piloto_CorridaRepository repository;
	
	
	private void validaCadastro(Piloto_Corrida piloto_Corrida) {
		if(piloto_Corrida == null) {
			throw new ViolacaoIntegridade("Não pode cadastrar com valores nulos");
		}
		if(piloto_Corrida.getColocacao() == null || piloto_Corrida.getColocacao() == 0) {
			throw new ViolacaoIntegridade("O piloto precisa de uma colocação na corrida");
		}
	}
	
	@Override
	public Piloto_Corrida insert(Piloto_Corrida pilotoCorrida) {
		validaCadastro(pilotoCorrida);
		return repository.save(pilotoCorrida);
	}

	@Override
	public Piloto_Corrida update(Piloto_Corrida pilotoCorrida) {
		findById(pilotoCorrida.getId());
		return repository.save(pilotoCorrida);
	}

	@Override
	public void delete(Integer id) {
		Piloto_Corrida pilotoCorrida = findById(id);
		repository.delete(pilotoCorrida);
	}

	@Override
	public List<Piloto_Corrida> listAll() {
		List<Piloto_Corrida> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não existem dados cadastrados");
		}
		return lista;
	}

	@Override
	public Piloto_Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> 
		new ObjetoNaoEncontrado("Piloto_Corrida id %s não existe".formatted(id)));
	}

	@Override
	public List<Piloto_Corrida> findByPiloto(Piloto piloto) {
		List<Piloto_Corrida> lista = repository.findByPiloto(piloto);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não existem pilotos cadastrados nessa corrida");
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByCorrida(Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByCorrida(corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Corrida %s não cadastrada".formatted(corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacao(Integer colocacao) {
		List<Piloto_Corrida> lista = repository.findByColocacao(colocacao);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Colocação %s não encontrada".formatted(colocacao));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoAndCorrida(Integer colocacao, Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoAndCorrida(colocacao, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel encontrar a colocação %s na corrida %s".formatted(colocacao, corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoBetweenAndCorrida(Integer colocacao1, Integer colocacao2,Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel achar resultado entre as colocações %s e %s na corrida %s".formatted(colocacao1, colocacao2, corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoLessThanEqualAndCorrida(Integer colocacao, Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoLessThanEqualAndCorrida(colocacao, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel encontrar a colocação menor ou igual a %s na corrida %s".formatted(colocacao, corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoGreaterThanEqualAndCorrida(Integer colocacao, Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoLessThanEqualAndCorrida(colocacao, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel encontrar a colocação maior ou igual a %s na corrida %s".formatted(colocacao, corrida.getId()));
		}
		return lista;
	}

}
