package br.com.trier.exemplospring.services.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Equipe;
import br.com.trier.exemplospring.repositories.EquipeRepository;
import br.com.trier.exemplospring.services.EquipeService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class EquipeServiceImpl implements EquipeService {

	@Autowired
	EquipeRepository repository;

	private void validaNome(Equipe equipe) {
		Optional<Equipe> equipeOpt = repository.findByName(equipe.getName());
		if(equipeOpt.isPresent()) {
			Equipe equipeValida = equipeOpt.get();
			if(equipe.getId() != equipeValida.getId()) {
				throw new ViolacaoIntegridade("Equipe já cadastrada");
			}
		}
	}

	@Override
	public Equipe salvar(Equipe equipe) {
		validaNome(equipe);
		return repository.save(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		return repository.findAll();
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada!".formatted(id)));
	}

	@Override
	public Equipe update(Equipe obj) {
		validaNome(obj);
		Equipe equipe = findById(obj.getId());
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
			repository.delete(equipe);
	}

	@Override
	public List<Equipe> findByName(String name) {
		List<Equipe> lista = repository.findByNameContainsIgnoreCase(name);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de equipe inicia com %s".formatted(name));
		}
		return lista;
	}
	
	@Override
	public List<Equipe> findByNameContainsIgnoreCase(String name) {
		return repository.findByNameContainsIgnoreCase(name);
	}

}
