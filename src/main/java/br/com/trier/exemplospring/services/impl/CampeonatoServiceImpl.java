package br.com.trier.exemplospring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.repositories.CampeonatoRepository;
import br.com.trier.exemplospring.services.CampeonatoService;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{

	@Autowired
	private CampeonatoRepository repository;
	
	@Override
	public Campeonato salvar(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato campeonato = findById(id);
		if(campeonato != null) {
			repository.delete(campeonato);
		}
		
	}

}