package com.quintly.api.enitity;

import java.util.ArrayList;

/**
 * This class demonstrates how a custom response model from the quintly API could look like
 */
public class MyCustomResponseModel {

    private boolean success;

    private ArrayList<MyCustomNode> data;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<MyCustomNode> getData() {
        return data;
    }
}
