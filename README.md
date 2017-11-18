# quintly-java-api
quintly's Social Media Analytics API interface for Java

## TODOs

- Response models
- Exceptions
- More tests

## Installation
To build the project from gradle wrapper, run:
```bash
~ ./gradlew build
```
Otherwise, from a global gradle installation:
```bash
~ gradle build
```

## Examples:

### List-Profiles endpoint:
```java
import com.quintly.api.*;
...
...
// fetch a client:
Client client = ClientFactory.createClient();

// create crdentials with your client ID and client secret:
Credentials credentials = new Credentials(1, "client-secret");

// create a new endpoint:
ListProfiles endpoint = new ListProfiles();
Response response = client.executeGet(credentials, endpoint, null);

// Verify 200 status code:
System.out.println("Response status code: " + response.getStatusCode());

// Dig in:
Data data = response.toJson();
```
