package ua.mykytenko.service;

import ua.mykytenko.dto.LaptopDto;

import java.util.List;

public interface LaptopDtoService {
    LaptopDto getById(int id);
    List<LaptopDto> getAll();
    boolean delete(int id);
    void update(LaptopDto laptop);
    void add(LaptopDto laptop);
    List<LaptopDto> getLaptopsInStock();
}