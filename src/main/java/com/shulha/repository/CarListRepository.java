package com.shulha.repository;

import com.shulha.model.Car;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class CarListRepository implements Repository<Number, Car, String> {
    private static final List<Car> CARS = new LinkedList<>();
    private static final BiPredicate<Car, String> CHECK_ID = (car, id) -> car.getId().equals(id);
    private static CarListRepository instance;

    private CarListRepository() {
    }

    public static CarListRepository getInstance() {
        if (instance == null) {
            instance = new CarListRepository();
        }
        return instance;
    }

    @Override
    public void delete(final String id) {
        CARS.removeIf(checkingCar -> CHECK_ID.test(checkingCar, id));
    }

    @Override
    public void save(final Car car) {
        CARS.stream()
                .filter(checkingCar -> CHECK_ID.test(checkingCar, car.getId()))
                .findAny()
                .ifPresentOrElse(
                        checkedCar -> checkedCar.setCount(car.getCount() + checkedCar.getCount()),
                        () -> CARS.add(car)
                );
    }

    @Override
    public void removeAll() {
        CARS.removeAll(CARS);
    }

    @Override
    public Car[] getAll() {
        return CARS.toArray(new Car[0]);
    }

    @Override
    public Optional<Car> getById(final String id) {
        return CARS.stream()
                .filter(checkingCar -> CHECK_ID.test(checkingCar, id))
                .findAny();
    }
}
