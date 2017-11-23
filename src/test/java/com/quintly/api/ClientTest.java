package com.quintly.api;

import com.quintly.api.endpoint.Qql;
import com.quintly.api.enitity.MyCustomNode;
import com.quintly.api.entity.Profile;
import com.quintly.api.enitity.MyCustomResponseModel;
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

    @Test
    public void testExecuteGetWithEmptyResponse()  throws IOException {
        String responseString = "{\"success\":true,\"data\":[]}";

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

    @Test
    public void testExecuteGetWithListProfilesResponse()  throws IOException, IncompatibleGetterException {
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
                new Qql(
                        new Date(1506816000000L),
                        new Date(),
                        profileIds,
                        "SELECT profileId, time, fans FROM facebook"
                ),
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
        assertEquals(0, profile1.getGroups().size());
    }

    @Test
    public void testExecuteGetWithIncompatibleGetterException()  throws IOException {
        String responseString = "{\"success\":true,\"data\":[]}";

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

    @Test
    public void testExecuteGetWithCustomModel()  throws IOException {
        String responseString = "{\n" +
                "    \"success\": true,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"profileId\": 100,\n" +
                "            \"time\": \"2017-10-01 00:00:00\",\n" +
                "            \"fans\": 200311\n" +
                "        },\n" +
                "        {\n" +
                "            \"profileId\": 100,\n" +
                "            \"time\": \"2017-10-02 00:00:00\",\n" +
                "            \"fans\": 200349\n" +
                "        }\n" +
                "\t  ]\n" +
                "}";

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
}
