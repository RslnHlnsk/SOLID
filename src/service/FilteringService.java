package service;

import java.util.List;
import java.util.stream.Collectors;
import model.Product;

public class FilteringService {
    // Применяет фильтр по ключевому слову в названии
    public List<Product> filterByKeyword(List<Product> products, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // фильтр по диапазону цен
    public List<Product> filterByPriceRange(List<Product> products, double minPrice, double maxPrice) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // фильтр по производителю
    public List<Product> filterByManufacturer(List<Product> products, String manufacturer) {
        String lowerManuf = manufacturer.toLowerCase();
        return products.stream()
                .filter(p -> p.getManufacturer().toLowerCase().equals(lowerManuf))
                .collect(Collectors.toList());
    }
}
