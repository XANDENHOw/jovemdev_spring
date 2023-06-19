package br.com.trier.exemplospring.services;

import java.util.List;

import br.com.trier.exemplospring.domain.User;

public interface UserService {

	User salvar(User user);
	List<User> listAll();
	User findById(Integer id);
	User update(User user);
	void delete(Integer id);
	List<User> findByNameStartsWithIgnoreCase(String nome);
	
}
