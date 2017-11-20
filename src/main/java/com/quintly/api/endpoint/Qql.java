package com.quintly.api.endpoint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.quintly.api.Interval;
import com.quintly.api.SortDirection;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * This class represents the QQL endpoint.
 * See https://api.quintly.com for more information
 */
public class Qql implements Endpoint {

    private final String edge = "qql";

    // mandatory parameters
    private Date startTime;
    private Date endTime;
    private List<Integer> profileIds;

    // optional parameters
    private Interval interval;
    private String metric;
    private String qqlQuery;
    private TimeZone timezone;
    private String sortBy;
    private SortDirection sortDir;

    public Qql(Date startTime, Date endTime, List<Integer> profileIds, String qqlQueryOrMetric) {
        this.validate(startTime, endTime, profileIds, qqlQueryOrMetric);
        this.startTime = startTime;
        this.endTime = endTime;
        this.profileIds = profileIds;

        // check if the requested metric is a raw QQL query or a predefined metric
        if (Pattern.compile("select.*from").matcher(qqlQueryOrMetric.toLowerCase()).find()) {
            this.qqlQuery = qqlQueryOrMetric;
        } else {
            this.metric = qqlQueryOrMetric;
        }
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDir(SortDirection sortDir) {
        this.sortDir = sortDir;
    }

    private void validate(Date startTime, Date endTime, List<Integer> profileIds, String qqlQueryOrMetric)
    {
        Validate.notNull(startTime, "The 'startTime' parameter is mandatory");
        Validate.notNull(endTime, "The 'endTime' parameter is mandatory");
        Validate.notEmpty(profileIds, "The 'profileIds' parameter is mandatory");
        Validate.notEmpty(qqlQueryOrMetric, "The 'qqlQueryOrMetric' parameter is mandatory");
    }

    public String getPathAsString() throws UnsupportedEncodingException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String path = String.format(
                "%s?startTime=%s&endTime=%s&profileIds=%s",
                this.edge,
                dateFormatter.format(this.startTime),
                dateFormatter.format(this.endTime),
                StringUtils.join(this.profileIds, ',')
        );

        if (this.qqlQuery != null) {
            path += "&qqlQuery=" + URLEncoder.encode(this.qqlQuery, "UTF-8");
        } else {
            path += "&metric=" + this.metric;
        }

        if (this.interval != null) {
            path += "&interval=" + this.interval.name().toLowerCase();
        }

        if (this.timezone != null) {
            path += "&timezone=" + this.timezone.getID();
        }

        if (this.sortBy != null) {
            path += "&sortBy=" + this.sortBy;
        }

        if (this.sortDir != null) {
            path += "&sortDir=" + this.sortDir.name();
        }

        return path;
    }
}
