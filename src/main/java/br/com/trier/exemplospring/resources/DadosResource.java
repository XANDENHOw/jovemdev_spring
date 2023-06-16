package br.com.trier.exemplospring.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.exemplospring.domain.Dice;

@RestController
@RequestMapping(value = "/dados")
public class DadosResource {
	
	@GetMapping
	public ResponseEntity<String> sortear(@RequestParam(name = "qtdado") Integer qtDado,
			@RequestParam Double aposta) {
		
		Integer menorValorDado = 1;
		Integer maiorValorDado = 6;
		Double menorValorAposta = (double) qtDado * menorValorDado;
		Double maiorValorAposta = (double) qtDado * maiorValorDado;

		if(qtDado < 1 || qtDado > 4) {
			return ResponseEntity.badRequest().body("A quantidade de dados deve estar em 1 e 4!");
		}
		
		if(aposta < menorValorAposta || aposta > maiorValorAposta) {
			return ResponseEntity.badRequest().body("Valor da aposta inválido");
		}
		
		List<Integer> dados = new ArrayList<>();
		Random sorteio = new Random();
		
		for(int i = 0; i < qtDado; i++) {
			int valorDado = sorteio.nextInt(maiorValorDado - menorValorDado + 1) + menorValorDado;
			dados.add(valorDado);
		}
		
		Integer somaValorDados = dados.stream().mapToInt(Integer :: intValue).sum();
		Double percentualAposta = aposta / somaValorDados  * 100;
		Dice result = new Dice(dados, somaValorDados, percentualAposta);
		String resultado = "Número de dados: " + result.getDados() + "\n"
				+ "Valor da soma dos dados: " + result.getSomaValorDados() + "\n"
				+ "Percentual aposta: " + percDif(aposta, somaValorDados) * 100;
		return ResponseEntity.ok(resultado);
	}
	
	private double percDif(Double n1, int n2) {
		double diferenca = Math.abs(n1 - n2);
		return (diferenca / Math.max(n1, n2));
	}

}
