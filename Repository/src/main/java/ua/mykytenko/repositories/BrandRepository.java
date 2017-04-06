package ua.mykytenko.repositories;

import ua.mykytenko.entities.Brand;

import java.util.List;

public interface BrandRepository {
    Brand getById(int id);
    Brand save(Brand brand);
    Brand getByShortName(String name);
    boolean deleteById(int id);
    List<Brand> getAll();
}
