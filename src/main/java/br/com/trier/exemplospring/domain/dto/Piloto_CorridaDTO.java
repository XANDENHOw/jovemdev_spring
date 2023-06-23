package br.com.trier.exemplospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Piloto_CorridaDTO {
	
	private Integer id;
	private Integer idPiloto;
	private String namePiloto;
	private Integer idCorrida;
	private Integer colocacao;

}
