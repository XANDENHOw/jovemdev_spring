package br.com.trier.exemplospring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Entity(name = "piloto")
public class Piloto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto")
	@Setter
	private Integer id;
	
	@Column(name = "nome_piloto")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "pais_id_piloto")
	private Pais pais;
	
	@ManyToOne
	@JoinColumn(name = "equipe_id_piloto")
	private Equipe equipe;

}
