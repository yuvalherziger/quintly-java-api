package com.quintly.api;

import org.apache.commons.lang3.Validate;

public class Credentials {
    private Integer clientId;
    private String clientSecret;

    public Credentials(Integer clientId, String clientSecret) {
        this.validate(clientId, clientSecret);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private void validate(Integer clientId, String clientSecret) {
        Validate.notNull(clientId, "The 'clientId' parameter is mandatory");
        Validate.notNull(clientSecret, "The 'clientSecret' parameter is mandatory");
        Validate.notEmpty(clientSecret, "The 'clientSecret' parameter cannot be empty");
    }

    public String toString() {
        return String.format("%s:%s", this.clientId, this.clientSecret);
    }
}
