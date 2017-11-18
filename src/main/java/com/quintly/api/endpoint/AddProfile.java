package com.quintly.api.endpoint;

import org.apache.commons.lang3.Validate;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AddProfile implements Endpoint {

    private final String edge = "add-profile";
    private Integer spaceId;
    private String query;

    public AddProfile(Integer spaceId, String query) {
        Validate.notNull(spaceId, "The 'spaceId' parameter is mandatory");
        Validate.notEmpty(query, "The 'query' parameter is mandatory");
        this.spaceId = spaceId;
        this.query = query;
    }

    public String getPathAsString() throws UnsupportedEncodingException {
        return String.format(
                "%s?spaceId=%s&q=%s",
                this.edge,
                this.spaceId.toString(),
                URLEncoder.encode(this.query, "UTF-8")
        );
    }
}
