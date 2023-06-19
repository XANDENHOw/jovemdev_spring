package br.com.trier.exemplospring.services.exceptions;

public class ViolacaoIntegridade extends RuntimeException{
	
	public ViolacaoIntegridade(String mensagem) {
		super(mensagem);
	}

}
