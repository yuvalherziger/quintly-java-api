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
        Qql qql = this.getQqlEndpoint(profileIds, null);
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92,105,132&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithPredefinedMetric() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = this.getQqlEndpoint(profileIds, "facebookFansTotal");
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&metric=facebookFansTotal";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithInterval() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = this.getQqlEndpoint(profileIds, null);
        qql.setInterval(Interval.DAILY);
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithIntervalAndTimezone() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = this.getQqlEndpoint(profileIds, null);
        qql.setInterval(Interval.DAILY);
        qql.setTimezone(TimeZone.getTimeZone("America/Denver"));
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily&timezone=America/Denver";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithIntervalAndTimezoneAndSortBy() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = this.getQqlEndpoint(profileIds, null);
        qql.setInterval(Interval.DAILY);
        qql.setTimezone(TimeZone.getTimeZone("America/Denver"));
        qql.setSortBy("time");
        String expectedPath = "qql?startTime=2017-10-01&endTime=2017-11-18&profileIds=92&qqlQuery=SELECT+profileId%2C+time%2C+fans+FROM+facebook&interval=daily&timezone=America/Denver&sortBy=time";
        assertEquals(expectedPath, qql.getPathAsString());
    }

    @Test
    public void testGetPathAsStringWithIntervalAndTimezoneAndSortByAndSortDir() throws UnsupportedEncodingException {
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Qql qql = this.getQqlEndpoint(profileIds, null);
        qql.setInterval(Interval.DAILY);
        qql.setTimezone(TimeZone.getTimeZone("America/Denver"));
        qql.setSortBy("time");
        qql.setSortDir(SortDirection.DESC);
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

    private Qql getQqlEndpoint(List<Integer> profileIds, String qqlQueryOrMetric) {
        qqlQueryOrMetric = qqlQueryOrMetric != null ? qqlQueryOrMetric : "SELECT profileId, time, fans FROM facebook";
        return new Qql(
                new Date(1506816000000L),
                new Date(1510992552000L),
                profileIds,
                qqlQueryOrMetric
        );
    }
}
