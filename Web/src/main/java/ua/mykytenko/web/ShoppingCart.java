package ua.mykytenko.web;

import ua.mykytenko.entities.Account;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.Order;

import java.util.Map;

public interface ShoppingCart {
    void addItem(Laptop laptop);
    void addItems(Laptop laptop, int amount);
    void removeItem(Laptop laptop);
    void removeItemsOfType(Laptop laptop);
    Order makeOrder();
    Order getOrder();
    void setOrder(Order order);
    int getItemsCount();
    void setAccount(Account account);
    Account getAccount();
    Map<Laptop, Integer> getOrderList();
    public boolean isReady();
    public void clear();
}
