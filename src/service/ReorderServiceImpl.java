package service;

import model.Order;
import model.OrderStatus;

public class ReorderServiceImpl implements ReorderService {
    private final OrderService orderService;

    public ReorderServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    //@Override
    //public Order repeatOrder(int oldOrderId) {
    //    Order oldOrder = orderService.getOrderById(oldOrderId);
        //if (oldOrder != null) {
            //Order newOrder = new Order(orderService.generateOrderId(), oldOrder.getProducts());
            //newOrder.setStatus(OrderStatus.NEW);
            //orderService.addOrder(newOrder);
            //return newOrder;
        //}
        //return null;
    //}

    @Override
    public Order repeatOrder(int oldOrderId) {
        Order oldOrder = orderService.getOrderById(oldOrderId);
        if (oldOrder != null) {
            Order newOrder = orderService.createOrder(oldOrder.getProducts());
            newOrder.setStatus(OrderStatus.NEW);
            return newOrder;
        }
        return null;
    }

}

