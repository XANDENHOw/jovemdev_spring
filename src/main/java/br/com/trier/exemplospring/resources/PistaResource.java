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

import br.com.trier.exemplospring.domain.Pista;
import br.com.trier.exemplospring.services.PaisService;
import br.com.trier.exemplospring.services.PistaService;

@RestController
@RequestMapping(value = "/pistas")
public class PistaResource {
	
	@Autowired
	private PistaService service;
	
	@Autowired
	private PaisService paisService;
	
	@PostMapping
	public ResponseEntity<Pista> insert(@RequestBody Pista pista) {
		paisService.findById(pista.getPais().getId());
		return ResponseEntity.ok(service.salvar(pista));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pista> findByCodigo(@RequestBody Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Pista>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista){
		paisService.findById(pista.getPais().getId());
		pista.setId(id);
		return ResponseEntity.ok(service.update(pista));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Pista> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/tamanho/{tamMinimo}/{tamMaximo}")
	public ResponseEntity<List<Pista>> buscaPorTamanhoBetween(@PathVariable Integer tamMinimo,
			@PathVariable Integer tamMaximo) {
		return ResponseEntity.ok(service.findByTamanhoBetween(tamMinimo, tamMaximo));
	}
	
	@GetMapping("/tamanho/{tamano}")
	public ResponseEntity<List<Pista>> buscaPorTamanho(@PathVariable Integer tamanho) {
		return ResponseEntity.ok(service.findByTamanho(tamanho));
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Pista>> findByPais(@PathVariable Integer idPais) {
		return ResponseEntity.ok(service.findByPais(paisService.findById(idPais)));
	}
	
}
