package com.shulha.service;

import com.shulha.model.Car;
import com.shulha.model.CarsColors;
import com.shulha.model.CarsManufacturers;
import com.shulha.model.Engine;
import com.shulha.repository.CarArrayRepository;
import com.shulha.util.RandomGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
        final int actual = target.create(null);


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
        Mockito.verify(repository).save(car);
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
        Mockito.verify(repository).insert(count, car);
    }

    @Test
    void insertIncorrectCount() {
//      initialize
        final int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, car));
        Mockito.verify(repository).insert(count, car);
    }

    @Test
    void insertIncorrectCarNull() {
//      initialize
        final int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, null));
        Mockito.verify(repository).insert(count, null);
    }

    @Test
    void printAll() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printAll());
        Mockito.verify(repository).getAll();
    }

    @Test
    void getAll() {
//      initialize

//      action

//      checks
        Assertions.assertNull(target.getAll());
        Mockito.verify(repository).getAll();
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
        Mockito.verify(repository).getById(id);
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
        Mockito.verify(repository).getById(id);
    }

    @Test
    void findIncorrectIdNull() {
//      initialize
        String id = null;

//      action
        car = target.find(id);

//      checks
        Assertions.assertNull(car);
        Mockito.verify(repository, Mockito.never()).getById(null);
    }

    @Test
    void findIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action
        car = target.find(id);

//      checks
        Assertions.assertNull(car);
        Mockito.verify(repository, Mockito.never()).getById("");
    }

    @Test
    void delete() {
//      initialize
        String id = "123456";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
        Mockito.verify(repository).delete(id);
    }

    @Test
    void deleteIncorrectIdNull() {
//      initialize
        String id = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
        Mockito.verify(repository, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    void deleteIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.delete(id));
        Mockito.verify(repository, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    void changeRandomColor() {
//      initialize
        String id = car.getId();

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.changeRandomColor(id));
    }

    @Test
    void changeRandomColorIncorrectIdNull() {
//      initialize
        String id = null;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.changeRandomColor(id));
        Mockito.verify(repository, Mockito.never()).updateColor(car.getId(), CarsColors.BLACK);
    }

    @Test
    void changeRandomColorIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.changeRandomColor(id));
        Mockito.verify(repository, Mockito.never()).updateColor(car.getId(), CarsColors.BLACK);
    }

    @Test
    void createWithThreeIncorrectParametersNull() {
//      initialize

//      action
        car = target.create(null, null, null);

//      checks
        Assertions.assertNull(car);
        Mockito.verify(repository, Mockito.never()).save(null);
    }

    @Test
    void createWithThreeParameters() {
//      initialize
        CarsManufacturers manufacturer = car.getManufacturer();
        Engine engine = car.getEngine();
        CarsColors color = car.getColor();

//      action
        Car actual = target.create(manufacturer, engine, color);

//      checks
        Assertions.assertNotNull(actual);
        Mockito.verify(repository).save(actual);
    }

    @Test
    void print() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.print(car));
    }

    @Test
    void printIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.print(null));
    }

    @Test
    void check() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> CarService.check(car));
    }

    @Test
    void checkIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> CarService.check(null));
    }
}