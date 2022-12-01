package com.shulha;

import com.shulha.model.*;
import com.shulha.service.CarService;
import com.shulha.util.RandomGenerator;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
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

        final Car testCar = carService.createCar(CarTypes.CAR);
        final Car testCar1 = carService.find(testCar.getId());
        carService.print(testCar);
        carService.print(testCar1);
        final Car testCar2 = carService.createCar(CarTypes.CAR);

        System.out.println(carService.carEquals(testCar, testCar1));
        System.out.println(carService.carEquals(testCar1, testCar2));
        System.out.println(carService.carEquals(testCar, testCar2));
    }
}
