package ua.mykytenko.dto;

import ua.mykytenko.entities.Laptop;

import java.math.BigDecimal;
import java.util.List;

public class LaptopDto {

    private int id;
    private BrandDto brand;
    private String model;
    private String description;
    private BigDecimal price;
    private double ram;
    private double cpuFrequency;
    private int stock;
    private List<String> imagesLocations;

    public LaptopDto(){}

    public LaptopDto(Laptop laptop){
        this.setId(laptop.getId());
        this.setBrand(new BrandDto(laptop.getBrand()));
        this.setDescription(laptop.getDescription());
        this.setModel(laptop.getModel());
        this.setStock(laptop.getStock());
        this.setImagesLocations(laptop.getImagesLocations());
        this.setCpuFrequency(laptop.getCpuFrequency());
        this.setPrice(laptop.getPrice());
        this.setRam(laptop.getRam());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

    public double getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(double cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImagesLocations() {
        return imagesLocations;
    }

    public void setImagesLocations(List<String> imagesLocations) {
        this.imagesLocations = imagesLocations;
    }
}
