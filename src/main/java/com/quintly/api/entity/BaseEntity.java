package com.quintly.api.entity;

import java.util.ArrayList;

public interface BaseEntity {
    boolean isSuccess();

    ArrayList<Object> getData();
}
