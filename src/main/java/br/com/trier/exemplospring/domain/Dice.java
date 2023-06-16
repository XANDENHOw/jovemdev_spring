package br.com.trier.exemplospring.domain;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dice {
	private List<Integer> dados;
	private int somaValorDados;
	private double percentualAposta;
}
