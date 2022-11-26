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
    public int createPassengerCar(final RandomGenerator randomGenerator) {
        if (randomGenerator == null) {
            return -1;
        }

        int count = randomGenerator.getRandomNumber();
        if (count <= 0 || count > 10) {
            return -1;
        }

        createPassengerCar(count);
        printAll();

        return count;
    }

    //  tested
    public int createTruck(final RandomGenerator randomGenerator) {
        if (randomGenerator == null) {
            return -1;
        }

        int count = randomGenerator.getRandomNumber();
        if (count <= 0 || count > 10) {
            return -1;
        }

        createTruck(count);
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

    private CarColors getRandomColor() {
        CarColors[] carColors = CarColors.values();
        int randomIndex = RANDOM.nextInt(carColors.length);
        return carColors[randomIndex];
    }

    private int getRandomPassengerCount() {
        return RANDOM.nextInt(4) + 2;
    }

    private int getRandomTruckCapacity() {
        return RANDOM.nextInt(1401) + 100;
    }

//  tested
    public PassengerCar createPassengerCar() {
        final PassengerCar passengerCar = new PassengerCar(getRandomManufacturer(), getRandomEngine(), getRandomColor(), getRandomPassengerCount());
        carArrayRepository.save(passengerCar);
        return passengerCar;
    }

//  tested
    public Truck createTruck() {
        final Truck truck = new Truck(getRandomManufacturer(), getRandomEngine(), getRandomColor(), getRandomTruckCapacity());
        carArrayRepository.save(truck);
        return truck;
    }

//  tested
    public void createPassengerCar(final int count) {
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            createPassengerCar();
        }
    }

    //  tested
    public void createTruck(final int count) {
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            createTruck();
        }
    }

//  tested
    public void insert(int index, final Car car) {
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
        final CarColors color = car.getColor();
        CarColors randomColor;

        do {
            randomColor = getRandomColor();
        } while (randomColor == color);
        carArrayRepository.updateColor(car.getId(), randomColor);
    }

//  tested
    public PassengerCar createPassengerCar(final CarsManufacturers manufacturer, final Engine engine, final CarColors color, final int passengerCount) {
        if (manufacturer == null || engine == null || color == null || passengerCount <= 0) {
            return null;
        }

        PassengerCar passengerCar = new PassengerCar(manufacturer, engine, color, passengerCount);
        carArrayRepository.save(passengerCar);
        return passengerCar;
    }

//  tested
    public Truck createTruck(final CarsManufacturers manufacturer, final Engine engine, final CarColors color, final int loadCapacity) {
        if (manufacturer == null || engine == null || color == null || loadCapacity <= 100) {
            return null;
        }
        Truck truck = new Truck(manufacturer, engine, color, loadCapacity);
        carArrayRepository.save(truck);
        return truck;
    }

//  tested
    public void print(Car car) {
        if (car == null) {
            System.out.println("Error! Car isn't delivered");
            return;
        }
        System.out.println(car.toString());
    }

//  tested
    public static void check(Car car) {
        if (car == null) {
            System.out.println("Error! Car isn't delivered");
            return;
        }

//      checks
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
