package br.com.southsystem.desafio_2front.frontend;

public class UserController {
	
	public static String userDataToLogin() {
		System.out.println("Por favor, informe seus dados de acesso. ");
		String username = UserInteraction.readNextFromUser("Informe seu username: ");
		String password = UserInteraction.readNextFromUser("Informe sua senha: ");
		return "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
	}

}
