package com.shulha.service;
import com.shulha.model.Car;

import java.util.Random;

public class CarService {
    private final Random RANDOM = new Random();

    private String getRandomManufacturer() {
        CarsManufacturers[] carsManufacturers = CarsManufacturers.values();
        int randomIndex = RANDOM.nextInt(carsManufacturers.length);
        String carsManufacturer = carsManufacturers[randomIndex].toString();
        return carsManufacturer;
    }

    private String getRandomEngine() {
        CarsEngines[] carsEngines = CarsEngines.values();
        int randomIndex = RANDOM.nextInt(carsEngines.length);
        String carEngine = carsEngines[randomIndex].toString();
        return carEngine;
    }

    private String getRandomColor() {
        CarsColors[] carsColors = CarsColors.values();
        int randomIndex = RANDOM.nextInt(carsColors.length);
        String carColor = carsColors[randomIndex].toString();
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

    public Car create(final String manufacturer, final String engine, final String color) {
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
