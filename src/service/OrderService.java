package service;

import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Order;

public class OrderService {
    private int nextOrderId = 1;
    private final List<Order> orders = new ArrayList<>();

    public Order createOrder(Cart cart) {
        if (cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Корзина пуста");
        }
        Order order = new Order(nextOrderId++, cart.getProducts(), "В обработке");
        orders.add(order);
        return order;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public void updateOrderStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
    }
}
