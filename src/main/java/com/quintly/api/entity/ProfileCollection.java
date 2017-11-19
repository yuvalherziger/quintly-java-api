package com.quintly.api.entity;

import java.util.ArrayList;

public class ProfileCollection {
    private boolean success;

    private ArrayList<Profile> data;

    public boolean isSuccess() {
        return this.success;
    }

    public ArrayList<Profile> getData() {
        return this.data;
    }
}
