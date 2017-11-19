package com.quintly.api.entity;

import java.util.ArrayList;

public class Profile implements BaseEntity {
    private int id;
    private String name;
    private String appendix;
    private String platformType;
    private String platformId;
    private String platformUsername;
    private String url;
    private String color;
    private String audienceDataSinceTime;
    private String interactionDataSinceTime;
    private ArrayList<Space> spaces;
    private ArrayList<Group> groups;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAppendix() {
        return appendix;
    }

    public String getPlatformType() {
        return platformType;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getPlatformUsername() {
        return platformUsername;
    }

    public String getUrl() {
        return url;
    }

    public String getColor() {
        return color;
    }

    public String getAudienceDataSinceTime() {
        return audienceDataSinceTime;
    }

    public String getInteractionDataSinceTime() {
        return interactionDataSinceTime;
    }

    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
}
