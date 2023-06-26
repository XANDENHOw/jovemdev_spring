package br.com.trier.exemplospring.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.trier.exemplospring.repositories.UserRepository;

@Component
public class JwtUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		br.com.trier.exemplospring.domain.User user = repository.findByEmail(email).orElseThrow(null);
		return User.builder()
				.username(user.getName())
				.password(encoder.encode(user.getPassword()))
				.roles(user.getRole().split(","))
				.build();
	}

}
