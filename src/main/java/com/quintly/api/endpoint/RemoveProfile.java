package com.quintly.api.endpoint;

import org.apache.commons.lang3.Validate;

public class RemoveProfile implements Endpoint {

    private final String edge = "remove-profile";
    private Integer spaceId;
    private Integer profileId;

    public RemoveProfile(Integer spaceId, Integer profileId) {
        Validate.notNull(spaceId, "The 'spaceId' parameter is mandatory");
        Validate.notNull(profileId, "The 'profileId' parameter is mandatory");
        this.spaceId = spaceId;
        this.profileId = profileId;
    }

    public String getPathAsString() {
        return String.format(
                "%s?spaceId=%s&profileId=%s",
                this.edge,
                this.spaceId.toString(),
                this.profileId.toString()
        );
    }
}
