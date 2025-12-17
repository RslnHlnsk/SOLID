package service;

import model.Product;

public class RatingService {
    // Установка рейтинга товара
    public void rateProduct(Product product, int rating) {
        product.setRating(rating);
    }
}
