package com.shulha;

import com.shulha.model.Car;
import com.shulha.service.CarService;

public class Main {
    public static void main(String[] args) {
        final CarService carService = new CarService();
        final Car randomCar = carService.create();
        final Car randomCar1 = carService.create();
        final Car randomCar2 = carService.create();

        carService.print(randomCar);
        carService.print(randomCar1);
        carService.print(randomCar2);
    }
}
