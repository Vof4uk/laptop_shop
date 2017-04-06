package ua.mykytenko.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brands")
@NamedQueries({
        @NamedQuery(name = Brand.GET_ALL, query = "SELECT b FROM Brand b ORDER BY b.id"),
        @NamedQuery(name = Brand.GET_BY_SHORT_NAME, query = "SELECT b from Brand b WHERE b.shortName=:shortName")
})
public class Brand {
    public static final String GET_ALL = "brand.getAll";
    public static final String GET_BY_SHORT_NAME = "brand.getByShortName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NaturalId
    @Column(name = "short_name", unique = true)
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Laptop> laptops;

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

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return shortName.equals(brand.shortName);
    }

    @Override
    public int hashCode() {
        return shortName.hashCode();
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Brand(String shortName, String fullName, String description) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.description = description;
    }

    public Brand() {
    }

    public boolean isNew() {
        return id == 0;
    }
}
