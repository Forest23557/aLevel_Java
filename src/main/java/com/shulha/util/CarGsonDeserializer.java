package com.shulha.util;

import com.google.gson.*;
import com.shulha.model.*;

import java.lang.reflect.Type;

public class CarGsonDeserializer implements JsonDeserializer<Car> {
    @Override
    public Car deserialize(final JsonElement json, final Type type,
                           final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final Car car;
        final Engine engine = new Engine();

        final String id = jsonObject.get("_id").getAsString();
        final CarTypes carType = Enum.valueOf(CarTypes.class,
                jsonObject.get("type").getAsString());
        final CarManufacturers manufacturer = Enum.valueOf(CarManufacturers.class,
                jsonObject.get("manufacturer").getAsString());
        final CarColors color = Enum.valueOf(CarColors.class,
                jsonObject.get("color").getAsString());
        final int count = jsonObject.get("count").getAsInt();
        final int price = jsonObject.get("price").getAsInt();
        final String engineId = jsonObject.get("engineId").getAsString();
        engine.setId(engineId);

        if (carType.equals(CarTypes.CAR)) {
            final int passengerCount = jsonObject.get("passengerCount").getAsInt();
            final PassengerCar passengerCar = new PassengerCar(manufacturer, engine, color, passengerCount);
            car = passengerCar;
        } else {
            final int loadCapacity = jsonObject.get("loadCapacity").getAsInt();
            final Truck truck = new Truck(manufacturer, engine, color, loadCapacity);
            car = truck;
        }

        car.setId(id);
        car.setCount(count);
        car.setPrice(price);

        return car;
    }
}
