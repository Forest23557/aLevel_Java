package com.shulha.builder;

import com.shulha.model.*;

public class TruckBuilder extends CarBuilder {
    private Truck truck;

    public TruckBuilder() {
        super(new Truck());
        truck = (Truck) super.car;
    }

    public Builder setLoadCapacity(final int loadCapacity) {
        truck.setLoadCapacity(loadCapacity);
        return this;
    }

    @Override
    public TruckBuilder getBuilder() {
        return this;
    }
}
