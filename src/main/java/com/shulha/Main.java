package com.shulha;

import com.shulha.model.*;
import com.shulha.service.CarService;
import com.shulha.util.RandomGenerator;

import java.util.Objects;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        final CarService carService = new CarService();

//        final Car passengerCar = carService.createCar(CarTypes.CAR);
//        final Car truck = carService.createCar(CarTypes.TRUCK);
//
//        CarService.check(passengerCar);
//        CarService.check(truck);
//
//        final PassengerCar passengerCar1 = (PassengerCar) passengerCar;
//        final Truck truck1 = (Truck) truck;
//
//        truck1.restore();
//        passengerCar1.restore();
//
//        carService.createRandomAmountOfCars(new RandomGenerator());
//        carService.printAll();

//        carService.printAll();

//        carService.print(passengerCar);
//        carService.print(truck);

//        carService.delete(passengerCar.getId());
//        carService.printAll();
//        carService.insert(3, new Truck());
//        carService.printAll();
//        System.out.println(carService.find(passengerCar.getId()).toString());

        final PassengerCar testCar = (PassengerCar) carService.createCar(CarTypes.CAR);
        final PassengerCar testCar1 = (PassengerCar) carService.createCar(testCar.getManufacturer(), testCar.getEngine(),
                testCar.getColor(), testCar.getType());
        testCar1.setPrice(testCar.getPrice());
        testCar1.setCount(testCar.getCount());
        testCar1.setPassengerCount(testCar.getPassengerCount());

        carService.print(testCar);
        carService.print(testCar1);

        final Car testCar2 = testCar.clone();
        carService.print(testCar2);

        final Car tesCar3 = testCar.clone();
        tesCar3.setManufacturer(CarsManufacturers.FORD);
        System.out.println(tesCar3.toString());


        System.out.println(carService.carEquals(testCar, testCar1));
//        System.out.println(carService.carEquals(testCar1, testCar2));
        System.out.println(carService.carEquals(testCar, testCar2));
        System.out.println(carService.carEquals(testCar, tesCar3));

        System.out.println(carService.carEquals(null, testCar2));
        System.out.println();

        System.out.println(testCar.hashCode());

        testCar.setCount(3);
        testCar.setColor(CarColors.AQUAMARINE);
        System.out.println(testCar.hashCode());

        testCar.setManufacturer(CarsManufacturers.BMW);
        System.out.println(testCar.hashCode());
    }
}
