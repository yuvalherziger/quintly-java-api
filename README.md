# quintly-java-api
quintly's Social Media Analytics API interface for Java

## TODO's

- Response models should be generic
- Response entities should be injectable (e.g. custom user entity objects to be injected)
- Exceptions
- More tests
- Add examples folder

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
// ...
// ...
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
ArrayList<Profile> profiles = response.getProfilesCollection().getData();
System.out.println(profiles.get(0).getName());
```
