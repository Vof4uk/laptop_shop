package ua.mykytenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mykytenko.entities.Order;
import ua.mykytenko.repositories.OrderRepository;
import ua.mykytenko.service.OrderService;
import ua.mykytenko.service.exceptions.FailedOperationException;
import ua.mykytenko.service.exceptions.NotFoundException;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository repository;

    @Override
    public Order getById(int id) {
        Order order = repository.getById(id);
        if(order == null){
            throw new NotFoundException("No such order");
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return repository.getAll();
    }

    @Override
    public void add(Order order) {
        if(!order.isNew()){
            throw new IllegalStateException("You cannot add not unique order");
        }
        try {
            repository.save(order);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to save new order.");
        }
    }

    @Override
    public void update(Order order) {
        if(order.isNew()){
            throw new java.lang.IllegalStateException("You cannot not update unsaved order. save it first");
        }
        try {
            repository.save(order);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to update order information.");
        }
    }

    @Override
    public void delete(Order order) {
        if(order == null){
            throw new IllegalStateException("You cannot delete null");
        }
        if(order.isNew()){
            throw new IllegalStateException("You cannot delete new order");
        }
        delete(order.getId());
    }

    public void delete(int id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new FailedOperationException("Failed to delete order");
        }
    }
}
