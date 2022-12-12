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
    private Car car;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarArrayRepository.class);
        randomGenerator = Mockito.mock(RandomGenerator.class);
        target = new CarService(repository);
        car = new PassengerCar();
    }

    @Test
    void createWithRandomCount() {
//      initialize
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(3);
        final int expected = 3;


//      action
        final int actual = target.createRandomAmountOfCars(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectNull() {
//      initialize
        final int expected = -1;


//      action
        final int actual = target.createRandomAmountOfCars(null);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectZero() {
//      initialize
        final int expected = -1;
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(0);


//      action
        final int actual = target.createRandomAmountOfCars(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createWithRandomCountIncorrectOutOfTheBound() {
//      initialize
        final int expected = -1;
        Mockito.when(randomGenerator.getRandomNumber()).thenReturn(-5);


//      action
        final int actual = target.createRandomAmountOfCars(randomGenerator);


//      checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
//      initialize

//      action
        final Car car = target.createCar(CarTypes.CAR);


//      checks
        Assertions.assertNotNull(car);
        Mockito.verify(repository).save(car);
    }

    @Test
    void createIncorrectTypeNull() {
//      initialize

//      action
        final Car car = target.createCar(null);


//      checks
        Assertions.assertNull(car);
        Mockito.verify(repository, Mockito.never()).save(car);
    }

    @Test
    void createWithCount() {
//      initialize
        int count = 5;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.createCar(count, CarTypes.CAR));
    }

    @Test
    void createWithCountIncorrectCount() {
//      initialize
        int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.createCar(count, CarTypes.CAR));
    }

    @Test
    void createWithCountIncorrectTypeNull() {
//      initialize
        int count = -8;

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.createCar(count, null));
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
        Mockito.verify(repository, Mockito.never()).updateColor(car.getId(), CarColors.BLACK);
    }

    @Test
    void changeRandomColorIncorrectIdEmpty() {
//      initialize
        String id = "";

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.changeRandomColor(id));
        Mockito.verify(repository, Mockito.never()).updateColor(car.getId(), CarColors.BLACK);
    }

    @Test
    void createWithThreeIncorrectParametersNull() {
//      initialize

//      action
        car = target.createCar(null, null, null, null);

//      checks
        Assertions.assertNull(car);
        Mockito.verify(repository, Mockito.never()).save(null);
    }

    @Test
    void createWithThreeParameters() {
//      initialize
        CarsManufacturers manufacturer = car.getManufacturer();
        Engine engine = car.getEngine();
        CarColors color = car.getColor();

//      action
        Car actual = target.createCar(manufacturer, engine, color, CarTypes.CAR);

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

    @Test
    void carEquals() throws CloneNotSupportedException {
//      initialize
        final Car carCopy = car.clone();

//      action
        final boolean answer = target.carEquals(car, carCopy);

//      checks
        Assertions.assertEquals(true, answer);
    }

    @Test
    void carEqualsCarsAreNotEqual() {
//      initialize
        final Car anotherCar = new PassengerCar();

//      action
        final boolean answer = target.carEquals(car, anotherCar);

//      checks
        Assertions.assertNotEquals(true, answer);
    }

    @Test
    void carEqualsIncorrectCarsNull() {
//      initialize
        final Car anotherCar = null;
        car = null;

//      action
        final boolean answer = target.carEquals(car, anotherCar);

//      checks
        Assertions.assertEquals(false, answer);
    }

    @Test
    void printManufacturerAndCount() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printManufacturerAndCount(car));
    }

    @Test
    void printManufacturerAndCountIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printManufacturerAndCount(null));
    }

    @Test
    void printColor() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printColor(car));
    }

    @Test
    void printColorIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printColor(null));
    }

    @Test
    void checkCount() {
//      initialize
        car.setCount(11);

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.checkCount(car));
    }

    @Test
    void checkCountIncorrectNumber() {
//      initialize
        car.setCount(0);

//      action

//      checks
        Assertions.assertThrows(UserInputException.class, () -> target.checkCount(car));
    }

    @Test
    void checkCountIncorrectCarNull() {
//      initialize
        car.setCount(0);

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.checkCount(null));
    }

    @Test
    void printEngineInfo() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printEngineInfo(car));
    }

    @Test
    void printEngineInfoIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printEngineInfo(null));
    }

    @Test
    void printInfo() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printInfo(car));
    }

    @Test
    void printInfoIncorrectCarNull() {
//      initialize

//      action

//      checks
        Assertions.assertDoesNotThrow(() -> target.printInfo(null));
    }
}