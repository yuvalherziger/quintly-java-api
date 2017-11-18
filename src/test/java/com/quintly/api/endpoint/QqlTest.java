package com.quintly.api.endpoint;

import com.quintly.api.Interval;
import com.quintly.api.SortDirection;
import junit.framework.TestCase;
import org.junit.Test;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class QqlTest extends TestCase {

    @Test
    public void testGetPathAsString() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        profileIds.add(105);
        profileIds.add(132);
        Qql qql = new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                "SELECT profileId, time, fans FROM facebook"
        );
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92,105,132&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithPredefinedMetric() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                "facebookFansTotal"
        );
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&metric=facebookFansTotal";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithInterval() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                "SELECT profileId, time, fans FROM facebook",
                Interval.DAILY
        );
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithIntervalAndTimezone() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                "SELECT profileId, time, fans FROM facebook",
                Interval.DAILY,
                TimeZone.getTimeZone("America/Denver")
        );
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily&timezone=America/Denver";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithIntervalAndTimezoneAndSortBy() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                "SELECT profileId, time, fans FROM facebook",
                Interval.DAILY,
                TimeZone.getTimeZone("America/Denver"),
                "time"
        );
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily&timezone=America/Denver&sortBy=time";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithIntervalAndTimezoneAndSortByAndSortDir() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                "SELECT profileId, time, fans FROM facebook",
                Interval.DAILY,
                TimeZone.getTimeZone("America/Denver"),
                "time",
                SortDirection.DESC
        );
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily&timezone=America/Denver&sortBy=time&sortDir=DESC";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithMissingParameter() {
        try {
            Qql qql = new Qql(null, null, null, null);
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
            assertEquals("The 'startTime' parameter is mandatory", e.getMessage());
        }
    }
}
