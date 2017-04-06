package ua.mykytenko.service;

import ua.mykytenko.entities.Account;

import java.util.List;

public interface AccountService {
    void delete(Account account);
    void update(Account account);
    void add(Account account);
    Account getById(int id);
    Account getByUsername(String username);
    List<Account> getAll();
}
