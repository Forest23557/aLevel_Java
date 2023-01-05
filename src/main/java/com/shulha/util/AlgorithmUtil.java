package com.shulha.util;

import com.shulha.model.Car;
import com.shulha.model.UserInputException;
import com.shulha.service.CarService;

import java.util.Objects;
import java.util.Optional;

public class AlgorithmUtil {
    private static final CarService CAR_SERVICE = CarService.getInstance();

    public static Car[] bubbleSort(final Car[] carArray) {
        Optional.ofNullable(carArray)
                .orElseThrow(() -> new NullPointerException("Your array is null"));

        int length = carArray.length;
        final Car[] sortedCarArray = carArray.clone();

        for (int i = length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (CAR_SERVICE.compareCar(sortedCarArray[j], sortedCarArray[j + 1]) > 0) {

                    Car temp = sortedCarArray[j];
                    sortedCarArray[j] = sortedCarArray[j + 1];
                    sortedCarArray[j + 1] = temp;
                }
            }
        }

        return sortedCarArray;
    }

    public static Car binarySearch(final Car[] carArray, final int start, final int end, final String id) {
        if (Objects.nonNull(carArray) && Objects.nonNull(id) && !id.isBlank()) {
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
