package com.shulha.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "passenger_car")
public class PassengerCar extends Car {
    @Column(name = "passenger_count")
    private int passengerCount;

    public PassengerCar() {
        this(CarManufacturers.AUDI, new Engine(), CarColors.BLACK, 4);
    }

    public PassengerCar (final CarManufacturers manufacturer, final Engine engine, final CarColors color, final int passengerCount) {
        super(manufacturer, engine, color);
        setType(CarTypes.CAR);
        if (passengerCount < 1 || passengerCount > 5) {
            this.passengerCount = 4;
            return;
        }
        this.passengerCount = passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        if (passengerCount < 1 || passengerCount > 5) {
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
