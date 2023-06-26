package br.com.trier.exemplospring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findByNameStartsWithIgnoreCase(String Nome);
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);
	
}
