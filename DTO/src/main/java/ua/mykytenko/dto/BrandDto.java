package ua.mykytenko.dto;

import ua.mykytenko.entities.Brand;

public class BrandDto {
    private int id;
    private String shortName;
    private String fullName;
    private String description;

    public BrandDto() {
    }

    public BrandDto(Brand brand) {
        this.setId(brand.getId());
        this.setDescription(brand.getDescription());
        this.setFullName(brand.getFullName());
        this.setShortName(brand.getShortName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
