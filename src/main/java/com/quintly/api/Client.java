package com.quintly.api;

import com.quintly.api.endpoint.Endpoint;
import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class Client {

    private final String host = "api.quintly.com";
    private final String scheme = "https";
    private final String version = "v0.9";

    private CloseableHttpClient httpClient;

    public Client(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response executeGet(Credentials credentials, Endpoint endpoint, HttpGet httpGet) throws IOException {
        String fullyQualifiedUrl = String.format(
                "%s://%s@%s/%s/%s",
                this.scheme,
                credentials.toString(),
                this.host,
                this.version,
                endpoint.getPathAsString()
        );

        return new Response(this.httpClient.execute(httpGet), endpoint);
    }

    public Response executeGet(Credentials credentials, Endpoint endpoint) throws IOException {
        String fullyQualifiedUrl = String.format(
                "%s://%s@%s/%s/%s",
                this.scheme,
                credentials.toString(),
                this.host,
                this.version,
                endpoint.getPathAsString()
        );

        return new Response(this.httpClient.execute(new HttpGet(fullyQualifiedUrl)), endpoint);
    }
}
