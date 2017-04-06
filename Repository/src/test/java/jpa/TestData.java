package jpa;

import ua.mykytenko.entities.Account;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.UserRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static final List<Laptop> LAPTOPS;
    public static final List<Brand> BRANDS;
    public static final List<Account> ACCOUNTS;

    static {
        BRANDS = new ArrayList<>();
        BRANDS.add(new Brand("Sony", "sony-full-name", "japanese company"));
        BRANDS.add(new Brand("MSI", null, "chinese company"));
        BRANDS.add(new Brand("Apple", null, "american"));
        BRANDS.add(new Brand("Вован-пк", "Вован юкрейн сомрани инкорплрейтд", "украина"));

        LAPTOPS = new ArrayList<>();
        LAPTOPS.add(new Laptop("sony-model-1", "good laptop", BigDecimal.valueOf(2900010).divide(BigDecimal.valueOf(100))
                , 8, 3.2, BRANDS.get(0), 20));
        LAPTOPS.add(new Laptop("sony-model-2", "average laptop", BigDecimal.valueOf(1400092).divide(BigDecimal.valueOf(100))
                , 4, 2, BRANDS.get(0),10));
        LAPTOPS.add(new Laptop("msi-1", "very good laptop", BigDecimal.valueOf(3403056).divide(BigDecimal.valueOf(100))
                , 8, 4, BRANDS.get(1), 0));
        LAPTOPS.add(new Laptop("v-104", "best laptop", BigDecimal.valueOf(1500000).divide(BigDecimal.valueOf(100))
                , 16, 8, BRANDS.get(3), 2));
        LAPTOPS.add(new Laptop("v-105", "the best laptop", BigDecimal.valueOf(2100000).divide(BigDecimal.valueOf(100))
                , 32, 16, BRANDS.get(3), 1));

        ACCOUNTS = new ArrayList<>();
        ACCOUNTS.add(new Account("user", "$2a$10$AKgT/Sw2JmNCUjAYOkoA.Ot35iAW5xHLlAOMwCnKCdX2/Z9GcUAGS", UserRole.USER));
        ACCOUNTS.add(new Account("admin", "$2a$10$/sM.K6bQCrzoJtnSlEE9m.Gv0c64dHOQLAUlmA4i.LwHxS4BiIfYu", UserRole.ADMIN));
        ACCOUNTS.add(new Account("admiin", "$2a$10$vDvvpSNenBl18qviUX0FROgceK56W2HUWyHngM6q9SRjAug0QC8D2", UserRole.ADMIN));
    }
}
