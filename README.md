# quintly-java-api
[![codecov](https://codecov.io/gh/yuvalherziger/quintly-java-api/branch/master/graph/badge.svg?token=Tnad7lN8Cb)](https://codecov.io/gh/yuvalherziger/quintly-java-api)
[![Codeship Status for yuvalherziger/quintly-java-api](https://app.codeship.com/projects/5d4f67e0-b284-0135-f1e6-124eec6b3035/status?branch=master)](https://app.codeship.com/projects/258096)


A light-weight Java interface for quintly's Social Media Analytics API. 
For more information about the API, see [quintly's API documentation](https://api.quintly.com/).

## TODO's

- Exceptions
- More tests
- Add examples folder
- Add CI integration, add a build status badge and a code coverage badge

## Installation
To build the project from gradle wrapper, run:
```bash
~ ./gradlew build
```
Otherwise, from a [global gradle installation](https://gradle.org/install/):
```bash
~ gradle build
```

## Examples:

### List-Profiles endpoint:
```java
import com.quintly.api.*;

class MyApp {
    
    public static void main(String[] args) {
        Client client = ClientFactory.createClient();
        
        // create credentials with your client ID and client secret:
        Credentials credentials = new Credentials(1, "client-secret");
        
        // create a new endpoint:
        ListProfiles endpoint = new ListProfiles();
        Response response = client.executeGet(credentials, endpoint);
        
        // Verify 200 status code:
        System.out.println("Response status code: " + response.getStatusCode());
        
        // Dig in:
        ArrayList<Profile> profiles = response.getProfilesCollection().getData();
        System.out.println(profiles.get(0).getName());
    }
}
```

### Custom Response Model:
The thing that makes the QQL endpoint of quintly's API different from others, is that the response's structure is a direct derivative of the metric or QQL query that's requested, thus it's not predictable.
The following QQL query is a legitimate query, where the `profileId`, `time` and `fans` data points are selected from the `Facebook` data source:

```sql
SELECT profileId, time, fans FROM facebook
```

The library allows you to pass down your custom response model, to create a clear interface between the raw response and your application's models.
Let's implement the following two classes, the first one for a single data node and the second one for the upper level response:

```java
package com.domain.myapp;

public class MyCustomNode {

    private int profileId;

    private String time;

    private int fans;

    public int getProfileId() {
        return profileId;
    }

    public String getTime() {
        return time;
    }

    public int getFans() {
        return fans;
    }
}
```
 
 ```java
package com.domain.myapp;
 
import java.util.ArrayList;
 
public class MyCustomResponseModel {
 
    private boolean success;
 
    private ArrayList<MyCustomNode> data;
 
    public boolean isSuccess() {
        return success;
    }
 
    public ArrayList<MyCustomNode> getData() {
        return data;
    }
 }
```

Now, all that's left to do is to use the library's custom parser and pass down your custom model, like so:

```java
import com.quintly.api.*;

class MyApp {
    
    public static void main(String[] args) {
        Client client = ClientFactory.createClient();
        
        // create credentials with your client ID and client secret:
        Credentials credentials = new Credentials(1, "client-secret");
        // create a list of desired profile ID's from the quintly API:
        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(100);
        
        // create a new endpoint, of type Qql:
        Qql endpoint = new Qql(
            new Date(1506816000000L),
            new Date(),
            profileIds,
            "SELECT profileId, time, fans FROM facebook"
        );
        Response response = client.executeGet(credentials, endpoint);
        
        // Verify 200 status code:
        System.out.println("Response status code: " + response.getStatusCode());
        
        // Dig in:
        MyCustomResponseModel myCustomResponseModel = (MyCustomResponseModel) response.getData(
            new ModelParser<>(MyCustomResponseModel.class)
        );
        MyCustomNode node1 = myCustomResponseModel.getData().get(0);
        System.out.println("Node #1, profileId:" + node1.getProfileId());
        System.out.println("Node #1, time:" + node1.getTime());
        System.out.println("Node #1, fans:" + node1.getFans());
    }
}
```
