package com.shulha.model;

import lombok.Getter;

@Getter
public class Engine {
    private int power;
    private EngineTypes type;

    public Engine() {
        this(250, EngineTypes.TURBOCHARGED);
    }

    public Engine(int power, EngineTypes type) {
        this.type = type;

        if(power < 0 || power > 1000) {
            return;
        }
        this.power = power;
    }

    public void setPower(int power) {
        if(power < 0 || power > 1000) {
            return;
        }
        this.power = power;
    }

    public void setType(EngineTypes type) {
        if (type == null) {
            return;
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("the power - %s hp, the type - %s", power, type);
    }
}
