package com.quintly.api;

import com.google.gson.Gson;
import com.quintly.api.endpoint.Endpoint;
import com.quintly.api.entity.*;
import com.quintly.api.exception.IncompatibleGetterException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Response {
    private HttpResponse httpResponse;
    private String message;
    private Endpoint endpoint;

    public Response(HttpResponse httpResponse, Endpoint endpoint) {
        this.httpResponse = httpResponse;
        this.endpoint = endpoint;
        HttpEntity entity = this.httpResponse.getEntity();
        try {
            this.message = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) { }
    }

    public String getMessage() throws IOException {
        return this.message;
    }

    public BaseResponseData getData() throws IOException {
        Gson gsonResponse = new Gson();
        Data data = gsonResponse.fromJson(this.getMessage(), Data.class);
        return data;
    }

    public ProfileCollection getProfilesCollection() throws IOException, IncompatibleGetterException {
        if (this.isProfilesRequest(endpoint)) {
            throw new IncompatibleGetterException("Cannot fetch profiles for this type of endpoint request.");
        }
        Gson gsonResponse = new Gson();
        ProfileCollection profileCollection = gsonResponse.fromJson(this.getMessage(), ProfileCollection.class);
        return profileCollection;
    }

    public int getStatusCode() {
        return this.httpResponse.getStatusLine().getStatusCode();
    }

    private boolean isProfilesRequest(Endpoint endpoint) {
        if (endpoint.getClass().getName().equals("ListProfiles")) {
            return true;
        }
        return false;
    }
}
