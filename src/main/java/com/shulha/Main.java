package com.shulha;

import com.shulha.model.Car;
import com.shulha.service.CarService;

public class Main {
    public static void main(String[] args) {
        final CarService randomCar = new CarService();
        final CarService randomCar1 = new CarService();
        final CarService randomCar2 = new CarService();
        randomCar.create("", "v8", "grey");
        randomCar1.create();
        randomCar2.create();

        randomCar.print();
        randomCar1.print();
        randomCar2.print();
    }
}
