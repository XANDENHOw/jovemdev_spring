package br.com.trier.exemplospring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.User;
import br.com.trier.exemplospring.repositories.UserRepository;
import br.com.trier.exemplospring.services.UserService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	private void validaUser(User user) {
		if(user == null) {
			throw new ViolacaoIntegridade("O usuário não pode ser nulo");
		} else if(user.getName() == null) {
			throw new ViolacaoIntegridade("O nome não pode ser nulo");
		}
	}
	
	private void validaEmail(User user) {
		Optional<User> userOpt = repository.findByEmail(user.getEmail());
		if(userOpt.isPresent()) {
			User usuario = userOpt.get();
			if(user.getId() != usuario.getId()) {
				throw new ViolacaoIntegridade("Esse email já existe");
			}
		}
	}

	@Override
	public User salvar(User user) {
		validaUser(user);
		validaEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado!".formatted(id)));
	}

	@Override
	public User update(User user) {
		validaUser(user);
		validaEmail(user);
		User usuario = findById(user.getId());
		return repository.save(usuario);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public List<User> findByNameStartsWithIgnoreCase(String name) {
		List<User> lista = repository.findByNameStartsWithIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de usuário inicia com %s".formatted(name));
		}
		return lista;
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = repository.findByEmail(email);
		return user.orElseThrow(() -> new ObjetoNaoEncontrado("%s não encontrado!".formatted(email)));

	}

}
