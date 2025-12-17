package service;

import java.util.List;
import java.util.stream.Collectors;
import repository.ProductRepository;
import model.Product;

// Простая рекомендация — будут рекомендованы товары того же производителя, за исключением выбранного товара, п ример Apple
public class RecommendationService {
    private final ProductRepository repository;

    public RecommendationService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getRecommendations(Product currentProduct) {
        return repository.getAllProducts().stream()
                .filter(p -> p.getManufacturer().equals(currentProduct.getManufacturer()) && p.getId() != currentProduct.getId())
                .collect(Collectors.toList());
    }
}
