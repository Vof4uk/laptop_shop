package ua.mykytenko.service;

import ua.mykytenko.entities.Laptop;

import java.util.List;

public interface LaptopService {
    Laptop getById(int id);
    List<Laptop> getAll();
    boolean delete(Laptop laptop);
    boolean delete(int id);
    void update(Laptop laptop);
    void updateIgnoreStock(Laptop laptop);
    void add(Laptop laptop);
    List<Laptop> getLaptopsInStock();
    int getStock(int laptopId);
}
