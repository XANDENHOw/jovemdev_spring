package br.com.trier.exemplospring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Pista;
import br.com.trier.exemplospring.repositories.PistaRepository;
import br.com.trier.exemplospring.services.PistaService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class PistaServiceImpl implements PistaService{

	@Autowired
	private PistaRepository repository;

	private void validarPista(Pista pista) {
		if(pista.getTamanho() == null || pista.getTamanho() <= 0) {
			throw new ViolacaoIntegridade("Tamanho inválido");
		}
	}
	
	@Override
	public Pista salvar(Pista pista) {
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public List<Pista> listAll() {
		List<Pista> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada");
		}
		return lista;
	}

	@Override
	public Pista findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> 
		new ObjetoNaoEncontrado("Pista id %s não existe".formatted(id)));
	}

	@Override
	public Pista update(Pista pista) {
		findById(pista.getId());
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public void delete(Integer id) {
		Pista pista = findById(id);
		repository.delete(pista);
	}


	@Override
	public List<Pista> findByTamanhoBetween(Integer tamMinimo, Integer tamMaximo) {
		List<Pista> lista = repository.findByTamanhoBetween(tamMinimo, tamMaximo);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada com esse tamanho");
		}
		return lista;
	}

	@Override
	public List<Pista> findByPais(Pais pais) {
		List<Pista> lista = repository.findByPais(pais);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada nesse país");
		}
		return lista;
	}

}
