package br.com.southsystem.desafio_2front.application;

import java.util.Locale;

import br.com.southsystem.desafio_2front.frontend.CenterFacade;
import br.com.southsystem.desafio_2front.frontend.UserInteraction;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		CenterFacade facade = new CenterFacade();
		
		int option = 0;
		boolean logged = false;
		System.out.println("Bem-vindo à Loja Online\n\n");
		
		while(option != 8) {
			while (!logged) {
				logged = facade.makeLogin(logged);
			}
			option = UserInteraction.showMainOptions();
			facade.mainMenu(option);
		}

	}

}
