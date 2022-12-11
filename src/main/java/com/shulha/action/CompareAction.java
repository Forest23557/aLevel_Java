package com.shulha.action;

import com.shulha.model.Car;
import com.shulha.model.CarTypes;

public class CompareAction implements Action {
    @Override
    public void execute() {
        CAR_SERVICE.createCar(5, CarTypes.CAR);
        final Car[] all = CAR_SERVICE.getAll();
        for (int i = 0; i < all.length - 1; i++) {
            Car currentCar = all[i];
            Car nextCar = all[i + 1];
            final int compare = CAR_SERVICE.compareCar(currentCar, nextCar);
            System.out.println("Current car: " + currentCar.getId());
            System.out.println("Next car: " + nextCar.getId());
            System.out.println("If the compare is positive it means the current car is bigger than the next car");
            System.out.println("If the compare is negative it means the current car is smaller than the next car");
            System.out.println("Compare: " + compare);
            System.out.println("-_- ".repeat(5));
        }
    }
}
