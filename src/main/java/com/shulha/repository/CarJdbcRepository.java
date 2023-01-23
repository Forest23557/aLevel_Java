package com.shulha.repository;

import com.shulha.model.Car;

import java.util.Optional;

public class CarJdbcRepository implements Repository<Car, String> {
    @Override
    public void delete(String object) {

    }

    @Override
    public void save(Car value) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public Car[] getAll() {
        return new Car[0];
    }

    @Override
    public Optional<Car> getById(String object) {
        return Optional.empty();
    }
}
