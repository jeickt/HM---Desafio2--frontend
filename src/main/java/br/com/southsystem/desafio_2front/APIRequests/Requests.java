package br.com.southsystem.desafio_2front.apirequests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.southsystem.desafio_2front.domain.entities.Category;
import br.com.southsystem.desafio_2front.domain.entities.Product;
import br.com.southsystem.desafio_2front.domain.entities.User;

public class Requests {

	private final static String URL_PRODUCTS = "http://localhost:8080/products/";
	private final static String URL_CATEGORIES = "http://localhost:8080/categories/";

	private static String jwtToken = null;

	public static int performLogin(String json) {
		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(json))
				.uri(URI.create("http://localhost:8080/login")).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());

			Gson gson = new GsonBuilder().create();
			User user = gson.fromJson(response.body(), User.class);

			jwtToken = user.getToken();
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a operação de login: " + e.getMessage());
		}
		return 400;
	}

	public static List<Product> performGetAllProducts() {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(URL_PRODUCTS))
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a busca por produtos:" + e.getMessage());
		}
		Gson gson = new GsonBuilder().create();
		List<Product> products = gson.fromJson(response.body(), new TypeToken<List<Product>>() {
		}.getType());

		return products;
	}

	public static Product performGetProductById(String id) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(URL_PRODUCTS + id))
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a busca de produto por id: " + e.getMessage());
		}
		Gson gson = new GsonBuilder().create();
		Product product = gson.fromJson(response.body(), Product.class);

		return product;
	}

	public static int performPostProduct(String productStr) {
		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(productStr))
				.uri(URI.create(URL_PRODUCTS)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a adição de produto: " + e.getMessage());
		}
		return 400;
	}

	public static int performPostFileWithProducts(String path) throws IOException {
		Path csvFile = Paths.get(path);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofFile(csvFile))
				.uri(URI.create(URL_PRODUCTS + "file?fileName=csvFile")).header("Authorization", "Bearer " + jwtToken)
				.timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a adição de produtos por arquivo: " + e.getMessage());
		}
		return 400;
	}

	public static int performUpdateProduct(Long id, String updatedProductStrWithCategory) {
		HttpRequest request = HttpRequest.newBuilder().PUT(BodyPublishers.ofString(updatedProductStrWithCategory))
				.uri(URI.create(URL_PRODUCTS + id)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a edição de produto: " + e.getMessage());
		}
		return 400;
	}

	public static int performDeleteProduct(Long id) {
		HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(URL_PRODUCTS + id))
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a exclusão de produto: " + e.getMessage());
		}
		return 400;
	}

	public static List<Category> performGetAllCategories() {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(URL_CATEGORIES))
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a busca por categorias: " + e.getMessage());
		}
		Gson gson = new GsonBuilder().create();
		List<Category> categories = gson.fromJson(response.body(), new TypeToken<List<Category>>() {
		}.getType());

		return categories;
	}

	public static Category performGetCategoryById(String id) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(URL_CATEGORIES + id))
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a busca de categoria por id:" + e.getMessage());
		}
		Gson gson = new GsonBuilder().create();
		Category category = gson.fromJson(response.body(), Category.class);

		return category;
	}

	public static int performPostCategory(String categoryStr) {
		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(categoryStr))
				.uri(URI.create(URL_CATEGORIES)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a adição de categoria: " + e.getMessage());
		}
		return 400;
	}

	public static int performUpdateCategory(Long id, String updatedCategory) {
		HttpRequest request = HttpRequest.newBuilder().PUT(BodyPublishers.ofString(updatedCategory))
				.uri(URI.create(URL_CATEGORIES + id)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a edição de categoria: " + e.getMessage());
		}
		return 400;
	}

	public static int performDeleteCategory(Long id) {
		HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(URL_CATEGORIES + id))
				.header("Authorization", "Bearer " + jwtToken).timeout(Duration.ofSeconds(3)).build();

		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			System.out.println("Erro ao realizar a exclusão de categoria: " + e.getMessage());
		}
		return 400;
	}

}
