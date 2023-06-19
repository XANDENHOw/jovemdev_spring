package br.com.trier.exemplospring.services.exceptions;


public class ObjetoNaoEncontrado extends RuntimeException{

	public ObjetoNaoEncontrado(String mensagem) {
		super(mensagem);
	}
}
