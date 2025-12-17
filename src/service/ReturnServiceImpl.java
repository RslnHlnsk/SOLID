package service;

import model.Order;
import model.OrderStatus;

public class ReturnServiceImpl implements ReturnService {
    private final OrderService orderService;

    public ReturnServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean initiateReturn(int orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null && order.getStatus() == OrderStatus.PAID) {
            order.setStatus(OrderStatus.RETURNED);
            return true;
        }
        return false;
    }
}
