package com.quintly.api;

import com.google.gson.GsonBuilder;

public class ModelParser<T> {

    private Class<T> type;

    public ModelParser(Class<T> type) { this.type = type; }

    public T run(String message) {
        return new GsonBuilder().create().fromJson(message, type);
    }
}
