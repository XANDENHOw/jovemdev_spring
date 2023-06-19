package br.com.trier.exemplospring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.repositories.PaisRepository;
import br.com.trier.exemplospring.services.PaisService;

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
		return repository.findAll();
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public Pais update(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		if(pais != null) {
			repository.delete(pais);
		}
		
	}

	@Override
	public List<Pais> findByName(String nome) {
		return repository.findByNameContainsIgnoreCase(nome);
	}
	
	
}
