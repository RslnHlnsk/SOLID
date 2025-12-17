package model;

import java.util.List;

public class Order {
    private final int orderId;
    private final List<Product> products;
    private String status; // "В обработке", "Доставляется", "Завершен"

    public Order(int orderId, List<Product> products, String status) {
        this.orderId = orderId;
        this.products = products;
        this.status = status;
    }

    public int getOrderId() { return orderId; }
    public List<Product> getProducts() { return products; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Заказ #%d, Статус: %s, Товары: %s", orderId, status, products);
    }
}
