package com.shulha.model;

import lombok.Getter;

@Getter
public class PassengerCar extends Car implements CountRestore{
    private int passengerCount;

    public PassengerCar() {
        this(CarsManufacturers.AUDI, new Engine(), CarColors.BLACK, 4);
    }

    public PassengerCar (final CarsManufacturers manufacturer, final Engine engine, final CarColors color, final int passengerCount) {
        super(manufacturer, engine, color);
        setType(CarTypes.CAR);
        if (passengerCount <= 1 || passengerCount > 5) {
            this.passengerCount = 4;
            return;
        }
        this.passengerCount = passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        if (passengerCount <= 1 || passengerCount > 5) {
            this.passengerCount = 4;
            return;
        }
        this.passengerCount = passengerCount;
    }

    @Override
    public void restore() {
        setCount(100);
        System.out.println("The count of passenger cars " + getManufacturer()
                + " with id " + getId() + " is " + getCount());
        System.out.println();
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Passenger count: %s%n", passengerCount);
    }
}
