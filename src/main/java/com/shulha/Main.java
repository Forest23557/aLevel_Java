package com.shulha;

import com.shulha.model.Car;
import com.shulha.service.CarService;

public class Main {
    public static void main(String[] args) {
        final Car audi = new Car();
        final Car randomCar = new CarService().create();
        final Car subaru = new Car(" Subaru", "V16", "YeLLoW");
        subaru.setCount(8);

        CarService.print(audi);
        CarService.print(randomCar);
        CarService.print(subaru);
    }
}
