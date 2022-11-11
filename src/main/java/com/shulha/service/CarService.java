package com.shulha.service;
import com.shulha.model.*;

import java.util.Random;

public class CarService {
    private static final Random RANDOM = new Random();

    private CarsManufacturers getRandomManufacturer() {
        CarsManufacturers[] carsManufacturers = CarsManufacturers.values();
        int randomIndex = RANDOM.nextInt(carsManufacturers.length);
        CarsManufacturers carsManufacturer = carsManufacturers[randomIndex];
        return carsManufacturer;
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
        CarsColors carColor = carsColors[randomIndex];
        return carColor;
    }
    public Car create() {
//      creating a random manufacturer
        getRandomManufacturer();

//      creating a random engine
        getRandomEngine();

//      creating a random color
        getRandomColor();

        return new Car(getRandomManufacturer(), getRandomEngine(), getRandomColor());
    }

    public Car create(final CarsManufacturers manufacturer, final Engine engine, final CarsColors color) {
        return new Car(manufacturer, engine, color);
    }

    public void print(Car car) {
        System.out.println(car.toString());
    }

    public static void check(Car car) {
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
