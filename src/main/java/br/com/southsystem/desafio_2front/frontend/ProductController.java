package br.com.southsystem.desafio_2front.frontend;

import java.util.HashMap;
import java.util.Map;

import br.com.southsystem.desafio_2front.domain.entities.Product;
import br.com.southsystem.desafio_2front.exceptions.NegativeValueException;

public class ProductController {
	
	public static String setNewProductString() {
		String name = UserInteraction.readNextLineFromUser("Informe o nome do produto: ");
		String price = Double.toString(getPrice("Informe o seu preço: "));
		String quantity = Integer.toString(getQuantity("Informe sua quantidade em estoque: "));
		return "{\"name\": \"" + name + "\", \"price\": \"" + price + "\", " 
		+ "\"quantity\": \"" + quantity + "\"}";
	}
	
	public static String setNewProductStringWithCategory() {
		String name = UserInteraction.readNextLineFromUser("Informe o nome do produto: ");
		String price = Double.toString(getPrice("Informe o seu preço: "));
		String quantity = Integer.toString(getQuantity("Informe sua quantidade em estoque: "));
		String categoryId = UserInteraction.readNextFromUser("Informe o id da categoria do produto: ");
		return "{\"name\": \"" + name + "\", \"price\": \"" + price + "\", " 
				+ "\"quantity\": \"" + quantity + "\", \"category\": {\"id\": " + categoryId + "}}";
	}
	
	public static String setAtributesProductWithCategoryString(Product product) {
		Map<String, String> updatedProductStr = new HashMap<>();
		updatedProductStr.put("name", product.getName());
		updatedProductStr.put("price", Double.toString(product.getPrice()));
		updatedProductStr.put("quantity", Integer.toString(product.getQuantity()));
		String categoryId = Long.toString(product.getCategory().getId());
		
		int option = UserInteraction.showProductEditOptions();
		while (option != 5) {
			switch(option) {
			case 1: updatedProductStr = updateName(updatedProductStr); break;
			case 2: updatedProductStr = updatePrice(updatedProductStr); break;
			case 3: updatedProductStr = updateQuantity(updatedProductStr); break;
			case 4: categoryId = updateCategory(); break;
			default:
			}
			if (option != 5) {
				if (UserInteraction.conclude("Finalizar edição (S=Sim / N=Não)? ")) {
					option = 5;
				} else {
					option = UserInteraction.showProductEditOptions();
				}
			}
		}
		return "{\"name\": \"" + updatedProductStr.get("name") + "\", \"price\": \"" + updatedProductStr.get("price") + "\", "
		+ "\"quantity\": \"" + updatedProductStr.get("quantity") + "\", \"category\": {\"id\": " + categoryId + "}}";
	}
	
	private static double getPrice(String msg) {
		double price = -1d;
		while (price < 0) {
			try {
				price = UserInteraction.readDoubleFromUser(msg);
				if (price < 0) {
					throw new NegativeValueException("O preço não pode ser negativo.");
				}
			} catch (NegativeValueException e) {
				System.out.println(e.getMessage());
			}
		}
		return price;
	}
	
	private static int getQuantity(String msg) {
		int quantity = -1;
		while (quantity < 0) {
			try {
				quantity = UserInteraction.readIntFromUser(msg);
				if (quantity < 0) {
					throw new NegativeValueException("A quantidade não pode ser negativa.");
				}
			} catch (NegativeValueException e) {
				System.out.println(e.getMessage());;
			}
		}
		return quantity;
	}
	
	private static Map<String, String> updateName(Map<String, String> updatedProductStr) {
		updatedProductStr.put("name", UserInteraction.readNextLineFromUser("Informe o novo nome do produto: "));
		return updatedProductStr;
	}
	
	private static Map<String, String> updatePrice(Map<String, String> updatedProductStr) {
		updatedProductStr.put("price", Double.toString(getPrice("Informe o seu novo preço: ")));
		return updatedProductStr;
	}
	
	private static Map<String, String> updateQuantity(Map<String, String> updatedProductStr) {
		updatedProductStr.put("quantity", Integer.toString(getQuantity("Informe quantos items há no estoque: ")));
		return updatedProductStr;
	}
	
	private static String updateCategory() {
		String categoryId = UserInteraction.readNextFromUser("Informe a nova categoria do produto: ");
		return categoryId;
	}

}
