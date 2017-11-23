package com.quintly.api.entity;

import java.util.ArrayList;
import java.util.Map;

public class BadResponse {
    private boolean success;

    private Map<String, String> error;

    public String getMessage() {
        return this.error.get("message");
    }
}
