package br.com.trier.exemplospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.services.CampeonatoService;
import br.com.trier.exemplospring.services.CorridaService;
import br.com.trier.exemplospring.services.PistaService;

@RestController
@RequestMapping(value = "/corridas")
public class CorridaResource {
	
	@Autowired
	CorridaService service;
	
	@Autowired
	PistaService pistaService;

	@Autowired
	CampeonatoService campeonatoService;
	
	@PostMapping
	public ResponseEntity<Corrida> insert (@RequestBody Corrida corrida) {
		pistaService.findById(corrida.getPista().getId());
		campeonatoService.findById(corrida.getCampeonato().getId());
		return ResponseEntity.ok(service.salvar(corrida));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Corrida> findById(@RequestBody Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Corrida>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Corrida> update(@PathVariable Integer id, @RequestBody Corrida corrida){
		pistaService.findById(corrida.getPista().getId());
		campeonatoService.findById(corrida.getCampeonato().getId());
		return ResponseEntity.ok(service.update(corrida));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Corrida> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/pais/{idPista}")
	public ResponseEntity<List<Corrida>> findByPista(@PathVariable Integer idPista) {
		return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)));
	}

	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<Corrida>> findByEquipe(@PathVariable Integer idCampeonato) {
		return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)));
	}

}
