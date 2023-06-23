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
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não existem corridas cadastradas com esses pilotos");
		}
		return lista;
	}

}
