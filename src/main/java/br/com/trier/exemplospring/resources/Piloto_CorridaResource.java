package br.com.trier.exemplospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Piloto;
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
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Piloto_CorridaDTO> insert (@RequestBody Piloto_CorridaDTO piloto_corridaDTO){
		return ResponseEntity.ok(service.insert(new Piloto_Corrida(
				piloto_corridaDTO, 
				pilotoService.findById(piloto_corridaDTO.getIdPiloto()), 
				corridaService.findById(piloto_corridaDTO.getIdCorrida())))
				.toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<Piloto_CorridaDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping ("/{id}")
	public ResponseEntity<Piloto_CorridaDTO> buscaPorId(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/piloto/{piloto}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorPiloto (@PathVariable Integer id_piloto){
		return ResponseEntity.ok(service.findByPiloto(pilotoService.findById(id_piloto)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/corrida/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorCorrida (@PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByCorrida(corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/colocacao/{colocacao}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacao (@PathVariable Integer colocacao){
		return ResponseEntity.ok(service.findByColocacao(colocacao).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/colocacao/entre/{colocacao1}/{colocacao2}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoEntre (@PathVariable Integer colocacao1, @PathVariable Integer colocacao2, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/colocacao/menor/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoMenor (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoLessThanEqualAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/colocacao/maior/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoMaior (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoGreaterThanEqualAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/colocacao/corrida/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoCorrida (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<Piloto_CorridaDTO> update (@PathVariable Integer id, @RequestBody Piloto_CorridaDTO piloto_corridaDTO){
		Corrida corrida = corridaService.findById(piloto_corridaDTO.getIdCorrida());
		Piloto piloto = pilotoService.findById(piloto_corridaDTO.getIdPiloto());
		Piloto_Corrida pilotoCorrida = new Piloto_Corrida(piloto_corridaDTO, piloto, corrida);
		pilotoCorrida.setId(id);
		return ResponseEntity.ok(service.update(pilotoCorrida).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
