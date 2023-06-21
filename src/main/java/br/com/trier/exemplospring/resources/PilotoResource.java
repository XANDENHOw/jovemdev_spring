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

import br.com.trier.exemplospring.domain.Piloto;
import br.com.trier.exemplospring.services.EquipeService;
import br.com.trier.exemplospring.services.PaisService;
import br.com.trier.exemplospring.services.PilotoService;

@RestController
@RequestMapping(value = "/pilotos")
public class PilotoResource {

	@Autowired
	PilotoService service;
	
	@Autowired
	PaisService paisService;

	@Autowired
	EquipeService equipeService;
	
	@PostMapping
	public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto){
		paisService.findById(piloto.getPais().getId());
		equipeService.findById(piloto.getEquipe().getId());
		return ResponseEntity.ok(service.salvar(piloto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Piloto> findById(@RequestBody Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Piloto> update(@PathVariable Integer id, @RequestBody Piloto piloto){
		paisService.findById(piloto.getPais().getId());
		equipeService.findById(piloto.getEquipe().getId());
		return ResponseEntity.ok(service.update(piloto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Piloto> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Piloto>> findByName(@PathVariable String name) {
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Piloto>> findByPais(@PathVariable Integer idPais) {
		return ResponseEntity.ok(service.findByPais(paisService.findById(idPais)));
	}

	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<Piloto>> findByEquipe(@PathVariable Integer idEquipe) {
		return ResponseEntity.ok(service.findByPais(paisService.findById(idEquipe)));
	}
}
