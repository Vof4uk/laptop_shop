package ua.mykytenko.businesslayer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.mykytenko.businesslayer.BankAccount;
import ua.mykytenko.businesslayer.CardManager;
import ua.mykytenko.businesslayer.LaptopManager;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.Order;
import ua.mykytenko.entities.OrderPosition;
import ua.mykytenko.service.LaptopService;
import ua.mykytenko.service.OrderService;

import java.util.List;

@Service
public class LaptopManagerImpl implements LaptopManager {

    private static final BankAccount BANK_ACCOUNT = new BankAccount(123L);

    private final LaptopService laptopService;

    private final CardManager cardManager;

    private final OrderService orderService;

    @Autowired
    public LaptopManagerImpl(LaptopService laptopService, CardManager cardManager, OrderService orderService) {
        this.laptopService = laptopService;
        this.cardManager = cardManager;
        this.orderService = orderService;
    }


    @Transactional
    public int sellOrder(Order order, BankAccount bankAccount) {
        List<OrderPosition> positions = order.getOrderPositions();
        for (OrderPosition op : positions) {
            Laptop laptop = laptopService.getById(op.getLaptop().getId());
            int stock = laptop.getStock() - op.getQuantity();
            if (stock < 0) {
                throw new RuntimeException("stock is not enough");
            }
            laptop.setStock(stock);
            laptopService.update(laptop);
            op.setLaptop(laptop);
        }
        order.setPaid(true);
        order.setOrderPositions(positions);
        orderService.add(order);
        if (!cardManager.doMoneyTransaction(BANK_ACCOUNT, bankAccount, order.getTotalCost())) {
            throw new RuntimeException(" money transaction denied");
        }
        return order.getId();
    }

    @Override
    public boolean confirmStock(Order order) {
        boolean orderWasCorrect = true;
        List<OrderPosition> positions = order.getOrderPositions();
        for (OrderPosition op : positions) {
            Laptop laptop = laptopService.getById(op.getLaptop().getId());
            int stock = laptop.getStock();
            if (stock - op.getQuantity() < 0) {
                orderWasCorrect = false;
                op.setQuantity(laptop.getStock());
            } else if (!op.getLaptop().getPrice().equals(laptop.getPrice())) {
                orderWasCorrect = false;
                op.setLaptop(laptop);
            }
        }
        return orderWasCorrect;
    }

    @Override
    @Transactional
    public void receiveLaptops(Laptop laptop, int quantity) {
        receiveLaptops(laptop.getId(), quantity);
    }

    @Override
    @Transactional
    public void removeLaptops(Laptop laptop, int quantity) {
        receiveLaptops(laptop.getId(), quantity);
    }

    @Override
    @Transactional
    public void receiveLaptops(int id, int quantity) {
        Laptop laptop = laptopService.getById(id);
        laptop.setStock(laptop.getStock() + quantity);
        laptopService.update(laptop);
    }

    @Override
    public void removeLaptops(int id, int quantity) {
        Laptop laptop = laptopService.getById(id);
        int newStock = laptop.getStock() - quantity;
        if (newStock >= 0) {
            laptopService.update(laptop);
        } else {
            throw new RuntimeException("stock is not enough to remove " + quantity + " laptops.");
        }
    }
}