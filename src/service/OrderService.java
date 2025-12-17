package service;

import model.Order;
import model.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class OrderService {
    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();
    private int currentId = 1;

    public Order createOrder(List<Product> products) {
        Order order = new Order(currentId++, products);
        orders.put(order.getId(), order);
        return order;
    }

    public Order getOrderById(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAllOrders() {
        return List.copyOf(orders.values());
    }

    public int generateOrderId() {
        return currentId++;
    }

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
    }
}
