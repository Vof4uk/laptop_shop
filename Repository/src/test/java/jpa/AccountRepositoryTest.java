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
import ua.mykytenko.entities.Account;
import ua.mykytenko.entities.UserRole;
import ua.mykytenko.repositories.AccountRepository;

import static jpa.TestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-context.xml"})
@ActiveProfiles(Profiles.TEST)
@TestPropertySource(locations = "classpath:jdbc-test.properties")
@Sql(scripts = "classpath:db/populateDB.sql")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    public void testGet(){
        Account account = repository.getById(2);
        assertUsersEqual(ACCOUNTS.get(1), account);
    }

    @Test
    public void testSave(){
        Account account = new Account("vovan", "pass", UserRole.USER);
        repository.save(account);
        assertUsersEqual(account, repository.getById(account.getId()));
    }


    @Test
    public void testUpdate(){
        Account account = repository.getById(2);
        account.setName("hhh");
        account.setPassword("jhg");
        account.setRole(UserRole.USER);
        repository.save(account);
    }

    @Test
    public void testFindByName(){
        assertUsersEqual(ACCOUNTS.get(0), repository.findByName("user"));
    }

    private void assertUsersEqual(Account expected, Account actual){
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getRole(), actual.getRole());
    }
}
