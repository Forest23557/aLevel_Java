package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.config.HibernateFactoryUtil;
import com.shulha.model.*;

import javax.persistence.EntityManager;
import java.util.*;

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

//    public static void main(String[] args) {
//        final OrderHibernateRepository instance1 = OrderHibernateRepository.getInstance();
//        final List<Order> orderList = new LinkedList<>();
//        for (int i = 0; i < 1000; i++) {
//            final Order order = new Order();
//            final PassengerCar passengerCar = new PassengerCar();
//            final Truck truck = new Truck();
//            order.getCars().add(passengerCar);
//            order.getCars().add(truck);
//            orderList.add(order);
//        }
//
//        orderList.forEach(order -> instance1.save(order));
//        System.out.println(instance1.getAll());
//
//        for (int i = 0; i < 1000; i++) {
//            final Car car = instance1.getAll().get(i).getCars().get(0);
//            final Car car1 = instance1.getAll().get(i).getCars().get(1);
//            System.out.println(car);
//            System.out.println(car1);
//        }
//    }
}
