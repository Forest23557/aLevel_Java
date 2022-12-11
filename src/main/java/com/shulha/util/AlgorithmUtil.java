package com.shulha.util;

import com.shulha.model.Car;
import com.shulha.service.CarService;

import java.util.Optional;

public class AlgorithmUtil {
    private static final CarService CAR_SERVICE = CarService.getInstance();

    public static Car[] bubbleSort(final Car[] carArray) {
        if (Optional.ofNullable(carArray).isPresent()) {
            int length = carArray.length;
            final Car[] sortedCarArray = carArray.clone();

            for (int i = length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (sortedCarArray[j].getId().compareTo(sortedCarArray[j + 1].getId()) > 0) {
//                        CAR_SERVICE.replaceCarsFromRepository(j, j + 1);
                        Car temp = sortedCarArray[j];
                        sortedCarArray[j] = sortedCarArray[j + 1];
                        sortedCarArray[j + 1] = temp;
                    }
//                    if (carArray[j] > carArray[j + 1]) {
//                        int temp = carArray[j];
//                        carArray[j] = carArray[j + 1];
//                        carArray[j + 1] = temp;
//                    }
                }
            }

            return sortedCarArray;
        }

        return null;
    }

    public static Car binarySearch(final Car[] carArray, final int start, final int end, final String id) {
        Optional<Car[]> safeCarArray = Optional.ofNullable(carArray);

        if (safeCarArray.isPresent()) {
            if (end >= start) {
                int middleElement = (end + start) / 2;

                boolean answer = carArray[middleElement].getId().equals(id);

                if (answer) {
                    return carArray[middleElement];
                }

                if (carArray[middleElement].getId().compareTo(id) > 0) {
                    return binarySearch(carArray, start, middleElement - 1, id);
                }
                return binarySearch(carArray, middleElement + 1, end, id);
            }

            return null;
        }

        return null;
    }
}
