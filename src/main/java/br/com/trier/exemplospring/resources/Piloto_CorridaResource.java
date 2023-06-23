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

import br.com.trier.exemplospring.domain.Piloto_Corrida;
import br.com.trier.exemplospring.domain.dto.Piloto_CorridaDTO;
import br.com.trier.exemplospring.services.CorridaService;
import br.com.trier.exemplospring.services.PilotoService;
import br.com.trier.exemplospring.services.Piloto_CorridaService;

@RestController
@RequestMapping(value = "/pilotos_corridas")
public class Piloto_CorridaResource {
	
	@Autowired
	Piloto_CorridaService service;
	
	@Autowired
	PilotoService pilotoService;
	
	@Autowired
	CorridaService corridaService;
	
	@PostMapping
	public ResponseEntity<Piloto_CorridaDTO> insert (@RequestBody Piloto_CorridaDTO dto){
		return ResponseEntity.ok(service.insert(new Piloto_Corrida(dto,
				pilotoService.findById(dto.getIdPiloto()),
				corridaService.findById(dto.getIdCorrida())))
				.toDTO());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Piloto_CorridaDTO> findById(@RequestBody Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto_CorridaDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream()
				.map((piloto_Corrida) -> piloto_Corrida.toDTO())
				.toList());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Piloto_CorridaDTO> update (@PathVariable Integer id, @RequestBody Piloto_CorridaDTO dto){
		return ResponseEntity.ok(service.insert(new Piloto_Corrida(dto,
				pilotoService.findById(dto.getIdPiloto()),
				corridaService.findById(dto.getIdCorrida())))
				.toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/piloto/{idPiloto}")
	public ResponseEntity<List<Piloto_CorridaDTO>> findByPiloto(@PathVariable Integer idPiloto){
		return ResponseEntity.ok(service.findByPiloto(pilotoService.findById(idPiloto)).stream()
				.map((piloto_Corrida) -> piloto_Corrida.toDTO())
				.toList());
	}

	@GetMapping("/corrida/{idCorrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> findByCorrida(@PathVariable Integer idCorrida){
		return ResponseEntity.ok(service.findByCorrida(corridaService.findById(idCorrida)).stream()
				.map((piloto_Corrida) -> piloto_Corrida.toDTO())
				.toList());
	}
	
}
