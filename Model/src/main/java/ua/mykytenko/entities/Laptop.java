package ua.mykytenko.entities;

import org.hibernate.annotations.NaturalId;
import ua.mykytenko.entities.converters.BigDecimalToIntConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "laptops")
@NamedQueries({@NamedQuery(name = Laptop.GET_ALL, query = "SELECT l FROM Laptop l ORDER BY l.id"),
        @NamedQuery(name = Laptop.GET_ALL_IN_STOCk, query = "SELECT l FROM Laptop l WHERE l.stock > 0 ORDER BY l.id"),
        @NamedQuery(name = Laptop.GET_STOCK_FOR_ID, query = "SELECT l.stock FROM Laptop l WHERE l.id=:id")
})
public class Laptop {
    public static final String GET_ALL = "laptops.getAll";
    public static final String GET_ALL_IN_STOCk = "laptops.inStock";
    public static final String GET_STOCK_FOR_ID = "laptops.getStockForId";

    public Laptop() {
    }

    public Laptop(String model, String description, BigDecimal price, double ram, double cpuFrequency, Brand brand, int stock)
    {
        this.model = model;
        this.description = description;
        this.price = price;
        this.ram = ram;
        this.cpuFrequency = cpuFrequency;
        this.brand = brand;
        this.stock = stock;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NaturalId
    @Column(name = "model", nullable = false, unique = true)
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @Convert(converter = BigDecimalToIntConverter.class)
    private BigDecimal price;

    @Column(name = "ram", nullable = false)
    private double ram;

    @Column(name = "cpu_frequency", nullable = false)
    private double cpuFrequency;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "laptop_images", joinColumns ={ @JoinColumn(name = "laptop_id")})
    @Column(name = "path")
    private List<String> imagesLocations;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "stock")
    private int stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == 0;
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

    public List<String> getImagesLocations() {
        return imagesLocations;
    }

    public void setImagesLocations(List<String> imagesPath) {
        this.imagesLocations = imagesPath;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Laptop laptop = (Laptop) o;

        return model.equals(laptop.model);
    }

    @Override
    public int hashCode() {
        return model.hashCode();
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", ram=" + ram +
                ", cpuFrequency=" + cpuFrequency +
                ", brand=" + brand +
                '}';
    }

}
