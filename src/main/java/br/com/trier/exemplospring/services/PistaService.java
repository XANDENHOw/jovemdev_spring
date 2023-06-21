package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.Pista;

public interface PistaService {
	
	Pista salvar(Pista pista);
	List<Pista> listAll();
	Pista findById(Integer id);
	Pista update(Pista pista);
	void delete(Integer id);
	List<Pista> findByTamanhoBetween(Integer tamMinimo, Integer tamMaximo);
	List<Pista> findByPais(Pais pais);
}
