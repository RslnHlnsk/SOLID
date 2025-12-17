package repository;

import java.util.List;
import model.Product;

public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void addProduct(Product product);
}
