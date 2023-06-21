package br.com.trier.exemplospring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Equipe;
import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.repositories.PilotoRepository;
import br.com.trier.exemplospring.services.PilotoService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoServiceImpl implements PilotoService{
	
	@Autowired
	private PilotoRepository repository;
	
	private void validarPiloto(Piloto piloto) {
		if(piloto.getName() == null) {
			throw new ViolacaoIntegridade("O nome do piloto não pode ser vazio");
		}
	}

	@Override
	public Piloto salvar(Piloto piloto) {
		validarPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public List<Piloto> listAll() {
		List<Piloto> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto cadastrado");
		}
		return lista;
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> 
		new ObjetoNaoEncontrado("Piloto id %s não existe".formatted(id)));
	}

	@Override
	public Piloto update(Piloto piloto) {
		findById(piloto.getId());
		validarPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Piloto piloto = findById(id);
		repository.delete(piloto);
	}

	@Override
	public List<Piloto> findByName(String name) {
		List<Piloto> lista = repository.findByName(name);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos cadastrados com esse nome");
		}
		return lista;
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> lista = repository.findByEquipe(equipe);
		if(lista.size() > 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos cadastrados nessa equipe");
		}
		return lista;
	}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> lista = repository.findByPais(pais);
		/*
		 * for (Piloto piloto : lista) { if(piloto.getPais() == pais) {
		 * lista.add(piloto); } }
		 */
		if(lista.size() > 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos cadastrados nesse país");
		}
		return lista;
	}

}
