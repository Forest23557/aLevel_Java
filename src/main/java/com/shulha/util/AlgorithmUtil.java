package com.shulha.util;

import com.shulha.model.Car;
import com.shulha.service.CarService;

import java.util.Optional;

public class AlgorithmUtil {
    private static final CarService CAR_SERVICE = CarService.getInstance();

    public static void bubbleSort(final Car[] carArray) {
        if (Optional.ofNullable(carArray).isPresent()) {
            int length = carArray.length;

            for (int i = length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (CAR_SERVICE.compareCar(carArray[j], carArray[j + 1]) > 0) {
                        CAR_SERVICE.replaceCarsFromRepository(j, j + 1);
                    }
//                    if (carArray[j] > carArray[j + 1]) {
//                        int temp = carArray[j];
//                        carArray[j] = carArray[j + 1];
//                        carArray[j + 1] = temp;
//                    }
                }
            }
        }
    }


}
