package com.shulha.factory;

import com.shulha.builder.Builder;
import com.shulha.builder.CarBuilder;
import com.shulha.builder.PassengerCarBuilder;
import com.shulha.model.CarTypes;

public class PassengerCarFactory extends CarFactory {
    @Override
    public PassengerCarBuilder createCarBuilder() {
        return new PassengerCarBuilder();
    }
}
