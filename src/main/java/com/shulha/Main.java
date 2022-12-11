package com.shulha;

import com.shulha.action.Actions;
import com.shulha.util.UserInput;
import lombok.SneakyThrows;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
//        final CarService carService = CarService.getInstance();
//
//        final Car car = carService.createCar(CarTypes.CAR);
//        final String id = car.getId();
//        carService.createCar(9, CarTypes.CAR);
//
//        carService.printAll();
//        final Car[] sortedCarArray = AlgorithmUtil.bubbleSort(carService.getAll());
//        System.out.println("~".repeat(20));
//        for (int i = 0; i < sortedCarArray.length; i++) {
//            carService.print(sortedCarArray[i]);
//        }
//
//        final Car car1 = AlgorithmUtil.binarySearch(sortedCarArray,
//                0, carService.getAll().length - 1, id);
//
//        System.out.println("~".repeat(20));
//        Optional.ofNullable(car1).ifPresent(car2 -> {
//            System.out.println(car2);
//        });

        final Actions[] values = Actions.values();
        String[] names = mapActionToName(values);

        while (true) {
            final int userChoice = UserInput.menu(names);
            values[userChoice].execute();
        }
    }

    private static String[] mapActionToName(final Actions[] values) {
        String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].getName();
        }
        return names;
    }
}
