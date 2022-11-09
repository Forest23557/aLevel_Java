package com.shulha.service;
import com.shulha.model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CarService {
    public Car create() {
        final Random random = new Random();

//      creating a random manufacturer
        String randomCarsManufacturer = "";
        try {
            File cars = new File("src/main/java/com/shulha/service/carsManufacturers.txt");
            Scanner scan = new Scanner(cars);
            ArrayList<String> carsManufacturers = new ArrayList<>();

            while (scan.hasNext()) {
                carsManufacturers.add(scan.next());
            }

            int randomIndex = random.nextInt(carsManufacturers.size());
            randomCarsManufacturer = carsManufacturers.get(randomIndex).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

//      creating a random engine
        String randomCarEngine = "";
        try {
            File engines = new File("src/main/java/com/shulha/service/carsEngines.txt");
            Scanner scan = new Scanner(engines);
            ArrayList<String> carEngines = new ArrayList<>();

            while (scan.hasNext()) {
                carEngines.add(scan.next());
            }

            int randomIndex = random.nextInt(carEngines.size());
            randomCarEngine = carEngines.get(randomIndex).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //      creating a random color
        String randomCarColor = "";
        try {
            File colors = new File("src/main/java/com/shulha/service/carsColors.txt");
            Scanner scan = new Scanner(colors);
            ArrayList<String> carColors = new ArrayList<>();

            while (scan.hasNext()) {
                carColors.add(scan.next());
            }

            int randomIndex = random.nextInt(carColors.size());
            randomCarColor = carColors.get(randomIndex).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Car(randomCarsManufacturer, randomCarEngine, randomCarColor);
    }

    public static void print(final Car car) {
        System.out.println("Manufacturer: " + car.getManufacturer());
        System.out.println("Engine: " + car.getEngine());
        System.out.println("Color: " + car.getColor());
        System.out.println("Price: " + car.getPrice());
        System.out.println("Count: " + car.getCount());
        System.out.println();
    }
}
