package ua.mykytenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.repositories.LaptopRepository;
import ua.mykytenko.service.LaptopService;
import ua.mykytenko.service.exceptions.BadArgumentException;
import ua.mykytenko.service.exceptions.FailedOperationException;
import ua.mykytenko.service.exceptions.IllegalStateException;
import ua.mykytenko.service.exceptions.NotFoundException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class LaptopServiceImpl implements LaptopService{

    private final LaptopRepository repository;

    @Autowired
    public LaptopServiceImpl(LaptopRepository repository) {
        this.repository = repository;
    }

    @Override
    public Laptop getById(int id) {
        Laptop laptop = repository.getById(id);
        if(laptop == null){
            throw new NotFoundException("No laptop with such id");
        }
        return laptop;
    }

    @Override
    public List<Laptop> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Laptop laptop) {
        if(laptop == null){
            throw new IllegalStateException("You cannot save null");
        }
        if(laptop.isNew()){
            throw new IllegalStateException("You cannot delete new laptop");
        }
        return delete(laptop);
    }

    @Override
    public boolean delete(int id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Laptop laptop) {
        if(laptop.isNew()){
            throw new java.lang.IllegalStateException("You cannot not update unsaved laptop. save it first");
        }
        try {
            repository.save(laptop);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to update laptop information.");
        }
    }

    @Override
    public void add(Laptop laptop) {
        if(!laptop.isNew()){
            throw new IllegalStateException("You cannot add not unique laptop");
        }
        try {
            repository.save(laptop);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to save new laptop.");
        }
    }

    @Override
    @Transactional
    public void updateIgnoreStock(Laptop laptop) {
        if(laptop.isNew()){
            throw new java.lang.IllegalStateException("You cannot not update unsaved laptop. save it first");
        }
        try {
            int stock = repository.getStock(laptop.getId());
            laptop.setStock(stock);
            repository.save(laptop);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to update laptop information.");
        }
    }

    @Override
    public List<Laptop> getLaptopsInStock() {
        return repository.getLaptopsInStock();
    }

    @Override
    public int getStock(int laptopId) {
        if(laptopId <= 0){
            throw new BadArgumentException("id cannot be 0");
        }
        return repository.getStock(laptopId);
    }
}
