package com.quintly.api.enitity;

/**
 * This class demonstrates how a custom response model node from the quintly API could look like
 */
public class MyCustomNode {

    private int profileId;

    private String time;

    private int fans;

    public int getProfileId() {
        return profileId;
    }

    public String getTime() {
        return time;
    }

    public int getFans() {
        return fans;
    }
}
