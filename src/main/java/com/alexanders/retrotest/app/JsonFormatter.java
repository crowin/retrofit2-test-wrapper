package com.alexanders.retrotest.app;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
public class JsonFormatter {
    private Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(jsonElement.getAsJsonPrimitive().getAsLong())).create();

    public Gson getGson() {
        return gson;
    }

    public <T> T get(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public <T> List<T> getListOf(String json, Class<T> type) {
        Object [] array = (Object[])java.lang.reflect.Array.newInstance(type, 0);
        List<T> list = new ArrayList<>();
        try {
            array = gson.fromJson(json, array.getClass());
            for (Object anArray : array) list.add(type.cast(anArray));
        } catch (NullPointerException ex) {
            ex.getCause();
            log.error("json is null");
            return null;
        }
        return list;
    }

    public <T> List<T> parseFromJSON(String json, Type type) {
        Set<Map.Entry<String, JsonElement>> obj = new JsonParser().parse(json).getAsJsonObject().entrySet();
        List<Map.Entry<String, JsonElement>> listFromSet = new ArrayList<>(obj);
        List<T> parsedList = new ArrayList<>();
        for(Map.Entry<String, JsonElement> element: listFromSet) {
            T entity = gson.fromJson(element.getValue(), type);
            parsedList.add(entity);
        }
        return parsedList;
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

}
