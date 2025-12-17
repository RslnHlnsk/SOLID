package model;

import java.util.List;

public class Order {
    private int orderId;
    private List<Product> products;
    private OrderStatus status;

    public Order(int id, List<Product> products) {
        this.orderId = id;
        this.products = products;
        this.status = OrderStatus.NEW;
    }

    public int getId() {
        return orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Заказ #%d, Статус: %s, Товары: %s", orderId, status, products);
    }
}
