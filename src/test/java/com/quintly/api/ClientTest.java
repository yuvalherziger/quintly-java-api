package com.quintly.api;

import com.quintly.api.endpoint.Qql;
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
import java.util.Map;

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

        assertTrue(response.toJson().isSuccess());
        assertEquals(200, response.getStatusCode());
        assertEquals(0, response.toJson().getData().size());
    }

    @Test
    public void testExecuteGetWithListProfilesResponse()  throws IOException {
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

        assertTrue(response.toJson().isSuccess());
        assertEquals(200, response.getStatusCode());
        assertEquals(2, response.toJson().getData().size());

        ArrayList<Map<String, Object>> data = response.toJson().getData();

        Map<String, Object> element1 = data.get(0);
        assertEquals(16, ((Double) element1.get("id")).intValue());
        assertEquals("Microsoft", element1.get("name"));

        Map<String, Object> element2 = data.get(1);
        assertEquals(18, ((Double) element2.get("id")).intValue());
        assertEquals("Coca-Cola", element2.get("name"));
    }
}
