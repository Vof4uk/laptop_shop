package ua.mykytenko.service;

import ua.mykytenko.dto.BrandDto;

import java.util.List;

public interface BrandDtoService {
    BrandDto getById(int id);
    BrandDto getByShortName(String shortName);
    List<BrandDto> getAll();
    void add(BrandDto brand);
    void update(BrandDto brand);
    void delete(BrandDto brand);
    void delete(int id);
}
