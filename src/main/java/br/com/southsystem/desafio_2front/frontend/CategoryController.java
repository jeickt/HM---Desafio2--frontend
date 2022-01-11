package br.com.southsystem.desafio_2front.frontend;

import br.com.southsystem.desafio_2front.domain.entities.Category;

public class CategoryController {
	
	public static String setNewCategoryString() {
		String  name = UserInteraction.readNextLineFromUser("Informe o nome da categoria: ");
		
		return "{\"name\": \"" + name + "\"}";
	}
	
	public static String setAtributesCategoryString(Category category) {
		String newName = null;
		
		int option = UserInteraction.showCategoryEditOptions();
		while (option != 2) {
			switch(option) {
			case 1: newName = UserInteraction.readNextLineFromUser("Informe o novo nome da categoria: ");
			default:
			}
			if (option != 2) {
				if (UserInteraction.conclude("Finalizar edição (S=Sim / N=Não)? ")) {
					option = 2;
				} else {
					option = UserInteraction.showCategoryEditOptions();
				}
			}
		}
		return "{\"name\": \"" + newName + "\"}";
	}
	

	


}
