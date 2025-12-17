package service;

import model.OrderStatus;

public interface DeliveryTrackingService {
    OrderStatus getDeliveryStatus(int orderId);
    void setDeliveryStatus(int orderId, OrderStatus status);
}