package com.shulha.service;

import com.shulha.model.*;
import com.shulha.repository.CarArrayRepository;
import com.shulha.util.RandomGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CarServiceTest {
    private CarService target;
    private CarArrayRepository repository;
    private RandomGenerator randomGenerator;
    private PassengerCar passengerCar;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarArrayRepository.class);
        randomGenerator = Mockito.mock(RandomGenerator.class);
        target = new CarService(repository);
        passengerCar = new PassengerCar();
    }

    @Test
    void createWithRandomCount() {
//      initialize
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(3);
        final int expected = 3;


//      action
        final int actual = target.createPassengerCar(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectNull() {
//      initialize
        final int expected = -1;


//      action
        final int actual = target.createPassengerCar(null);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectZero() {
//      initialize
        final int expected = -1;
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(0);


//      action
        final int actual = target.createPassengerCar(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectOutOfTheBound() {
//      initialize
        final int expected = -1;
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(-5);


//      action
        final int actual = target.createPassengerCar(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
//      initialize

//      action
        final Car car = target.createPassengerCar();


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
        Assertions.assertDoesNotThrow(() -> target.createPassengerCar(count));
    }

    @Test
    void createWithCountIncorrectCount() {
//      initialize
        int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.createPassengerCar(count));
    }

    @Test
    void insert() {
//      initialize
        final int count = 7;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, passengerCar));
        Mockito.verify(repository).insert(count, passengerCar);
    }

    @Test
    void insertIncorrectCount() {
//      initialize
        final int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.insert(count, passengerCar));
        Mockito.verify(repository).insert(count, passengerCar);
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
        final Car expected = new PassengerCar();
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
        final Car actual = target.find(id);

//      checks
        Assertions.assertNull(actual);
        Mockito.verify(repository).getById(id);
    }

    @Test
    void findIncorrectIdNull() {
//      initialize
        String id = null;

//      action
        final Car actual = target.find(id);

//      checks
        Assertions.assertNull(actual);
        Mockito.verify(repository, Mockito.never()).getById(null);
    }

    @Test
    void findIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action
        final Car actual = target.find(id);

//      checks
        Assertions.assertNull(actual);
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
        String id = passengerCar.getId();

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
        Mockito.verify(repository, Mockito.never()).updateColor(passengerCar.getId(), CarColors.BLACK);
    }

    @Test
    void changeRandomColorIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.changeRandomColor(id));
        Mockito.verify(repository, Mockito.never()).updateColor(passengerCar.getId(), CarColors.BLACK);
    }

    @Test
    void createWithThreeIncorrectParametersNull() {
//      initialize

//      action
        passengerCar = target.createPassengerCar(null, null, null, 0);

//      checks
        Assertions.assertNull(passengerCar);
        Mockito.verify(repository, Mockito.never()).save(null);
    }

    @Test
    void createWithThreeParameters() {
//      initialize
        CarsManufacturers manufacturer = passengerCar.getManufacturer();
        Engine engine = passengerCar.getEngine();
        CarColors color = passengerCar.getColor();

//      action
        Car actual = target.createPassengerCar(manufacturer, engine, color, 4);

//      checks
        Assertions.assertNotNull(actual);
        Mockito.verify(repository).save(actual);
    }

    @Test
    void print() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.print(passengerCar));
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
        Assertions.assertDoesNotThrow(() -> CarService.check(passengerCar));
    }

    @Test
    void checkIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> CarService.check(null));
    }
}