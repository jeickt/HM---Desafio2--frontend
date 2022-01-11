package br.com.southsystem.desafio_2front.frontend;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInteraction {
	
	static Scanner sc = new Scanner(System.in);
	
	public static int showMainOptions() {
		String msg = "Escolha uma das opções:\n"
				+ "[1] - Adicionar novo produto\n"
				+ "[2] - Editar produto\n"
				+ "[3] - Excluir produto\n"
				+ "[4] - Importar mostruário de fábrica\n"
				+ "[5] - Adicionar nova categoria\n"
				+ "[6] - Editar categoria\n"
				+ "[7] - Excluir categoria\n"
				+ "[8] - Sair\n";
		return readOption(msg, 1, 8);
	}
	
	static int showProductEditOptions() {
		String msg = "Escolha uma das opções para editar:\n"
				+ "[1] - Editar nome do produto\n"
				+ "[2] - Editar preço do produto\n"
				+ "[3] - Editar quantidade do produto\n"
				+ "[4] - Alterar categoria do produto\n"
				+ "[5] - Cancelar\n";
		return readOption(msg, 1, 5);
	}
	
	static int showCategoryEditOptions() {
		String msg = "Escolha uma das opções para editar:\n"
				+ "[1] - Editar nome do produto\n"
				+ "[2] - Cancelar\n";
		return readOption(msg, 1, 2);
	}
	
	static int readOption(String msg, int min, int max) {
		int option = readIntFromUser(msg);
		while (option < min || option > max) {
			System.out.println("Opção inválida. ");
			option = readIntFromUser(msg);
		}
		return option;
	}
	
	static boolean conclude(String msg) {
		char option = readNextFromUser(msg).toUpperCase().charAt(0);
		while (option != 'S' && option != 'N') {
			System.out.println("Opção inválida. ");
			option = readNextLineFromUser(msg).toUpperCase().charAt(0);
		}
		return option == 'S' ? true : false;
	}

	static String readNextLineFromUser(String msg) {
		String string = "";
		while("".equals(string)) {
			System.out.print(msg);
			try {
				sc.nextLine();
				string = sc.nextLine();
				if (!"".equals(string)) {
					return string;				
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("Informação inválida.");
			} 
		}
		return string;
	}
	
	static String readNextFromUser(String msg) {
		String string = "";
		while("".equals(string)) {
			System.out.print(msg);
			try {
				string = sc.next();
			} catch (InputMismatchException e) {
				System.out.println("Informação inválida.");
			} 
		}
		return string;
	}
	
	static int readIntFromUser(String msg) {
		int num = 0;
		while(num == 0) {
			System.out.print(msg);
			try {
				num = sc.nextInt();
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Informação inválida.");
			}
		}
		return num;
	}
	
	static double readDoubleFromUser(String msg) {
		double value = -1d;
		while(value == -1) {
			System.out.print(msg);
			try {
				String string = sc.next();
				value = Double.parseDouble(string.replace(',', '.'));
			} catch (InputMismatchException | NumberFormatException  e) {
				System.out.println("Informação inválida.");
			}
		}
		return value;
	}

}
