package com.shulha.service;
import com.shulha.model.Car;

import java.util.Random;

public class CarService {
    private final Random random = new Random();

    private String carsManufacturer;
    private String carEngine;
    private String carColor;
    private Car car;
    private int randomIndex;

    private String getRandomManufacturer() {
        CarsManufacturers[] carsManufacturers = CarsManufacturers.values();
        randomIndex = random.nextInt(carsManufacturers.length);
        carsManufacturer = carsManufacturers[randomIndex].toString();
        return carsManufacturer;
    }

    private String getRandomEngine() {
        CarsEngines[] carsEngines = CarsEngines.values();
        randomIndex = random.nextInt(carsEngines.length);
        carEngine = carsEngines[randomIndex].toString();
        return carEngine;
    }

    private String getRandomColor() {
        CarsColors[] carsColors = CarsColors.values();
        randomIndex = random.nextInt(carsColors.length);
        carColor = carsColors[randomIndex].toString();
        return carColor;
    }
    public Car create() {
//      creating a random manufacturer
        getRandomManufacturer();

//      creating a random engine
        getRandomEngine();

//      creating a random color
        getRandomColor();

        car = new Car(carsManufacturer, carEngine, carColor);
        return car;
    }

    public Car create(final String manufacturer, final String engine, final String color) {
        if (manufacturer.isBlank()) {
//          creating a random manufacturer
            getRandomManufacturer();
        } else {
            this.carsManufacturer = manufacturer;
        }

        if (engine.isBlank()) {
//          creating a random engine
            getRandomEngine();
        } else {
            this.carEngine = engine;
        }

        if (color.isBlank()) {
//          creating a random color
            getRandomColor();
        } else {
            this.carColor = color;
        }

        car = new Car(carsManufacturer, carEngine, carColor);
        return car;
    }



    public void print() {
        System.out.println("Manufacturer: " + car.getManufacturer());
        System.out.println("Engine: " + car.getEngine());
        System.out.println("Color: " + car.getColor());
        System.out.println("Price: " + car.getPrice());
        System.out.println("Count: " + car.getCount());
        System.out.println();
    }
}
