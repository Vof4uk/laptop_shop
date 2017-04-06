package ua.mykytenko.service;

import ua.mykytenko.entities.Brand;

import java.util.List;

public interface BrandService {
    Brand getById(int id);
    Brand getByShortName(String shortName);
    List<Brand> getAll();
    void add(Brand brand);
    void update(Brand brand);
    void delete(Brand brand);
    void delete(int id);
}
