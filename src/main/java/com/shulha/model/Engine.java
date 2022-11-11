package com.shulha.model;

public class Engine {
    private int power;
    private EngineTypes type;

    public Engine() {
        this(250, EngineTypes.TURBOCHARGED);
    }

    public Engine(int power, EngineTypes type) {
        this.type = type;

        if(power < 0) {
            return;
        }
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        if(power < 0) {
            return;
        }
        this.power = power;
    }

    public EngineTypes getType() {
        return type;
    }

    public void setType(EngineTypes type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("the power - %s hp, the type - %s", power, type);
    }
}
