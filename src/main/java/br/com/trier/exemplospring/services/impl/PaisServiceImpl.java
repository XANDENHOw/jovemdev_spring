package br.com.trier.exemplospring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.repositories.PaisRepository;
import br.com.trier.exemplospring.services.PaisService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;

@Service
public class PaisServiceImpl implements PaisService{

	@Autowired
	PaisRepository repository;
	
	

	@Override
	public Pais salvar(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public List<Pais> listAll() {
		List<Pais> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum pais encontrado");
		}
		return lista;
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("País %s não encontrado!".formatted(id)));
	}

	@Override
	public Pais update(Pais obj) {
		Pais pais = findById(obj.getId());
		findByName(pais.getName());
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		repository.delete(pais);	
	}

	@Override
	public List<Pais> findByName(String name) {
		List<Pais> lista =  repository.findByNameIgnoreCase(name);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de país inicia com %s".formatted(name));
		}
		return lista;
	}

	@Override
	public List<Pais> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}
	
	@Override
	public List<Pais> findByNameContains(String name) {
		return repository.findByNameContains(name);
	}
}
