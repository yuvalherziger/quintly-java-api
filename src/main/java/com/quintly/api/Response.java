package com.quintly.api;

import com.google.gson.Gson;
import com.quintly.api.entity.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Response {
    private HttpResponse httpResponse;
    private String message;

    public Response(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        HttpEntity entity = this.httpResponse.getEntity();
        try {
            this.message = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) { }
    }

    public String getMessage() throws IOException {
        return this.message;
    }

    public Data toJson() throws IOException {
        Gson gsonResponse = new Gson();
        Data data = gsonResponse.fromJson(this.getMessage(), Data.class);
        return data;
    }

    public int getStatusCode() {
        return this.httpResponse.getStatusLine().getStatusCode();
    }
}
