package com.quintly.api;

import com.quintly.api.endpoint.ListProfiles;
import com.quintly.api.endpoint.Qql;
import com.quintly.api.enitity.MyCustomNode;
import com.quintly.api.entity.Profile;
import com.quintly.api.enitity.MyCustomResponseModel;
import com.quintly.api.exception.BadResponseException;
import com.quintly.api.exception.IncompatibleGetterException;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest extends BaseTestCase {

    public void testExecuteGetWithEmptyResponse()  throws IOException, BadResponseException {
        String responseString = this.loadResourceAsString("/src/test/fixtures/successWithEmptyResponse.json");

        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        HttpGet httpGetMock = mock(HttpGet.class);
        CloseableHttpResponse httpResponseMock = mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = mock(StatusLine.class);

        BasicHttpEntity httpEntity = new BasicHttpEntity();
        InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8.name()));
        httpEntity.setContent(stream);

        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntity);
        when(httpClientMock.execute(httpGetMock)).thenReturn(httpResponseMock);

        Client client = new Client(httpClientMock);
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Response response = client.executeGet(
                new Credentials(2, "secretSanta"),
                new Qql(
                        new Date(1506816000000L),
                        new Date(),
                        profileIds,
                        "SELECT profileId, time, fans FROM facebook"
                ),
                httpGetMock
        );

        assertTrue(response.getData().isSuccess());
        assertEquals(200, response.getStatusCode());
        assertEquals(0, response.getData().getData().size());
    }

    public void testExecuteGetWithListProfilesResponse()  throws IOException, IncompatibleGetterException, BadResponseException {
        String responseString = this.loadResourceAsString("/src/test/fixtures/listProfilesResponse.json");

        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);


        HttpGet httpGetMock = mock(HttpGet.class);
        CloseableHttpResponse httpResponseMock = mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = mock(StatusLine.class);

        BasicHttpEntity httpEntity = new BasicHttpEntity();
        InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8.name()));
        httpEntity.setContent(stream);

        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntity);
        when(httpClientMock.execute(httpGetMock)).thenReturn(httpResponseMock);

        Client client = new Client(httpClientMock);
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Response response = client.executeGet(
                new Credentials(2, "secretSanta"),
                new ListProfiles(),
                httpGetMock
        );

        assertTrue(response.getProfilesCollection().isSuccess());
        assertEquals(200, response.getStatusCode());
        assertEquals(2, response.getProfilesCollection().getData().size());

        ArrayList<Profile> data = response.getProfilesCollection().getData();

        Profile profile1 = data.get(0);
        assertEquals(16, profile1.getId());
        assertEquals("Microsoft", profile1.getName());
        assertNull(profile1.getAppendix());
        assertEquals("facebook", profile1.getPlatformType());
        assertEquals("20528438720", profile1.getPlatformId());
        assertEquals("Microsoft", profile1.getPlatformUsername());
        assertEquals("http://www.facebook.com/Microsoft", profile1.getUrl());
        assertNull(profile1.getColor());
        assertEquals("2010-06-10 14:00:00", profile1.getAudienceDataSinceTime());
        assertEquals("2010-05-26 17:00:00", profile1.getInteractionDataSinceTime());
        assertEquals(1, profile1.getSpaces().size());
        assertEquals(3251, profile1.getSpaces().get(0).getId());
        assertEquals("Analysts Space", profile1.getSpaces().get(0).getName());
        assertEquals(1, profile1.getGroups().size());
        assertEquals(1239, profile1.getGroups().get(0).getId());
        assertEquals("Tech Brands", profile1.getGroups().get(0).getName());
        assertEquals(3251, profile1.getGroups().get(0).getSpaceId());
    }

    public void testExecuteGetWithIncompatibleGetterException()  throws IOException {
        String responseString = this.loadResourceAsString("/src/test/fixtures/successWithEmptyResponse.json");

        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);


        HttpGet httpGetMock = mock(HttpGet.class);
        CloseableHttpResponse httpResponseMock = mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = mock(StatusLine.class);

        BasicHttpEntity httpEntity = new BasicHttpEntity();
        InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8.name()));
        httpEntity.setContent(stream);

        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntity);
        when(httpClientMock.execute(httpGetMock)).thenReturn(httpResponseMock);

        Client client = new Client(httpClientMock);
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Response response = client.executeGet(
                new Credentials(2, "secretSanta"),
                new Qql(
                        new Date(1506816000000L),
                        new Date(),
                        profileIds,
                        "SELECT profileId, time, fans FROM facebook"
                ),
                httpGetMock
        );


        try {
            response.getProfilesCollection();
        } catch (Exception e) {
            assertEquals(IncompatibleGetterException.class, e.getClass());
            assertEquals("Cannot fetch profiles for this type of endpoint request.", e.getMessage());
        }
    }

    public void testExecuteGetWithCustomModel()  throws IOException {
        String responseString = this.loadResourceAsString("/src/test/fixtures/customQqlResponse.json");

        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);


        HttpGet httpGetMock = mock(HttpGet.class);
        CloseableHttpResponse httpResponseMock = mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = mock(StatusLine.class);

        BasicHttpEntity httpEntity = new BasicHttpEntity();
        InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8.name()));
        httpEntity.setContent(stream);

        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntity);
        when(httpClientMock.execute(httpGetMock)).thenReturn(httpResponseMock);

        Client client = new Client(httpClientMock);
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Response response = client.executeGet(
                new Credentials(2, "secretSanta"),
                new Qql(
                        new Date(1506816000000L),
                        new Date(),
                        profileIds,
                        "SELECT profileId, time, fans FROM facebook"
                ),
                httpGetMock
        );

        MyCustomResponseModel myCustomResponseModel = (MyCustomResponseModel) response.getData(
                new ModelParser<>(MyCustomResponseModel.class)
        );

        assertEquals(200, response.getStatusCode());
        assertTrue(myCustomResponseModel.isSuccess());
        assertEquals(2, myCustomResponseModel.getData().size());

        MyCustomNode node1 = myCustomResponseModel.getData().get(0);
        assertEquals("2017-10-01 00:00:00", node1.getTime());
        assertEquals(100, node1.getProfileId());
        assertEquals(200311, node1.getFans());

        MyCustomNode node2 = myCustomResponseModel.getData().get(1);
        assertEquals("2017-10-02 00:00:00", node2.getTime());
        assertEquals(100, node2.getProfileId());
        assertEquals(200349, node2.getFans());
    }

    public void testExecuteGetWithBadResponseExceptionFromQqlEndpoint()  throws IOException {
        String responseString = this.loadResourceAsString("/src/test/fixtures/malformedQqlQueryResponse.json");

        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);


        HttpGet httpGetMock = mock(HttpGet.class);
        CloseableHttpResponse httpResponseMock = mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = mock(StatusLine.class);

        BasicHttpEntity httpEntity = new BasicHttpEntity();
        InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8.name()));
        httpEntity.setContent(stream);

        when(statusLineMock.getStatusCode()).thenReturn(500);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntity);
        when(httpClientMock.execute(httpGetMock)).thenReturn(httpResponseMock);

        Client client = new Client(httpClientMock);
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(92);
        Response response = client.executeGet(
                new Credentials(2, "secretSanta"),
                new Qql(
                        new Date(1506816000000L),
                        new Date(),
                        profileIds,
                        "SELECT profile, time, fans FROM facebook"
                ),
                httpGetMock
        );

        try {
            response.getData();
        } catch (Exception e) {
            String expectedExceptionMessage = "It appears like the configuration does not comprise of a valid SQL query. Please check your syntax and ensure that the SQL query you have entered is a valid one. You may find hints as to what went wrong within the following message: Unable to prepare statement: 1, no such column: profile";
            assertEquals(BadResponseException.class, e.getClass());
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

    public void testExecuteGetWithBadResponseExceptionFromListProfilesEndpoint()  throws IOException {
        String responseString = this.loadResourceAsString("/src/test/fixtures/internalServerErrorResponse.json");

        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);


        HttpGet httpGetMock = mock(HttpGet.class);
        CloseableHttpResponse httpResponseMock = mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = mock(StatusLine.class);

        BasicHttpEntity httpEntity = new BasicHttpEntity();
        InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8.name()));
        httpEntity.setContent(stream);

        when(statusLineMock.getStatusCode()).thenReturn(500);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntity);
        when(httpClientMock.execute(httpGetMock)).thenReturn(httpResponseMock);

        Client client = new Client(httpClientMock);
        List<Integer> profileIds = new ArrayList<>();
        profileIds.add(92);
        Response response = client.executeGet(
                new Credentials(2, "secretSanta"),
                new Qql(
                        new Date(1506816000000L),
                        new Date(),
                        profileIds,
                        "SELECT profileId, time, fans FROM facebook"
                ),
                httpGetMock
        );
        try {
            response.getData();
        } catch (Exception e) {
            String expectedExceptionMessage = "an internal server error has occurred, please try again later.";
            assertEquals(BadResponseException.class, e.getClass());
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }
}
