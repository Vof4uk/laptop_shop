package ua.mykytenko.businesslayer;

import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.Order;

import java.util.Map;

public interface LaptopManager {

    int sellOrder(Order order, BankAccount bankAccount);

    /**checks Order with available positions and correct price and corrects it if mistakes*/
    boolean confirmStock(Order order);

    void receiveLaptops(Laptop laptop, int quantity);
    void receiveLaptops(int id, int quantity);

    void removeLaptops(Laptop laptop, int quantity);
    void removeLaptops(int id, int quantity);
}
