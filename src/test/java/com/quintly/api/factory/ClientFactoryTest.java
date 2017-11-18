package com.quintly.api.factory;

import com.quintly.api.Client;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.UnsupportedEncodingException;

public class ClientFactoryTest extends TestCase {

    @Test
    public void testGetPathAsString() throws UnsupportedEncodingException {
        Client client = ClientFactory.createClient();
        assertThat(client, instanceOf(Client.class));
    }
}
