package com.shulha.builder;

import com.shulha.model.Car;
import com.shulha.model.CarColors;
import com.shulha.model.CarManufacturers;
import com.shulha.model.EngineTypes;

public interface CarBuilder {
    int MIN_PRICE = 1000;
    CarBuilder chooseManufacturer(final CarManufacturers carManufacturer);

    CarBuilder insertEngine(final int enginePower, final EngineTypes engineType);

    CarBuilder paintCar(final CarColors color);

    CarBuilder setCount(final int count);

    CarBuilder setPrice(final int price);

    CarBuilder checkCount();

    CarBuilder checkPrice();

    Car getCar();
}
