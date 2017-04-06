package ua.mykytenko.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.repositories.BrandRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BrandRepositoryJpaImpl implements BrandRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandRepositoryJpaImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Brand save(Brand brand) {
        LOGGER.info("Savig brand " + brand.getShortName());

        if(brand.isNew()){
            entityManager.persist(brand);
        }else {
            entityManager.merge(brand);
        }
        return brand;
    }

    @Override
    @Transactional(readOnly = true)
    public Brand getById(int id) {
        return entityManager.find(Brand.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Brand getByShortName(String name) {
        return entityManager.createNamedQuery(Brand.GET_BY_SHORT_NAME, Brand.class).setParameter("shortName", name).getSingleResult();
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        LOGGER.warn("Deleting brand with id = " + id);
        try {
            Brand ref = entityManager.getReference(Brand.class, id);
            entityManager.remove(ref);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> getAll() {
        return entityManager.createNamedQuery(Brand.GET_ALL, Brand.class).getResultList();
    }
}
