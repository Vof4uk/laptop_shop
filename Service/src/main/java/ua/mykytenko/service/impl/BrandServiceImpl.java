package ua.mykytenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.repositories.BrandRepository;
import ua.mykytenko.service.BrandService;
import ua.mykytenko.service.exceptions.FailedOperationException;
import ua.mykytenko.service.exceptions.IllegalStateException;
import ua.mykytenko.service.exceptions.NotFoundException;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{

    private final BrandRepository repository;

    @Autowired
    public BrandServiceImpl(BrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public Brand getById(int id) {
        Brand brand = repository.getById(id);
        if(brand == null){
            throw new NotFoundException("No such brand");
        }
        return brand;
    }

    @Override
    public Brand getByShortName(String shortName) {
        try {
            return repository.getByShortName(shortName);
        } catch (RuntimeException e) {
            throw new NotFoundException("No such brand");
        }
    }

    @Override
    public List<Brand> getAll() {
        return repository.getAll();
    }

    @Override
    public void add(Brand brand) {
        if(!brand.isNew()){
            throw new IllegalStateException("You cannot add not unique brand");
        }
        try {
            repository.save(brand);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to save new brand.");
        }
    }

    @Override
    public void update(Brand brand) {
        if(brand.isNew()){
            throw new java.lang.IllegalStateException("You cannot not update fresh brend. save it first");
        }
        try {
            repository.save(brand);
        } catch (Exception e) {
            throw new FailedOperationException("Failed to update brand information.");
        }
    }

    @Override
    public void delete(Brand brand) {
        if(brand == null){
            throw new IllegalStateException("You cannot delete null");
        }
        if(brand.isNew()){
            throw new IllegalStateException("You cannot delete new brand");
        }
        delete(brand.getId());
    }

    @Override
    public void delete(int id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new FailedOperationException("Failed to delete brand");
        }
    }
}
