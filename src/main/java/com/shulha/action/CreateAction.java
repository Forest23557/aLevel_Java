package com.shulha.action;

import com.shulha.model.CarTypes;

public class CreateAction implements Action {
    private static final int DEFAULT_COUNT = 10;

    @Override
    public void execute() {
        CAR_SERVICE.createCar(DEFAULT_COUNT, CarTypes.CAR);
        System.out.printf("You have just created %d cars%n", DEFAULT_COUNT);
    }
}
