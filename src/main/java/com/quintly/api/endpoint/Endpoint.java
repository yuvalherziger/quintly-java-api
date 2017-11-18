package com.quintly.api.endpoint;

import java.io.UnsupportedEncodingException;

public interface Endpoint {
    String getPathAsString() throws UnsupportedEncodingException;
}
