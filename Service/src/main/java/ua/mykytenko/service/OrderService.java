package ua.mykytenko.service;

import ua.mykytenko.entities.Order;

import java.util.List;

public interface OrderService {
    void delete(Order account);
    void update(Order account);
    void add(Order account);
    Order getById(int id);
    List<Order> getAll();
}
