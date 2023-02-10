package com.shulha.util;

import com.google.gson.*;
import com.shulha.model.Order;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class OrderGsonDeserializer implements JsonDeserializer<Order> {
    @Override
    public Order deserialize(final JsonElement json, final Type type,
                             final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final Order order = new Order();

        final String id = jsonObject.get("_id").getAsString();
        final LocalDateTime date =
                LocalDateTime.parse(
                        jsonObject.get("date")
                                .getAsString()
                );

        order.setId(id);
        order.setDate(date);

        return order;
    }
}
