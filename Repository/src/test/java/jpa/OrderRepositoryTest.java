package jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.mykytenko.Profiles;
import ua.mykytenko.entities.Order;
import ua.mykytenko.repositories.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-context.xml"})
@ActiveProfiles(Profiles.TEST)
@TestPropertySource(locations = "classpath:jdbc-test.properties")
@Sql(scripts = "classpath:db/populateDB.sql")
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orders;

    @Test
    public void testGetById(){
        Order order = orders.getById(1);
        System.out.println(order);
    }
}
