package com.quintly.api.endpoint;

import junit.framework.TestCase;
import org.junit.Test;
import java.io.UnsupportedEncodingException;

public class AddProfileTest extends TestCase {

    @Test
    public void testGetPathAsString() throws UnsupportedEncodingException {
        AddProfile addProfile = new AddProfile(1, "https://www.facebook.com/quintly");
        String expectedPath = "add-profile?spaceId=1&q=https%3A%2F%2Fwww.facebook.com%2Fquintly";
        assertEquals(expectedPath, addProfile.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithMissingParameter() {
        try {
            AddProfile addProfile = new AddProfile(null, "https://www.facebook.com/quintly");
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
            assertEquals("The 'spaceId' parameter is mandatory", e.getMessage());
        }
    }
}
