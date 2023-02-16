package com.shulha.builder;

import com.shulha.model.*;

public class PassengerCarBuilder extends CarBuilder {
    private PassengerCar passengerCar;

    public PassengerCarBuilder() {
        super(new PassengerCar());
        passengerCar = (PassengerCar) super.car;
    }

    public Builder setPassengerCount(final int passengerCount) {
        passengerCar.setPassengerCount(passengerCount);
        return this;
    }

    @Override
    public PassengerCarBuilder getBuilder() {
        return this;
    }
}
