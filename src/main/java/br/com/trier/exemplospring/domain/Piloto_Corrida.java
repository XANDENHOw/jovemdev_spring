package br.com.trier.exemplospring.domain;

import br.com.trier.exemplospring.domain.dto.Piloto_CorridaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto_corrida")
public class Piloto_Corrida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto_corrida")
	@Setter
	private Integer id;
	
	@ManyToOne
	private Piloto piloto;
	
	@ManyToOne
	private Corrida corrida;
	
	@Column(name = "colocacao_piloto_corrida")
	private Integer colocacao;

	
	public Piloto_Corrida(Piloto_CorridaDTO dto, Piloto piloto, Corrida corrida) {
		this(dto.getId(), piloto, corrida, dto.getColocacao());
	}
	
	public Piloto_CorridaDTO toDTO() {
		return new Piloto_CorridaDTO(id, piloto.getId(), piloto.getName(), corrida.getId(), colocacao);
	}
}
