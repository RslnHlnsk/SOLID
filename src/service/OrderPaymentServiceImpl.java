package service;

import model.*;

public class OrderPaymentServiceImpl implements OrderPaymentService {
    private final OrderService orderService;

    public OrderPaymentServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean payOrder(int orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null && order.getStatus() == OrderStatus.NEW) {
            order.setStatus(OrderStatus.PAID);
            return true;
        }
        return false;
    }
}
