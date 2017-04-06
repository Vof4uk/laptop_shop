package ua.mykytenko.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.mykytenko.entities.Account;
import ua.mykytenko.repositories.AccountRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Account getById(int id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account findByName(String name) {
        return em.createNamedQuery(Account.FIND_BY_NAME, Account.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Account save(Account account) {
        LOGGER.info("Saving account " + account.getName());

        if (account.isNew()) {
            em.persist(account);
        } else {
            return em.merge(account);
        }
        return account;
    }

    @Override
    public boolean deleteById(int id) {
        LOGGER.warn("Deleting account id=" + id);
        try {
            Account accountRef = em.getReference(Account.class, id);
            em.remove(accountRef);
            if (accountRef == null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Account> getAll() {
        return em.createNamedQuery(Account.GET_ALL, Account.class)
                .getResultList();
    }
}
