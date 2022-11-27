package com.shulha.repository;

import com.shulha.model.Car;
import com.shulha.model.CarColors;
import com.shulha.model.PassengerCar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarArrayRepositoryTest {
    private CarArrayRepository target;
    private PassengerCar passengerCar;
    private String id;

//      initialize

//      action

//      checks

    @BeforeEach
    void setUp() {
        target = new CarArrayRepository();
        passengerCar = new PassengerCar();
        target.removeAll();
    }

    @Test
    void deleteIdIsNotFound() {
//      initialize
        id = "1234";
//        Mockito.when(car.getId()).thenReturn("1234");

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }

    @Test
    void delete() {
//      initialize
        target.save(passengerCar);
        id = passengerCar.getId();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
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
        Assertions.assertDoesNotThrow(() -> target.save(passengerCar));
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
        id = passengerCar.getId();
        Car expected = passengerCar;

//      action
        target.save(passengerCar);
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
        target.save(passengerCar);
        Car expected = passengerCar;
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
        target.save(passengerCar);
        target.save(passengerCar);

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
        target.save(passengerCar);
        String id = passengerCar.getId();
        Car expected = passengerCar;

//      action
        Car actual = target.getById(id);

//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByIdIsNotFound() {
//      initialize
        target.save(passengerCar);
        String id = "12345";

//      action

//      checks
        Assertions.assertNull(target.getById(id));
    }

    @Test
    void getByIdIncorrectIdNull() {
//      initialize
        target.save(passengerCar);
        String id = null;

//      action

//      checks
        Assertions.assertNull(target.getById(id));
    }

    @Test
    void getByIdIncorrectIdEmpty() {
//      initialize
        target.save(passengerCar);
        String id = "";

//      action

//      checks
        Assertions.assertNull(target.getById(id));
    }

    @Test
    void updateColor() {
//      initialize
        id = passengerCar.getId();
        CarColors color = passengerCar.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIdIsNotFound() {
//      initialize
        id = "12345";
        CarColors color = passengerCar.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIncorrectIdNull() {
//      initialize
        id = null;
        CarColors color = passengerCar.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIncorrectIdEmpty() {
//      initialize
        id = "";
        CarColors color = passengerCar.getColor();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void updateColorIncorrectColorNull() {
//      initialize
        id = passengerCar.getId();
        CarColors color = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.updateColor(id, color));
    }

    @Test
    void insert() {
//      initialize
        int index = 0;
        Car expected = passengerCar;

//      action
        target.insert(index, passengerCar);
        Car actual = target.getAll()[index];

//      checks
        Assertions.assertEquals(expected, actual);
        Assertions.assertDoesNotThrow(() -> target.insert(index, passengerCar));
    }

    @Test
    void insertIncorrectIndexOutOfTheBound() {
//      initialize
        int index = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(index, passengerCar));
    }

    @Test
    void insertIncorrectCarNull() {
//      initialize
        int index = 0;
        passengerCar = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(index, passengerCar));
    }
}