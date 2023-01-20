package com.shulha.action;

import com.shulha.service.CarService;
import com.shulha.util.AnnotationProcessor;

import java.io.IOException;

public interface Action {
    CarService CAR_SERVICE = (CarService) AnnotationProcessor.getCachedObject("CarService");

    void execute();
}
