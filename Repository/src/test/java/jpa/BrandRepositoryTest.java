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
import ua.mykytenko.repositories.BrandRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static jpa.TestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-context.xml"})
@ActiveProfiles(Profiles.TEST)
@TestPropertySource(locations = "classpath:jdbc-test.properties")
@Sql(scripts = "classpath:db/populateDB.sql")
public class BrandRepositoryTest {
    @Autowired
    BrandRepository repository;

    @Test
    public void testGetById(){
        assertBrandsEqual(BRANDS.get(0), repository.getById(1));
    }

    @Test
    public  void  testGetByShortName(){
        assertBrandsEqual(BRANDS.get(1), repository.getByShortName("MSI"));
    }

    @Test
    public void testGetAll(){
        assertBrandsListsEqual(BRANDS, repository.getAll());
    }

    @Test
    public void testDeleteById(){
        Brand actual = repository.getById(3);
        Assert.assertEquals(BRANDS.get(2), actual);
        Assert.assertTrue(repository.deleteById(actual.getId()));
        Assert.assertNull(repository.getById(actual.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void deleteNotFound(){
        repository.deleteById(Integer.MAX_VALUE);
    }

    @Test(expected = RuntimeException.class)
    public void updateNotFound(){
        Brand brand = repository.getById(1);
        brand.setId(Integer.MAX_VALUE);
        repository.save(brand);
    }

    @Test
    public void byIdNotFound(){
        Assert.assertNull(repository.getById(Integer.MAX_VALUE));
    }

    @Test(expected = RuntimeException.class)
    public void byShortNameNotFound(){
        repository.getByShortName("djdksfjbskjbfskj");
    }

    private void assertBrandsEqual(Brand expected, Brand actual){
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getFullName(), actual.getFullName());
    }

    private void assertBrandsListsEqual(List<Brand> expectedList, List<Brand> actualList){
        Assert.assertEquals(expectedList.size(), actualList.size());
        Brand[] first = expectedList.toArray(new Brand[expectedList.size()]);
        Brand[] second = expectedList.toArray(new Brand[expectedList.size()]);
        Comparator<Brand> comparator = Comparator.comparing(Brand::getShortName);
        Arrays.sort(first, comparator);
        Arrays.sort(second, comparator);

        for (int i = 0; i < first.length; i++) {
            assertBrandsEqual(first[i], second[i]);
        }
    }
}

