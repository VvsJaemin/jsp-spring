package ch08;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
	Map<String, Product> products = new HashMap<>();
	
	public ProductService() {
		Product p = new Product("101", "애플", "애플 냔", 12222110, "20120.1121.1");
		products.put("101", p);
		Product p1 = new Product("102", "삼성", "애플 냔", 12222110, "20120.1121.1");
		products.put("102", p1);
		Product p3 = new Product("103", "애플3", "애플 냔", 12222110, "20120.1121.1");
		products.put("103", p3);
	}
	
	public List<Product>findAll(){
		return new ArrayList<>(products.values());
	}
	
	public Product find(String id) {
		return products.get(id);
	}
}
