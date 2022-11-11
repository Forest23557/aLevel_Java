package com.shulha.repository;


import com.shulha.model.Car;
import com.shulha.model.CarsColors;

import java.util.Arrays;

//  CRUD
//  Create
//  Read
//  Update
//  Delete
public class CarArrayRepository {
    private static Car[] cars = new Car[10];

    public void save(final Car car) {
        final int index = putCar(car);
        if (index == cars.length) {
            final int lastIndex = cars.length;
            increaseArray();
            cars[lastIndex] = car;
        }
    }

    public void delete(final String id) {
        int i = 0;
        for (; i < cars.length; i++) {
            if (cars[i].getId().equals(id)) {
                break;
            }
        }

        System.arraycopy(cars, i + 1, cars, i,
                cars.length - (i + 1));
    }

    private int putCar(final Car car) {
        int i = 0;
        for (; i < cars.length; i++) {
            if (cars[i] == null) {
                cars[i] = car;
                break;
            }
        }
        return i;
    }

    public Car[] getAll() {
        final int newLength = findUsefulLength();
        final Car[] newCarsArray = new Car[newLength];
        System.arraycopy(cars, 0, newCarsArray, 0, newLength);
        return newCarsArray;
    }

    public Car getById(final String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public void updateColor(final String id, final CarsColors color) {
        final Car car = getById(id);
        if (car != null) {
            car.setColor(color);
        }
    }

    private void increaseArray() {
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

    public void insert(int index, final Car car) {
        if (index >= cars.length) {
            if (cars[cars.length - 1] != null) {
                increaseArray();
            }

            for (int i = 0; i < cars.length; i++) {
                if (cars[i] == null) {
                    index = i;
                    break;
                }
            }
        } else if (cars[index] != null) {
            if (cars[cars.length - 1] != null) {
                increaseArray();
            }
            System.arraycopy(cars, index, cars, index + 1,
                    cars.length - (index + 1));
        } else {
            if (cars[cars.length - 1] != null) {
                increaseArray();
            }

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
