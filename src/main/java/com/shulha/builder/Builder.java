package com.shulha.builder;

import com.shulha.model.*;

public interface Builder {
    int MIN_PRICE = 1000;

    Builder chooseManufacturer(final CarManufacturers carManufacturer);

    Builder insertEngine(final int enginePower, final EngineTypes engineType);

    Builder paintCar(final CarColors color);

    Builder setCount(final int count);

    Builder setPrice(final int price);

    Car getCar();
}
