package com.shulha.repository;

import com.shulha.model.Car;
import com.shulha.model.CarsColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CarArrayRepositoryTest {
    private CarArrayRepository target;
    private Car car;
    private String id;

//      initialize

//      action

//      checks

    @BeforeEach
    void setUp() {
        target = new CarArrayRepository();
        car = new Car();
        target.removeAll();
    }

    @Test
    void delete() {
//      initialize
        id = "1234";
//        Mockito.when(car.getId()).thenReturn("1234");

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }

    @Test
    void deleteChecking() {
//      initialize
        target.save(car);
        id = car.getId();

//      action
        target.delete(id);

//      checks
        Assertions.assertNull(target.getById(id));
    }

    @Test
    void deleteIncorrectIdNull() {
//      initialize
        id = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }

    @Test
    void deleteIncorrectIdEmpty() {
//      initialize
        id = "";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }

    @Test
    void save() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.save(car));
    }

    @Test
    void saveIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.save(null));
    }

    @Test
    void saveChecking() {
//      initialize
        id = car.getId();
        Car expected = car;
//      action
        target.save(car);
        Car actual = target.getById(id);

//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllIncorrectEmptyArray() {
//      initialize

//      action

//      checks
        Assertions.assertNull(target.getAll());
    }

    @Test
    void getAll() {
//      initialize
        target.save(car);
        Car expected = car;
        int expectedLength = 1;

//      action
        Car actual = target.getAll()[0];
        int actualLength = target.getAll().length;

//      checks
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedLength, actualLength);
    }

    @Test
    void removeAll() {
//      initialize
        target.save(car);
        target.save(car);
//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.removeAll());
        Assertions.assertNull(target.getAll());
    }

    @Test
    void removeAllIncorrectEmptyArray() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.removeAll());
    }

    @Test
    void getById() {
//      initialize
        target.save(car);
        String id = car.getId();
        Car expected = car;

//      action
        Car actual = target.getById(id);

//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByIdIncorrectIdNull() {
//      initialize
        target.save(car);
        String id = null;

//      action

//      checks
        Assertions.assertNull(target.getById(id));
    }

    @Test
    void getByIdIncorrectIdEmpty() {
//      initialize
        target.save(car);
        String id = "";

//      action

//      checks
        Assertions.assertNull(target.getById(id));
    }

    @Test
    void updateColor() {
//      initialize
        id = car.getId();
        CarsColors color = car.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIncorrectIdNull() {
//      initialize
        id = null;
        CarsColors color = car.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIncorrectIdEmpty() {
//      initialize
        id = "";
        CarsColors color = car.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIncorrectColorNull() {
//      initialize
        id = car.getId();
        CarsColors color = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void insert() {
//      initialize
        int index = 0;
        Car expected = car;

//      action
        target.insert(index, car);
        Car actual = target.getAll()[index];

//      checks
        Assertions.assertEquals(expected, actual);
        Assertions.assertDoesNotThrow(() -> target.insert(index, car));
    }

    @Test
    void insertIncorrectIndexOutOfTheBound() {
//      initialize
        int index = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(index, car));
    }

    @Test
    void insertIncorrectCarNull() {
//      initialize
        int index = 0;
        car = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(index, car));
    }
}