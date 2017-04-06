package services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.mykytenko.service.BrandService;

import static services.TestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context.xml"})
@Sql(scripts = "classpath:db/populateDB.sql")
public class BrandServiceTest {

    @Autowired
    BrandService service;

    @Test
    public void getById(){
        Assert.assertEquals(BRANDS.get(0), service.getById(1));
    }


}
