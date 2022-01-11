package br.com.southsystem.desafio_2front.exceptions;

public class ThereAreNoProductsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ThereAreNoProductsException(String msg) {
		super(msg);
	}

}
