package com.quintly.api.endpoint;

public class ListProfiles implements Endpoint {

    private final String edge = "list-profiles";
    private Integer groupId;

    public ListProfiles() { }

    public ListProfiles(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPathAsString() {
        if (this.groupId == null) {
            return this.edge;
        } else {
            return String.format("%s?groupId=%s", this.edge, this.groupId.toString());
        }
    }
}
