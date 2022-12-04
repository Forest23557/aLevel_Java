package com.shulha.service;

import com.shulha.model.*;
import com.shulha.repository.CarArrayRepository;
import com.shulha.util.RandomGenerator;

import java.util.Optional;
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

    public void printManufacturerAndCount(final Car car) {
        final Optional<Car> optionalCar = Optional.ofNullable(car);

        optionalCar.ifPresent(car1 -> {
            System.out.println("The manufacturer of the car is: " + car1.getManufacturer());
            System.out.println("The count of cars: " + car1.getCount());
            System.out.println();
        });
    }

    public void printColor(final Car car) {
        final Car existOrNewCar = Optional.ofNullable(car).orElse(createCar(CarTypes.CAR));

        System.out.println("The car with ID " + existOrNewCar.getId() + " has " + existOrNewCar.getColor() + " color");
        System.out.println();
    }

    public void checkCount(final Car car) {
        final Optional<Car> optionalCar = Optional.ofNullable(car);

        if (optionalCar.isPresent()) {
            final Car rightCar = optionalCar
                    .filter(car1 -> car1.getCount() > 10)
                    .orElseThrow(() -> new UserInputException(car.getId()));

            System.out.println("The car with ID: " + rightCar.getId());
            System.out.println("Manufacturer: " + rightCar.getManufacturer());
            System.out.println("Count: " + rightCar.getCount());
            System.out.println();
        }
    }

    public void printEngineInfo(final Car car) {
        final Car existOrNewCar = Optional.ofNullable(car)
                .orElseGet(() -> {
                    System.out.println("A new car has just been created");
                    return createCar(CarTypes.CAR);
                });
        System.out.println("ID of the car: " + existOrNewCar.getId());

        final Engine engine = Optional.of(existOrNewCar)
                .map(car1 -> car1.getEngine())
                .get();

        System.out.println("Power of the engine: " + engine.getPower());
        System.out.println();
    }

    public void printInfo(final Car car) {
        Optional.ofNullable(car).ifPresentOrElse(car1 -> print(car), () -> print(createCar(CarTypes.CAR)));
    }

    //  tested
    public int createRandomAmountOfCars(final RandomGenerator randomGenerator) {
        if (randomGenerator == null) {
            return -1;
        }

        int count = randomGenerator.getRandomNumber();
        if (count <= 0 || count > 10) {
            return -1;
        }

        for (int i = 0; i < count; i++) {
            int carTypeNumber = RANDOM.nextInt(2);

            if (carTypeNumber == 0) {
                createCar(CarTypes.CAR);
            } else {
                createCar(CarTypes.TRUCK);
            }
        }
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
        return RANDOM.nextInt(3901) + 100;
    }

    //  tested
    public Car createCar(final CarTypes carType) {
        Car car;

        if (carType == null) {
            return null;
        }

        if (carType == CarTypes.CAR) {
            car = new PassengerCar(getRandomManufacturer(), getRandomEngine(), getRandomColor(), getRandomPassengerCount());
        } else {
            car = new Truck(getRandomManufacturer(), getRandomEngine(), getRandomColor(), getRandomTruckCapacity());
        }

        carArrayRepository.save(car);

        return car;
    }

    //  tested
    public void createCar(final int count, final CarTypes carType) {
        if (count <= 0 || carType == null) {
            return;
        }

        for (int i = 0; i < count; i++) {
            createCar(carType);
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
    public Car createCar(final CarsManufacturers manufacturer, final Engine engine, final CarColors color, final CarTypes carType) {
        Car car;

        if (manufacturer == null || engine == null || color == null || carType == null) {
            return null;
        }

        if (carType == CarTypes.CAR) {
            car = new PassengerCar(manufacturer, engine, color, getRandomPassengerCount());
        } else {
            car = new PassengerCar(manufacturer, engine, color, getRandomTruckCapacity());
        }

        carArrayRepository.save(car);
        return car;
    }

    public boolean carEquals(final Car firstCar, final Car secondCar) {
        if (firstCar == null || secondCar == null) {
            return false;
        }

        if (firstCar.getType() != secondCar.getType()) {
            return false;
        }

        if (firstCar.hashCode() != secondCar.hashCode()) {
            return false;
        } else {
            if (!firstCar.equals(secondCar)) {
                return false;
            }
        }

        return true;
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
            System.out.println("The car is ready for sale. Car ID: " + car.getId());
        } else if (car.getCount() < 0) {
            System.out.println("The count is wrong. Car ID: " + car.getId());
        } else if (car.getEngine().getPower() < 200) {
            System.out.println("The power of the engine is wrong. Car ID: " + car.getId());
        } else {
            System.out.println("The count and the power of the engine are wrong. Car ID: " + car.getId());
        }
        System.out.println();
    }
}
