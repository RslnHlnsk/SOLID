package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Product;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public InMemoryProductRepository() {
        // добавим тестовые товары
        products.add(new Product(1, "PlayStation", "Sony", 499));
        products.add(new Product(2, "Xbox X", "Microsoft", 499));
        products.add(new Product(3, "NintendoS", "Nintendo", 299));
        products.add(new Product(4, "MacBook Pro", "Apple", 1299));
        products.add(new Product(5, "Dell 27", "Dell", 999));
        products.add(new Product(6, "iPhone 17 Pro", "Apple", 2299));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> p = products.stream().filter(prod -> prod.getId() == id).findFirst();
        return p.orElse(null);
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }
}
