package ua.mykytenko.utils;

import ua.mykytenko.dto.BrandDto;
import ua.mykytenko.dto.LaptopDto;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.entities.Laptop;

import java.util.ArrayList;

public class DtoUtil {
    private DtoUtil(){}

    public static Laptop dtoToEntity(LaptopDto dto){
        Laptop laptop = new Laptop();
        laptop.setId(dto.getId());
        laptop.setModel(dto.getModel());
        laptop.setDescription(dto.getDescription());
        laptop.setBrand(dtoToEntity(dto.getBrand()));
        laptop.setRam(dto.getRam());
        laptop.setCpuFrequency(dto.getCpuFrequency());
        laptop.setImagesLocations(dto.getImagesLocations());
        laptop.setStock(dto.getStock());
        laptop.setPrice(dto.getPrice());
        return laptop;
    }

    public static Brand dtoToEntity(BrandDto dto){
        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setDescription(dto.getDescription());
        brand.setFullName(dto.getFullName());
        brand.setShortName(dto.getShortName());
        brand.setLaptops(new ArrayList<>());
        return brand;
    }
}
