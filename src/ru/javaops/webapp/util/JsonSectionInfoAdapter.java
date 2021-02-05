package ru.javaops.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionInfoAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASSNAME); // get ClassName property
        String className = primitive.getAsString();

        try {
            Class clazz = Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject returnValue = new JsonObject();
        returnValue.addProperty(CLASSNAME, src.getClass().getName());
        JsonElement jsonElement = context.serialize(src);
        returnValue.add(INSTANCE, jsonElement);
        return returnValue;
    }
}
