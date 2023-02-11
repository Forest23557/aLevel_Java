package com.shulha.service;

import com.shulha.annotation.Autowired;
import com.shulha.annotation.Singleton;
import com.shulha.model.Car;
import com.shulha.model.Order;
import com.shulha.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Singleton
public class OrderService {
    private final static Random RANDOM = new Random();

    private final Repository<Order, String> orderRepository;
    private static OrderService instance;

    private OrderService(final Repository<Order, String> orderRepository) {
        this.orderRepository = orderRepository;
    }

    public static OrderService getInstance() {
        instance = Optional
                .ofNullable(instance)
                .orElseGet(() -> new OrderService(OrderJdbcRepository.getInstance()));
        return instance;
    }

    @Autowired(set = OrderHibernateRepository.class)
    public static OrderService getInstance(final Repository<Order, String> orderRepository) {
        instance = Optional
                .ofNullable(instance)
                .orElseGet(() -> new OrderService(Optional
                        .ofNullable(orderRepository)
                        .orElseGet(() -> OrderHibernateRepository.getInstance())));
        return instance;
    }

    public void save(final Order order) {
        Optional.ofNullable(order)
                        .ifPresent(order1 -> orderRepository.save(order1));
    }

    public void deleteById(final String id) {
        orderRepository.delete(id);
    }

    public void removeAll() {
        orderRepository.removeAll();
    }

    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    public Optional<Order> getById(final String id) {
        return orderRepository.getById(id);
    }
}
