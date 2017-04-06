package ua.mykytenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mykytenko.entities.Account;
import ua.mykytenko.repositories.AccountRepository;
import ua.mykytenko.service.AccountService;
import ua.mykytenko.service.exceptions.FailedOperationException;
import ua.mykytenko.service.exceptions.IllegalStateException;
import ua.mykytenko.service.exceptions.NotFoundException;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private final
    AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Account account) {
        if(account == null){
            throw new IllegalStateException("You cannot delete null");
        }
        if(account.isNew()){
            throw new IllegalStateException("You cannot delete unsaved account");
        }
        repository.deleteById(account.getId());
    }

    @Override
    public void update(Account account) {
        if(account.isNew()){
            throw new IllegalStateException("You cannot update new account");
        }
        try {
            repository.save(account);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to update brand information.");
        }
    }

    @Override
    public void add(Account account) {
        if(!account.isNew()){
            throw new IllegalStateException("You cannot add not unique account");
        }
        try {
            repository.save(account);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to save new account.");
        }
    }

    @Override
    public Account getById(int id) {
        Account account = repository.getById(id);
        if(account == null){
            throw new NotFoundException("No such account");
        }
        return account;
    }

    @Override
    public Account getByUsername(String username) {
        try {
            return repository.findByName(username);
        } catch (RuntimeException e) {
            throw new NotFoundException("No such account");
        }
    }

    @Override
    public List<Account> getAll() {
        return repository.getAll();
    }
}
