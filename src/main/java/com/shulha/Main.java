package com.shulha;

import com.shulha.model.Car;
import com.shulha.service.CarService;
import com.shulha.util.RandomGenerator;

public class Main {
    public static void main(String[] args) {
        final CarService carService = new CarService();

        final Car randomCar = carService.create();
        final Car randomCar1 = carService.create();
        final Car randomCar2 = carService.create();

//        carService.print(randomCar);
//        CarService.check(randomCar);
//
//        carService.printAll();
//
//        System.out.println(carService.find(randomCar1.getId()));
//
//        carService.create(3);
//        carService.printAll();
//
//        final Car car = carService.create();
//        carService.insert(0, car);
//        carService.printAll();
//
//        carService.delete(randomCar1.getId());
//        carService.printAll();
//
//        final Car[] all = carService.getAll();
//        final Car car1 = all[0];
//        carService.print(car);
//        carService.changeRandomColor(car.getId());
//        System.out.println(carService.find(car.getId()));


//        System.out.println(carService.create(new RandomGenerator()));
        carService.create(null, null, null);
        carService.printAll();
        carService.create();
        carService.printAll();
    }
}
