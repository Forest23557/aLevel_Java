package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.config.HibernateFactoryUtil;
import com.shulha.model.Car;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class CarHibernateRepository implements Repository<Car, String> {
    private static final EntityManager ENTITY_MANAGER = HibernateFactoryUtil.getEntityManager();
    private static CarHibernateRepository instance;

    private CarHibernateRepository() {
    }

    public static CarHibernateRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(CarHibernateRepository::new);
        return instance;
    }

    @Override
    public void delete(final String id) {
        getById(id).ifPresent(car -> {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.remove(car);
            ENTITY_MANAGER.getTransaction().commit();
        });
    }

    @Override
    public void save(final Car car) {
        if (Objects.nonNull(car)) {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.persist(car);
            ENTITY_MANAGER.flush();
            ENTITY_MANAGER.getTransaction().commit();
        }
    }

    @Override
    public void removeAll() {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.createNativeQuery("DELETE FROM passenger_car; " +
                        "DELETE FROM truck; " +
                        "DELETE FROM car; " +
                        "DELETE FROM engine")
                .executeUpdate();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public List<Car> getAll() {
        return ENTITY_MANAGER.createQuery("select c from Car c", Car.class)
                .getResultList();
    }

    @Override
    public Optional<Car> getById(final String id) {
        Car car = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            car = ENTITY_MANAGER.find(Car.class, id);
        }

        return Optional.ofNullable(car);
    }
}
