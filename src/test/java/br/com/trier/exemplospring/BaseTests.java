package br.com.trier.exemplospring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.exemplospring.services.CampeonatoService;
import br.com.trier.exemplospring.services.EquipeService;
import br.com.trier.exemplospring.services.PaisService;
import br.com.trier.exemplospring.services.UserService;
import br.com.trier.exemplospring.services.impl.CampeonatoServiceImpl;
import br.com.trier.exemplospring.services.impl.EquipeServiceImpl;
import br.com.trier.exemplospring.services.impl.PaisServiceImpl;
import br.com.trier.exemplospring.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public PaisService paisService() {
		return new PaisServiceImpl();
	}
	@Bean
	public EquipeService equipeService() {
		return new EquipeServiceImpl();
	}
	@Bean
	public CampeonatoService campeonatoService() {
		return new CampeonatoServiceImpl();
	}
}
