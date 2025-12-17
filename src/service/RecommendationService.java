package service;

import java.util.List;
import java.util.stream.Collectors;
import repository.ProductRepository;
import model.Product;

public class RecommendationService {
    private final ProductRepository repository;

    public RecommendationService(ProductRepository repository) {
        this.repository = repository;
    }

    // Простая рекомендация — товары от того же производителя, исключая текущий
    public List<Product> getRecommendations(Product currentProduct) {
        return repository.getAllProducts().stream()
                .filter(p -> p.getManufacturer().equals(currentProduct.getManufacturer()) && p.getId() != currentProduct.getId())
                .collect(Collectors.toList());
    }
}
