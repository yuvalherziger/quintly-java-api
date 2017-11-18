package com.quintly.api.endpoint;

import junit.framework.TestCase;
import org.junit.Test;

public class SearchProfilesTest extends TestCase {

    @Test
    public void testGetPathAsString() {
        SearchProfiles searchProfiles = new SearchProfiles("coca-cola");
        String expectedPath = "search-profiles?q=coca-cola";
        assertEquals(expectedPath, searchProfiles.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithNetwork() {
        SearchProfiles searchProfiles = new SearchProfiles("coca-cola", "instagram");
        String expectedPath = "search-profiles?q=coca-cola&network=instagram";
        assertEquals(expectedPath, searchProfiles.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithMissingParameter() {
        try {
            SearchProfiles searchProfiles = new SearchProfiles(null);
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
            assertEquals("The 'query' parameter is mandatory", e.getMessage());
        }
    }
}
