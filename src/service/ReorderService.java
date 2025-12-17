package service;

import model.Order;

public interface ReorderService {
    Order repeatOrder(int oldOrderId);
}
