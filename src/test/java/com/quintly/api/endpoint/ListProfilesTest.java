package com.quintly.api.endpoint;

import junit.framework.TestCase;
import org.junit.Test;

public class ListProfilesTest extends TestCase {

    @Test
    public void testGetPathAsString() {
        ListProfiles listProfiles = new ListProfiles();
        String expectedPath = "list-profiles";
        assertEquals(expectedPath, listProfiles.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithGroup() {
        ListProfiles listProfiles = new ListProfiles(110);
        String expectedPath = "list-profiles?groupId=110";
        assertEquals(expectedPath, listProfiles.getPathAsString());
    }
}
