package com.quintly.api.entity;

import java.util.ArrayList;

public class Data implements BaseEntity {
    private boolean success;

    private ArrayList<Object> data;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Object> getData() {
        return data;
    }
}
