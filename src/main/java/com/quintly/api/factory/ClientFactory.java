package com.quintly.api.factory;

import com.quintly.api.Client;
import org.apache.http.impl.client.HttpClientBuilder;

public class ClientFactory {
    public static Client createClient() {
        return new Client(HttpClientBuilder.create().build());
    }
}
