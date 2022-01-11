package br.com.southsystem.desafio_2front.frontend;

import java.io.IOException;
import java.util.List;

import br.com.southsystem.desafio_2front.apirequests.Requests;
import br.com.southsystem.desafio_2front.domain.entities.Category;
import br.com.southsystem.desafio_2front.domain.entities.Product;
import br.com.southsystem.desafio_2front.exceptions.ThereAreNoCategoriesException;
import br.com.southsystem.desafio_2front.exceptions.ThereAreNoProductsException;

public class CenterFacade {
	
	public boolean makeLogin(boolean logged) {
		String json = UserController.userDataToLogin();
		int result = Requests.performLogin(json);
		if (result == 200) {
			System.out.println("Login efetuado com sucesso.\n");
			return true;
		}
		System.out.println("Login não efetuado.\n");
		return false;
	}

	public void mainMenu(int option) {
		switch (option) {
		case 1:
			System.out.println(addProduct());
			clearScreen();
			break;
		case 2:
			System.out.println(updateProduct());
			clearScreen();
			break;
		case 3:
			System.out.println(deleteProduct());
			clearScreen();
			break;
		case 4:
			System.out.println(importProducts());
			clearScreen();
			break;
		case 5:
			System.out.println(addCategory());
			clearScreen();
			break;
		case 6:
			System.out.println(updateCategory());
			clearScreen();
			break;
		case 7:
			System.out.println(deleteCategory());
			clearScreen();
			break;
		default:
			System.out.println("Aplicação encerrada.");
			clearScreen();
		}
	}

	private String addProduct() {
		boolean optionAboutCategoryInsert = UserInteraction.conclude("Você deseja adicionar também a categoria do produto (S=Sim / N=Não)? ");
		String productStr = null;
		if (optionAboutCategoryInsert) {
			productStr = ProductController.setNewProductStringWithCategory();
		} else {
			productStr = ProductController.setNewProductString();
		}
		
		boolean confirm = UserInteraction.conclude("Confirmar a adição do produto (S=Sim / N=Não)? ");
		if (confirm) {
			Integer result = Requests.performPostProduct(productStr);
			if (result == 201) {
				return "Produto adicionado com sucesso.";				
			} else {
				return "Falha na adição do produto";
			}
		} else {
			return "Adição de produto cancelada";
		}
	}

	private String updateProduct() {
		List<Product> products = Requests.performGetAllProducts();
		Product product;
		try {
			product = viewProductsAndSelectOne(products);
		} catch (ThereAreNoProductsException e) {
			return e.getMessage();
		}
		if (product == null) {
			return "";
		}

		Long id = product.getId();
		String updatedProductStrWithCategory = ProductController.setAtributesProductWithCategoryString(product);
		boolean confirm = UserInteraction.conclude("Confirmar a edição do produto (S=Sim / N=Não)? ");
		if (confirm) {
			int result = Requests.performUpdateProduct(id, updatedProductStrWithCategory);
			if (result == 200) {
				return "Produto editado com sucesso.";				
			} else {
				return "Falha na edição do produto";
			}
		} else {
			return "Edição de produto cancelada";
		}
	}

	private String deleteProduct() {
		List<Product> products = Requests.performGetAllProducts();
		Product product;
		try {
			product = viewProductsAndSelectOne(products);
		} catch (ThereAreNoProductsException e) {
			return e.getMessage();
		}
		if (product == null) {
			return "";
		}

		Long id = product.getId();
		boolean confirm = UserInteraction.conclude("Confirmar a exclusão do produto (S=Sim / N=Não)? ");
		if (confirm) {
			int result = Requests.performDeleteProduct(id);
			if (result == 204) {
				return "Produto excluído com sucesso.";				
			} else if (result == 403) {
				return "Exclusão de produto não autorizada para o usuário.";
			} else {
				return "Falha na exclusão de produto.";
			}
		} else {
			return "Exclusão de produto cancelada.";
		}
	}

	private String importProducts() {
		String path = UserInteraction.readNextFromUser("Digite o caminho do arquivo para importação: ");
		boolean confirm = UserInteraction.conclude("Confirmar a importação de produtos (S=Sim / N=Não)? ");

		if (confirm) {
			int result;
			try {
				result = Requests.performPostFileWithProducts(path);
			} catch (IOException e) {
				return "Arquivo não encontrado.";
			}
			if (result == 204) {
				return "Importação de produtos realizada com sucesso.";
			} else {
				return "Erro na importação de produtos.";
			}
		} else {
			return "Importação de produtos cancelada.";
		}
	}
	
	private String addCategory() {
		String categoryStr = CategoryController.setNewCategoryString();
		
		boolean confirm = UserInteraction.conclude("Confirmar a adição da categoria (S=Sim / N=Não)? ");
		if (confirm) {
			Integer result = Requests.performPostCategory(categoryStr);
			if (result == 201) {
				return "Categoria adicionada com sucesso.";				
			} else {
				return "Falha na adição da categoria";
			}
		} else {
			return "Adição de categoria cancelada";
		}
	}
	
	private String updateCategory() {
		List<Category> categories = Requests.performGetAllCategories();
		Category category;
		try {
			category = viewCategoriesAndSelectOne(categories);
		} catch (ThereAreNoCategoriesException e) {
			return e.getMessage();
		}
		if (category == null) {
			return "";
		}

		Long id = category.getId();
		String updatedCategory = CategoryController.setAtributesCategoryString(category);

		boolean confirm = UserInteraction.conclude("Confirmar a edição da categoria (S=Sim / N=Não)? ");
		if (confirm) {
			int result = Requests.performUpdateCategory(id, updatedCategory);
			if (result == 200) {
				return "Categoria editada com sucesso.";				
			} else {
				return "Falha na edição da categoria";
			}
		} else {
			return "Edição de categoria cancelada.";
		}
	}
	
	private String deleteCategory() {
		List<Category> categories = Requests.performGetAllCategories();
		Category category;
		try {
			category = viewCategoriesAndSelectOne(categories);
		} catch (ThereAreNoCategoriesException e) {
			return e.getMessage();
		}
		if (category == null) {
			return "";
		}

		Long id = category.getId();
		boolean confirm = UserInteraction.conclude("Confirmar a exclusão do categoria (S=Sim / N=Não)? ");
		if (confirm) {
			int result = Requests.performDeleteCategory(id);
			if (result == 204) {
				return "Categoria excluída com sucesso.";				
			} else if (result == 400) {
				return "Exclusão de categoria não autorizada para o usuário.";
			} else {
				return "Falha na exclusão de categoria.";
			}
		} else {
			return "Exclusão de categoria cancelada.";
		}
	}

	private Product viewProductsAndSelectOne(List<Product> products) throws ThereAreNoProductsException {
		if (products == null || products.size() == 0) {
			throw new ThereAreNoProductsException("Não constam produtos cadastrados.\n");
		}

		System.out.println();
		for (int i = 1; i <= products.size(); i++) {
			System.out.println("[" + i + "] para selecionar => " + products.get(i - 1));
		}
		System.out.println("[" + (products.size() + 1) + "] para cancelar a operação.");

		int selected = UserInteraction.readOption("Digite o número do produto a ser selecionado: ", 1,
				products.size() + 1);
		if (selected == products.size() + 1) {
			System.out.println("Operação cancelada.");
			return null;
		}
		return Requests.performGetProductById(Long.toString(products.get(selected-1).getId()));
	}
	
	private Category viewCategoriesAndSelectOne(List<Category> categories) throws ThereAreNoCategoriesException {
		if (categories == null || categories.size() == 0) {
			throw new ThereAreNoCategoriesException("Não constam categorias cadastradas.\n");
		}

		System.out.println();
		for (int i = 1; i <= categories.size(); i++) {
			System.out.println("[" + i + "] para selecionar => " + categories.get(i - 1));
		}
		System.out.println("[" + (categories.size() + 1) + "] para cancelar a operação.");

		int selected = UserInteraction.readOption("Digite o número da categoria a ser selecionada: ", 1,
				categories.size() + 1);
		if (selected == categories.size() + 1) {
			System.out.println("Operação cancelada.");
			return null;
		}
		return Requests.performGetCategoryById(Long.toString(categories.get(selected-1).getId()));
	}

	private void clearScreen() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println("\n\n\n\n\n\n\n\n\n\n");
	}

}
