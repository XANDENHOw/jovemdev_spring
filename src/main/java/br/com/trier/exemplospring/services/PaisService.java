package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.Pais;

public interface PaisService {

	Pais salvar(Pais pais);
	List<Pais> listAll();
	Pais findById(Integer id);
	Pais update(Pais pais);
	void delete(Integer id);
	List<Pais> findByName(String nome);
}
