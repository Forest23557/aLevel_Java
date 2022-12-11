package com.shulha;

import com.shulha.model.*;
import com.shulha.service.CarService;
import com.shulha.util.AlgorithmUtil;
import com.shulha.util.RandomGenerator;

import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        final CarService carService = CarService.getInstance();

        final Car car = carService.createCar(CarTypes.CAR);
        carService.createCar(9, CarTypes.CAR);

//        int[] array = {10, 4, 48, 32, 56, 60};
//        System.out.println(Arrays.toString(array));
//        AlgorithmUtil.bubbleSort(array);
//        System.out.println(Arrays.toString(array));

        carService.printAll();
        AlgorithmUtil.bubbleSort(carService.getAll());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        carService.printAll();

//        carService.printManufacturerAndCount(car);
//        carService.printManufacturerAndCount(null);
//
//        carService.printColor(car);
//        carService.printColor(null);
//
//        car.setCount(11);
//        carService.checkCount(car);
//        car.setCount(5);
//        carService.checkCount(car);
//
//        carService.printEngineInfo(car);
//        carService.printEngineInfo(null);
//
//        carService.printInfo(car);
//        carService.printInfo(null);
    }
}
