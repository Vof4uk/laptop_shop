package ua.mykytenko.repositories;

import ua.mykytenko.entities.Laptop;

import java.util.List;

public interface LaptopRepository {
    Laptop getById(int id);
    Laptop save(Laptop laptop);
    boolean deleteById(int id);
    List<Laptop> getAll();
    List<Laptop> getLaptopsInStock();
    int getStock(int laptopId);
}
