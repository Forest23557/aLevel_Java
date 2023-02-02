package com.shulha.model;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Engine {
    private final String id;
    private int power;
    private EngineTypes type;

    public Engine() {
        this(250, EngineTypes.TURBOCHARGED);
    }

    public Engine(int power, EngineTypes type) {
        this.id = UUID.randomUUID().toString();
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
        return Objects.equals(id, engine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("engine id - %s, engine power - %s hp, engine type - %s",
                id, power, type);
    }
}
