package com.shulha.factory;

import com.shulha.builder.Builder;
import com.shulha.builder.CarBuilder;

public abstract class CarFactory {
    public abstract CarBuilder createCarBuilder();
}
