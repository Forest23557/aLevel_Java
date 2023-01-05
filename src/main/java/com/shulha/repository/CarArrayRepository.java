package com.shulha.repository;


import com.shulha.model.Car;
import com.shulha.model.CarColors;

import java.util.Objects;
import java.util.Optional;

//  CRUD
//  Create
//  Read
//  Update
//  Delete
public class CarArrayRepository implements Repository<Car, String> {
    private static Car[] cars = new Car[10];
    private static CarArrayRepository instance;

    private CarArrayRepository() {
    }

    public static CarArrayRepository getInstance() {
        instance = Optional.ofNullable(instance).orElseGet(() -> new CarArrayRepository());
        return instance;
    }

//  tested
    @Override
    public void save(final Car car) {
        if (Optional.ofNullable(car).isPresent()) {
            final int index = put(car);
            if (index == cars.length) {
                final int lastIndex = cars.length;
                increase();
                cars[lastIndex] = car;
            }
        }
    }

//  tested
    @Override
    public void delete(final String id) {
//      checking
        if (cars[0] == null || id == null || id.isBlank()) {
            return;
        }

//      Looking for a car with the id
        int i = 0;
        for (; i < cars.length; i++) {
            if (cars[i] == null) {
                return;
            } else if (cars[i].getId().equals(id)) {
                break;
            }
        }

//      removing
        System.arraycopy(cars, i + 1, cars, i,
                cars.length - (i + 1));
    }

//  tested
    @Override
    public void removeAll() {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] == null) {
                break;
            } else {
                cars[i] = null;
            }
        }
    }

    private int put(final Car car) {
        int i = 0;
        for (; i < cars.length; i++) {
            if (cars[i] == null) {
                cars[i] = car;
                break;
            }
        }
        return i;
    }

//  tested
    @Override
    public Car[] getAll() {
        final int newLength = findUsefulLength();

        if (newLength == 0) {
            return null;
        }

        final Car[] newCarsArray = new Car[newLength];
        System.arraycopy(cars, 0, newCarsArray, 0, newLength);
        return newCarsArray;
    }

//  tested
    @Override
    public Optional<Car> getById(final String id) {
        Optional<Car> carOptional = Optional.ofNullable(null);

        for (Car car : cars) {
            if (car == null) {
                return carOptional;
            } else if (car.getId().equals(id)) {
                return Optional.of(car);
            }
        }

        return carOptional;
    }

//  tested
    public void updateColor(final String id, final CarColors color) {
        if (id == null || id.isBlank() || color == null) {
            return;
        }

        getById(id).ifPresent(car -> car.setColor(color));
    }

    private void increase() {
        Car[] newCarsArray = new Car[cars.length + 10];
        System.arraycopy(cars, 0, newCarsArray, 0,
                cars.length);
        cars = newCarsArray;
    }

    private int findUsefulLength() {
        int lengthWithoutEmptyCells = 0;
        for (Car car : cars) {
            if (car == null) {
                break;
            }
            lengthWithoutEmptyCells++;
        }
        return lengthWithoutEmptyCells;
    }

    //  tested
    public void insert(int index, final Car car) {
//      checking
        if (!Objects.isNull(car)) {

            if (index < 0) {
                return;
            }

//      increase the array
            if (cars[cars.length - 1] != null) {
                increase();
            }

//      looking for a place for an insertion
            if (index >= cars.length) {
                for (int i = 0; i < cars.length; i++) {
                    if (cars[i] == null) {
                        index = i;
                        break;
                    }
                }
            } else if (cars[index] != null) {
                System.arraycopy(cars, index, cars, index + 1,
                        cars.length - (index + 1));
            } else {
                for (int i = 0; i < index; i++) {
                    if (cars[i] == null) {
                        index = i;
                        break;
                    }
                }
            }

            cars[index] = car;
        }
    }

    public void replaceCars(final int indexOfFirst, final int indexOfSecond) {
        int lastNotNullIndex = findUsefulLength() - 1;
        if ((indexOfFirst < 0 || indexOfFirst > lastNotNullIndex) ||
                (indexOfSecond < 0 || indexOfSecond > lastNotNullIndex)) {
            return;
        }

        Car temp = cars[indexOfFirst];
        cars[indexOfFirst] = cars[indexOfSecond];
        cars[indexOfSecond] = temp;
    }
}
