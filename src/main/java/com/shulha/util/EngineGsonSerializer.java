package com.shulha.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.shulha.model.Engine;

import java.lang.reflect.Type;

public class EngineGsonSerializer implements JsonSerializer<Engine> {
    @Override
    public JsonElement serialize(final Engine engine, final Type type,
                                 final JsonSerializationContext jsonSerializationContext) {
        final JsonObject engineJson = new JsonObject();

        engineJson.addProperty("id", engine.getId());
        engineJson.addProperty("type", engine.getType().toString());
        engineJson.addProperty("power", engine.getPower());

        return engineJson;
    }
}
