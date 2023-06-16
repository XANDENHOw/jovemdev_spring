package br.com.trier.exemplospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.exemplospring.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findByName(String Nome);
	
}