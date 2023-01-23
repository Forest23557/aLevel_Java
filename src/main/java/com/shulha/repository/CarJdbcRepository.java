package com.shulha.repository;

import com.shulha.model.Car;

import java.util.ArrayList;
import java.util.List;
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
    public List<Car> getAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Car> getById(String object) {
        return Optional.empty();
    }
}
