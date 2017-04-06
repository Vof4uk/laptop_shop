package jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.mykytenko.Profiles;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.repositories.LaptopRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static jpa.TestData.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-context.xml"})
@ActiveProfiles(Profiles.TEST)
@TestPropertySource(locations = "classpath:jdbc-test.properties")
@Sql(scripts = "classpath:db/populateDB.sql")
public class LaptopRepositoryTest {

    @Autowired
    private LaptopRepository repository;

    @Test
    public void testGetById(){
        Laptop actual = repository.getById(1);
        Laptop expected = LAPTOPS.get(0);
        assertLaptopsEquals(actual, expected);
    }

    @Test
    public void testSaveNewLaptop(){
        Brand brand = repository.getById(1).getBrand();
        Laptop newLaptop = new Laptop("model-x", "descr", BigDecimal.valueOf(100001), 3, 3.7, brand, 4);
        List<String> locations = new ArrayList<>();
        locations.add("abc");
        locations.add("abcd");
        newLaptop.setImagesLocations(locations);
        Laptop persisted = repository.save(newLaptop);
        assertLaptopsEquals(persisted, repository.getById(persisted.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void testDelete(){
        repository.deleteById(1);
        System.out.println();
    }

    @Test
    public void testUpdate(){
        Laptop laptop = repository.getById(3);
        laptop.setDescription("abc");
        laptop.setCpuFrequency(1.8);
        repository.save(laptop);
        assertLaptopsEquals(laptop, repository.getById(3));
    }

    @Test(expected = RuntimeException.class)
    public void deleteNotFound(){
        repository.deleteById(Integer.MAX_VALUE);
    }

    @Test
    public void byIdNotFound(){
        Assert.assertNull(repository.getById(Integer.MAX_VALUE));
    }

    @Test(expected = RuntimeException.class)
    public void updateNotFound(){
        Laptop laptop = repository.getById(1);
        laptop.setId(Integer.MAX_VALUE);
        repository.save(laptop);
    }

    @Test
    public  void testGetAll(){
        assertLaptopListsEqual(LAPTOPS, repository.getAll());
    }

    private void assertLaptopsEquals(Laptop expected, Laptop actual){
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.getBrand(), actual.getBrand());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getCpuFrequency(), actual.getCpuFrequency(), 0.001);
        Assert.assertEquals(expected.getRam(), actual.getRam(), 0.001);
        Assert.assertEquals(expected.getStock(), actual.getStock());
    }

    private void assertLaptopListsEqual(List<Laptop> expectedList, List<Laptop> actualList){
        Assert.assertEquals(expectedList.size(), actualList.size());
        Laptop[] first = expectedList.toArray(new Laptop[expectedList.size()]);
        Laptop[] second = expectedList.toArray(new Laptop[expectedList.size()]);
        Comparator<Laptop> comparator = Comparator.comparing(Laptop::getModel);
        Arrays.sort(first, comparator);
        Arrays.sort(second, comparator);

        for (int i = 0; i < first.length; i++) {
            assertLaptopsEquals(first[i], second[i]);
        }
    }
}
