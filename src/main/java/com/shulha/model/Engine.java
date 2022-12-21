package com.shulha.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Engine {
    private int power;
    private EngineTypes type;

    public Engine() {
        this(250, EngineTypes.TURBOCHARGED);
    }

    public Engine(int power, EngineTypes type) {
        if(power < 0 || power > 1000) {
            return;
        }
        this.power = power;

        if (type == null) {
            return;
        }
        this.type = type;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return power == engine.power && type == engine.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, type);
    }

    @Override
    public String toString() {
        return String.format("the power of the engine - %s hp, the type of the engine - %s", power, type);
    }
}
