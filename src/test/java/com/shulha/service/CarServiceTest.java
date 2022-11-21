package com.shulha.service;

import com.shulha.model.Car;
import com.shulha.model.CarsManufacturers;
import com.shulha.repository.CarArrayRepository;
import com.shulha.util.RandomGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    private CarService target;
    private CarArrayRepository repository;
    private RandomGenerator randomGenerator;
    private Car car;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarArrayRepository.class);
        randomGenerator = Mockito.mock(RandomGenerator.class);
        target = new CarService(repository);
        car = new Car();
    }

    @Test
    void createWithRandomCount() {
//      initialize
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(3);
        final int expected = 3;


//      action
        final int actual = target.create(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectNull() {
//      initialize
        final int expected = -1;


//      action
        final int actual = target.create(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectZero() {
//      initialize
        final int expected = -1;
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(0);


//      action
        final int actual = target.create(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectOutOfTheBound() {
//      initialize
        final int expected = -1;
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(-5);


//      action
        final int actual = target.create(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
//      initialize

//      action
        final Car car = target.create();


//      checks
        Assertions.assertNotNull(car);
    }

    @Test
    void createWithCount() {
//      initialize
        int count = 5;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.create(count));
    }

    @Test
    void createWithCountIncorrectCount() {
//      initialize
        int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.create(count));
    }

    @Test
    void insert() {
//      initialize
        final int count = 7;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, car));
    }

    @Test
    void insertIncorrectCount() {
//      initialize
        final int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, car));
    }

    @Test
    void insertIncorrectCarNull() {
//      initialize
        final int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, null));
    }

    @Test
    void printAll() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printAll());
    }

    @Test
    void getAll() {
//      initialize

//      action

//      checks
        Assertions.assertNull(target.getAll());
    }

    @Test
    void find() {
//      initialize
        final Car expected = new Car();
        String id = "1234567890";
        Mockito.when(repository.getById(id)).thenReturn(expected);

//      action
        final Car actual = target.find(id);

//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findNotFound() {
//      initialize
        String id = "1234567890";
        Mockito.when(repository.getById(id)).thenReturn(null);

//      action
        car = target.find(id);

//      checks
        Assertions.assertNull(car);
    }

    @Test
    void findIncorrectIdNull() {
//      initialize
        String id = null;

//      action
        car = target.find(id);

//      checks
        Assertions.assertNull(car);
    }

    @Test
    void findIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action
        car = target.find(id);

//      checks
        Assertions.assertNull(car);
    }

    @Test
    void delete() {
//      initialize
        String id = "123456";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }

    @Test
    void deleteIncorrectIdNull() {
//      initialize
        String id = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }

    @Test
    void deleteIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
    }
}