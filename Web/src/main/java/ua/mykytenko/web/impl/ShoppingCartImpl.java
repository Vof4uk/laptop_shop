package ua.mykytenko.web.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import ua.mykytenko.entities.Account;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.Order;
import ua.mykytenko.entities.OrderPosition;
import ua.mykytenko.web.ShoppingCart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//@Component("shoppingCart")
//@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
public class ShoppingCartImpl implements ShoppingCart {

    public ShoppingCartImpl(Account account) {
        this.account = account;
    }

    public ShoppingCartImpl() {
    }

    private Map<Laptop, Integer> orderList = new HashMap<>();
    private Account account;
    private Order order;

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public Map<Laptop, Integer> getOrderList() {
        return orderList;
    }



    @Override
    public synchronized void addItem(Laptop laptop) {
       addItems(laptop, 1);
    }

    @Override
    public synchronized void addItems(Laptop laptop, int amount) {
        orderList.compute(laptop, (laptop1, integer) -> integer == null ? amount : integer + amount);
    }

    @Override
    public synchronized void removeItem(Laptop laptop) {
        if(orderList.get(laptop) != null) {

        if (orderList.get(laptop) <= 1) {
                orderList.remove(laptop);
            } else {
                orderList.compute(laptop, (laptop1, integer) -> integer - 1);
            }
        }

    }

    @Override
    public synchronized void removeItemsOfType(Laptop laptop) {
        orderList.remove(laptop);
    }

    @Override
    public synchronized Order makeOrder() {
        Order order = new Order();
        order.setAccount(account);
        List<OrderPosition> positions =
                orderList.entrySet().stream().
                        map(entry -> {
                            OrderPosition op = new OrderPosition();
                            op.setLaptop(entry.getKey());
                            op.setOrder(order);
                            op.setQuantity(entry.getValue());
                            return op;
                        })
                        .collect(Collectors.toList());
        order.setOrderPositions(positions);
        this.order = order;
        return order;
    }

    @Override
    public synchronized int getItemsCount() {
        return orderList.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce((i1, i2) -> i1 + i2)
                .orElse(0);
    }

    @Override
    public boolean isReady(){
        return !orderList.isEmpty();
    }

    @Override
    public void clear() {
        this.order = null;
        this.orderList = new HashMap<>();
    }

    @Override
    public Order getOrder() {
        if(order == null){
            makeOrder();
        }
        return order;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }
}
