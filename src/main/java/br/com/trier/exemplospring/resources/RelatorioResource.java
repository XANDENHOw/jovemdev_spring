package br.com.trier.exemplospring.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.domain.Corrida;
import br.com.trier.exemplospring.domain.Pais;
import br.com.trier.exemplospring.domain.dto.CorridaDTO;
import br.com.trier.exemplospring.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.exemplospring.services.CampeonatoService;
import br.com.trier.exemplospring.services.CorridaService;
import br.com.trier.exemplospring.services.PaisService;
import br.com.trier.exemplospring.services.PistaService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping("/relatorios")
public class RelatorioResource {

	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PistaService pistaService;
	
	@Autowired
	private CorridaService corridaService;
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@GetMapping("/corridas-por-pais-ano/{paisId}/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByPaisAndAno(@PathVariable Integer paisId,
			@PathVariable Integer ano){
		Pais pais = paisService.findById(paisId);
		List<CorridaDTO> corridaDTOs = pistaService.findByPais(pais).stream()
				.flatMap(pista -> {
					try {
						return corridaService.findByPista(pista).stream();
					}catch (ObjetoNaoEncontrado e) {
						return Stream.empty();
					}
				}).filter(corrida -> corrida.getDate().getYear() == ano)
				.map(Corrida::toDto)
				.toList();
		return ResponseEntity.ok(new CorridaPaisAnoDTO(ano, pais.getName(), corridaDTOs));
	}
	
	@GetMapping("/corridas-por-pais/{idPais}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByPais(@PathVariable Integer idPais){
		Pais pais = paisService.findById(idPais);
		List<CorridaDTO> corridaDTOs = pistaService.findByPais(pais).stream()
				.flatMap(pista -> {
					try {
						return corridaService.findByPista(pista).stream();
					}catch (ObjetoNaoEncontrado e) {
						return Stream.empty();
					}
				}).map(Corrida::toDto)
				.toList();
		return ResponseEntity.ok(new CorridaPaisAnoDTO(null ,pais.getName(), corridaDTOs));
	}
	
	@GetMapping("corridas-por-ano/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByAno(@PathVariable Integer ano){
		Campeonato campeonato = (Campeonato) campeonatoService.findByYear(ano);
		List<CorridaDTO> corridaDTOs = corridaService.findByCampeonato(campeonato).stream()
				.filter(corrida -> corrida.getDate().getYear() == ano)
				.map(Corrida::toDto)
				.toList();;
		
		return ResponseEntity.ok(new CorridaPaisAnoDTO(ano ,null, corridaDTOs));

	}
}
