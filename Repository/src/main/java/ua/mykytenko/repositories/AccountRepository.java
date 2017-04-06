package ua.mykytenko.repositories;

import ua.mykytenko.entities.Account;

import java.util.List;

public interface AccountRepository {
    Account getById(int id);
    Account findByName(String name);
    Account save(Account account);
    boolean deleteById(int id);
    List<Account> getAll();
}
