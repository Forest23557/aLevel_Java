package com.shulha.model;

import com.shulha.repository.Repository;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiPredicate;

public class Order implements Repository<Car, String> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final BiPredicate<Car, String> CHECK_ID = (car, id) -> car.getId().equals(id);
    private final List<Car> cars = new ArrayList<>();
    @Getter
    private final String id;
    @Getter
    private LocalDateTime date;

    public Order() {
        setDate();
        id = UUID.randomUUID().toString();
    }

    private void setDate() {
        date = LocalDateTime.now();
    }

    @Override
    public void delete(final String id) {
        cars.stream()
                .dropWhile(Objects::isNull)
                .filter(checkingCar -> CHECK_ID.test(checkingCar, id))
                .findAny()
                .ifPresent(cars::remove);
    }

    @Override
    public void save(final Car car) {
        cars.stream()
                .dropWhile(Objects::isNull)
                .filter(checkingCar -> CHECK_ID.test(checkingCar, car.getId()))
                .findAny()
                .ifPresentOrElse(
                        checkedCar -> checkedCar.setCount(car.getCount() + checkedCar.getCount()),
                        () -> cars.add(car)
                );
    }

    @Override
    public void removeAll() {
        cars.clear();
    }

    @Override
    public List<Car> getAll() {
        return cars;
    }

    @Override
    public Optional<Car> getById(final String id) {
        return cars.stream()
                .dropWhile(Objects::isNull)
                .filter(checkingCar -> CHECK_ID.test(checkingCar, id))
                .findAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("ORDER №%s%nDATE AND TIME: %s%nCARS: %s%n", id, date, cars);
    }
}
