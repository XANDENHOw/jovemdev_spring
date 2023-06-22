package br.com.trier.exemplospring.domain;

import java.time.ZonedDateTime;

import br.com.trier.exemplospring.domain.dto.CorridaDTO;
import br.com.trier.exemplospring.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name= "corrida")
public class Corrida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	@Setter
	private Integer id;
	
	@Column(name = "data_corrida")
	private ZonedDateTime date;
	
	@ManyToOne
	@NotNull
	private Pista pista;
	
	@ManyToOne
	@NotNull
	private Campeonato campeonato;
	
	public Corrida (CorridaDTO dto) {
		this(dto.getId(),
				DateUtils.strToZoneDateTime(dto.getDate()),
				new Pista(dto.getIdPista(), null, null),
				new Campeonato(dto.getId(), dto.getDescricaoCampeonato(), null));
	}
	
	public Corrida (CorridaDTO dto, Campeonato campeonato, Pista pista) {
		this(dto.getId(),
				DateUtils.strToZoneDateTime(dto.getDate()),
				pista,
				campeonato);
	}
	
	public CorridaDTO toDto() {
		return new CorridaDTO(id, DateUtils.zonedDateTimeToStr(date),
				pista.getId(), campeonato.getId(), campeonato.getDescricao());
	}
	
}
