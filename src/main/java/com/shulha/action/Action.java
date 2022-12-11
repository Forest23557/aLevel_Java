package com.shulha.action;

import com.shulha.service.CarService;

public interface Action {
    CarService CAR_SERVICE = CarService.getInstance();

    void execute();
}
