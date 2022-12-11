package com.shulha.action;

import com.shulha.service.CarService;

import java.io.IOException;

public interface Action {
    CarService CAR_SERVICE = CarService.getInstance();

    void execute();
}
