package com.quintly.api.endpoint;

import junit.framework.TestCase;
import org.junit.Test;

public class RemoveProfileTest extends TestCase {

    @Test
    public void testGetPathAsString() {
        RemoveProfile removeProfile = new RemoveProfile(10, 20);
        String expectedPath = "remove-profile?spaceId=10&profileId=20";
        assertEquals(expectedPath, removeProfile.getPathAsString());
    }


    @Test
    public void testGetPathAsStringWithMissingParameter() {
        try {
            RemoveProfile removeProfile = new RemoveProfile(null, null);
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
            assertEquals("The 'spaceId' parameter is mandatory", e.getMessage());
        }
    }
}
