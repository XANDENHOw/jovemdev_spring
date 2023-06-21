package br.com.trier.exemplospring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Pista;
import br.com.trier.exemplospring.repositories.CorridaRepository;
import br.com.trier.exemplospring.services.CorridaService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class CorridaServiceImpl implements CorridaService{

	@Autowired
	private CorridaRepository repository;
	
	private void validaCorrida(Corrida corrida) {
		if(corrida.getDate() == null) {
			throw new ViolacaoIntegridade("A data da corrida não pode ser nula");
		}
	}
	
	@Override
	public Corrida salvar(Corrida corrida) {
		validaCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public Corrida update(Corrida corrida) {
		findById(corrida.getId());
		validaCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> 
		new ObjetoNaoEncontrado("Corrida id %s não existe".formatted(id)));
	}

	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
		repository.delete(corrida);
	}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> lista = repository.findByPista(pista);
		if(lista.size() > 0) {
			throw new ObjetoNaoEncontrado("Não há corridas cadastradas nessa pista");
		}
		return lista;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> lista = repository.findByCampeonato(campeonato);
		if(lista.size() > 0) {
			throw new ObjetoNaoEncontrado("Não há corridas cadastradas nessa pista");
		}
		return lista;
	}

	@Override
	public List<Corrida> listAll() {
		List<Corrida> lista = repository.findAll();
		if(lista.size() > 0) {
			throw new ObjetoNaoEncontrado("Não há corridas cadastradas");
		}
		return lista;
	}

}
