package ua.mykytenko.repositories;

import ua.mykytenko.entities.Order;

import java.util.List;

public interface OrderRepository {
    Order getById(int id);
    List<Order> getAll();
    Order save(Order order);
    boolean deleteById(int id);
}