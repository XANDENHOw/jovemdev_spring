package br.com.trier.exemplospring.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CorridaPaisAnoDTO {
	
	private Integer ano;
	private String pais;
	private List<CorridaDTO> corridas;
	
}
