package com.shulha.service;
import com.shulha.model.Car;
import com.shulha.model.CarsColors;
import com.shulha.model.EngineTypes;
import com.shulha.model.CarsManufacturers;

import java.util.Random;

public class CarService {
    private static final Random RANDOM = new Random();

    private CarsManufacturers getRandomManufacturer() {
        CarsManufacturers[] carsManufacturers = CarsManufacturers.values();
        int randomIndex = RANDOM.nextInt(carsManufacturers.length);
        CarsManufacturers carsManufacturer = carsManufacturers[randomIndex];
        return carsManufacturer;
    }

    private String getRandomEngine() {
        EngineTypes[] carsEngines = EngineTypes.values();
        int randomIndex = RANDOM.nextInt(carsEngines.length);
        String carEngine = carsEngines[randomIndex].toString();
        return carEngine;
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

    public Car create(final CarsManufacturers manufacturer, final String engine, final CarsColors color) {
        return new Car(manufacturer, engine, color);
    }



    public void print(Car car) {
        System.out.println("Manufacturer: " + car.getManufacturer());
        System.out.println("Engine: " + car.getEngine());
        System.out.println("Color: " + car.getColor());
        System.out.println("Price: " + car.getPrice());
        System.out.println("Count: " + car.getCount());
        System.out.println();
    }
}
