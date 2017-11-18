package com.quintly.api.entity;

import java.util.ArrayList;
import java.util.Map;

public interface BaseEntity {
    boolean isSuccess();

    ArrayList<Map<String, Object>> getData();
}
