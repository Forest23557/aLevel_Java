package com.shulha.service;

import com.shulha.model.*;
import com.shulha.repository.CarArrayRepository;
import com.shulha.util.RandomGenerator;

import java.util.Random;

public class CarService {
    private final static Random RANDOM = new Random();

    private final CarArrayRepository carArrayRepository;

    public CarService() {
        this(new CarArrayRepository());
    }

    public CarService(final CarArrayRepository carArrayRepository) {
        this.carArrayRepository = carArrayRepository;
    }

//  tested
    public int create(final RandomGenerator randomGenerator) {
        if (randomGenerator == null) {
            return -1;
        }

        int count = randomGenerator.getRandomNumber();
        if (count <= 0 || count > 10) {
            return -1;
        }

        create(count);
        printAll();

        return count;
    }

    private CarsManufacturers getRandomManufacturer() {
        CarsManufacturers[] carsManufacturers = CarsManufacturers.values();
        int randomIndex = RANDOM.nextInt(carsManufacturers.length);
        return carsManufacturers[randomIndex];
    }

    private Engine getRandomEngine() {
        EngineTypes[] engineTypes = EngineTypes.values();
        int randomIndex = RANDOM.nextInt(engineTypes.length);
        EngineTypes engineType = engineTypes[randomIndex];
        int randomPower = RANDOM.nextInt(1001);
        return new Engine(randomPower, engineType);
    }

    private CarsColors getRandomColor() {
        CarsColors[] carsColors = CarsColors.values();
        int randomIndex = RANDOM.nextInt(carsColors.length);
        return carsColors[randomIndex];
    }

//  tested
    public Car create() {
        final Car car = new Car(getRandomManufacturer(), getRandomEngine(), getRandomColor());
        carArrayRepository.save(car);
        return car;
    }

//  tested
    public void create(final int count) {
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            create();
        }
    }

//  tested
    public void insert(int index, final Car car) {
        if (index < 0) {
            return;
        }

        if (car == null) {
            return;
        }
        carArrayRepository.insert(index, car);
    }

//  tested
    public void printAll() {
        final Car[] allCars = carArrayRepository.getAll();

        if (allCars == null) {
            return;
        }

        for (int i = 0; i < allCars.length; i++) {
            System.out.println(allCars[i]);
        }
    }

//  tested
    public Car[] getAll() {
        return carArrayRepository.getAll();
    }

//  tested
    public Car find(final String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        return carArrayRepository.getById(id);
    }

//  tested
    public void delete(final String id) {
        if (id == null || id.isBlank()) {
            return;
        }
        carArrayRepository.delete(id);
    }

//  tested
    public void changeRandomColor(final String id) {
        if (id == null || id.isBlank()) {
            return;
        }

        final Car car = find(id);
        if (car == null) {
            return;
        }

        findAndChangeRandomColor(car);
    }

    private void findAndChangeRandomColor(final Car car) {
        final CarsColors color = car.getColor();
        CarsColors randomColor;

        do {
            randomColor = getRandomColor();
        } while (randomColor == color);
        carArrayRepository.updateColor(car.getId(), randomColor);
    }

//  tested
    public Car create(final CarsManufacturers manufacturer, final Engine engine, final CarsColors color) {
        if (manufacturer == null || engine == null || color == null) {
            return null;
        }
        Car car = new Car(manufacturer, engine, color);
        carArrayRepository.save(car);
        return car;
    }

    public void print(Car car) {
        if (car == null) {
            System.out.println("Error! Car isn't delivered");
            return;
        }
        System.out.println(car.toString());
    }

    public static void check(Car car) {
        if (car == null) {
            System.out.println("Error! Car isn't delivered");
            return;
        }

        if (car.getCount() > 0 && car.getEngine().getPower() > 200) {
            System.out.println("The car is ready for sale");
        } else if (car.getCount() < 0) {
            System.out.println("The count is wrong");
        } else if (car.getEngine().getPower() < 200) {
            System.out.println("The power of the engine is wrong");
        } else {
            System.out.println("The count and the power of the engine are wrong");
        }
        System.out.println();
    }
}
