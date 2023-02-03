package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.config.HibernateFactoryUtil;
import com.shulha.model.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class OrderHibernateRepository implements Repository<Order, String> {
    private static final EntityManager ENTITY_MANAGER = HibernateFactoryUtil.getEntityManager();
    private static OrderHibernateRepository instance;

    private OrderHibernateRepository() {
    }

    public static OrderHibernateRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(() -> new OrderHibernateRepository());
        return instance;
    }

    @Override
    public void delete(final String id) {
        getById(id).ifPresent(order -> {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.remove(order);
            ENTITY_MANAGER.getTransaction().commit();
        });
    }

    @Override
    public void save(final Order order) {
        if (Objects.nonNull(order)) {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.persist(order);
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
                        "DELETE FROM engine; " +
                        "DELETE FROM our_order")
                .executeUpdate();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public List<Order> getAll() {
        return ENTITY_MANAGER.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    @Override
    public Optional<Order> getById(final String id) {
        Order order = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            order = ENTITY_MANAGER.find(Order.class, id);
        }

        return Optional.ofNullable(order);
    }
}
