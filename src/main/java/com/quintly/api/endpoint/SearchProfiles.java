package com.quintly.api.endpoint;

import org.apache.commons.lang3.Validate;

public class SearchProfiles implements Endpoint {

    private final String edge = "search-profiles";
    private String query;
    private String network;

    public SearchProfiles(String query) {
        this.validate(query);
        this.query = query;
    }

    public SearchProfiles(String query, String network) {
        this(query);
        this.network = network;
    }

    private void validate(String query) {
        Validate.notEmpty(query, "The 'query' parameter is mandatory");
    }

    public String getPathAsString() {
        if (this.network == null) {
            return String.format("%s?q=%s", this.edge, this.query);
        } else {
            return String.format("%s?q=%s&network=%s", this.edge, this.query, this.network);
        }
    }
}
