package com.shulha.repository;


import com.shulha.model.Car;
import com.shulha.model.CarColors;

//  CRUD
//  Create
//  Read
//  Update
//  Delete
public class CarArrayRepository {
    private static Car[] cars = new Car[10];

//  tested
    public void save(final Car car) {
        if (car == null) {
            return;
        }

        final int index = putCar(car);
        if (index == cars.length) {
            final int lastIndex = cars.length;
            increaseArray();
            cars[lastIndex] = car;
        }
    }

//  tested
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
    public void removeAll() {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] == null) {
                break;
            } else {
                cars[i] = null;
            }
        }
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

//  tested
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
    public Car getById(final String id) {
        for (Car car : cars) {
            if (car == null) {
                return null;
            } else if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

//  tested
    public void updateColor(final String id, final CarColors color) {
        if (id == null || id.isBlank() || color == null) {
            return;
        }

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

//  tested
    public void insert(int index, final Car car) {
//      checking
        if (index < 0 || car == null) {
            return;
        }

//      increase the array
        if (cars[cars.length - 1] != null) {
            increaseArray();
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
