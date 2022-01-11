package br.com.southsystem.desafio_2front.exceptions;

public class ThereAreNoCategoriesException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ThereAreNoCategoriesException(String msg) {
		super(msg);
	}

}
