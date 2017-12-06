package com.quintly.api;

import com.quintly.api.endpoint.Endpoint;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
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
                "%s://%s/%s/%s",
                this.scheme,
                this.host,
                this.version,
                endpoint.getPathAsString()
        );
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, this.getAuthenticationHeader(credentials));
        return new Response(this.httpClient.execute(httpGet), endpoint);
    }

    public Response executeGet(Credentials credentials, Endpoint endpoint) throws IOException {
        String fullyQualifiedUrl = String.format(
                "%s://%s/%s/%s",
                this.scheme,
                this.host,
                this.version,
                endpoint.getPathAsString()
        );
        HttpGet httpGet = new HttpGet(fullyQualifiedUrl);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, this.getAuthenticationHeader(credentials));
        return new Response(this.httpClient.execute(httpGet), endpoint);
    }

    private String getAuthenticationHeader(Credentials credentials) {
        String auth = credentials.toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        return "Basic " + new String(encodedAuth);
    }
}
