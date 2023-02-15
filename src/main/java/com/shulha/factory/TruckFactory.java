package com.shulha.factory;

import com.shulha.builder.Builder;
import com.shulha.builder.CarBuilder;
import com.shulha.builder.TruckBuilder;
import com.shulha.model.CarTypes;

public class TruckFactory extends CarFactory {
    @Override
    public TruckBuilder createCarBuilder() {
        return new TruckBuilder();
    }
}
