package ua.mykytenko.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.repositories.LaptopRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LaptopRepositoryJpaImpl implements LaptopRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopRepositoryJpaImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Laptop getById(int id) {
        return entityManager.find(Laptop.class, id);
    }

    @Transactional
    public Laptop save(Laptop laptop) {
        LOGGER.info("Saving laptop model " + laptop.getModel());

        if(laptop.isNew()){
            entityManager.persist(laptop);
        }else{
            laptop = entityManager.merge(laptop);
        }
        return laptop;
    }

    @Transactional
    public boolean deleteById(int id) {
        LOGGER.warn("Deleting laptop id =" + id);

        try {
            Laptop ref = entityManager.getReference(Laptop.class, id);
            entityManager.remove(ref);
            if(ref == null){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Laptop> getAll() {
        return entityManager.createNamedQuery(Laptop.GET_ALL, Laptop.class).getResultList();
    }

    @Override
    public List<Laptop> getLaptopsInStock() {
        return entityManager.createNamedQuery(Laptop.GET_ALL_IN_STOCk, Laptop.class).getResultList();
    }

    @Override
    public int getStock(int laptopId) {
        return entityManager.createNamedQuery(Laptop.GET_STOCK_FOR_ID, Integer.class).setParameter("id", laptopId).getSingleResult();
    }
}
