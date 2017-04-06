package ua.mykytenko.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.mykytenko.entities.Order;
import ua.mykytenko.repositories.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional
public class OrderRepositoryImpl implements OrderRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Order getById(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return entityManager.createNamedQuery(Order.GET_ALL, Order.class).getResultList();
    }

    @Override
    public Order save(Order order) {
        if(order.isNew()) {
            entityManager.persist(order);
        }else {
            return entityManager.merge(order);
        }
        LOGGER.info("Saving order id= " + order.getId());
        return order;
    }

    @Override
    public boolean deleteById(int id) {
        LOGGER.warn("deleting order by id =" + id);
        Order order = entityManager.getReference(Order.class, id);
        entityManager.remove(order);
        return true;
    }
}
