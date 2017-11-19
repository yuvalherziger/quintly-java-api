package com.quintly.api.entity;

import java.util.ArrayList;
import java.util.Map;

public class Data implements BaseResponseData {
    private boolean success;

    private ArrayList<Map<String, Object>> data;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Map<String, Object>> getData() {
        return data;
    }
}
