package service;

import model.Order;
import model.OrderStatus;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeliveryTrackingServiceImpl implements DeliveryTrackingService {
    private final Map<Integer, OrderStatus> deliveryStatuses = new ConcurrentHashMap<>();
    private final OrderService orderService;

    public DeliveryTrackingServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderStatus getDeliveryStatus(int orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            if (order.getStatus() == OrderStatus.PAID) {
                return deliveryStatuses.getOrDefault(orderId, OrderStatus.SHIPPED);
            }
            return order.getStatus(); // чистый статус
        }
        return null;
    }

    @Override
    public void setDeliveryStatus(int orderId, OrderStatus status) {
        // Проверяем, что заказ существует
        if (orderService.getOrderById(orderId) != null) {
            deliveryStatuses.put(orderId, status);
            // Обновляем статус
            Order order = orderService.getOrderById(orderId);
            if (order != null) {
                order.setStatus(status);
            }
        } else {
            System.out.println("Заказ с ID " + orderId + " не найден");
        }
    }
}
