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
        System.out.println();
    }
}
