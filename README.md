# 📈 quintly-java-api
[![codecov](https://codecov.io/gh/yuvalherziger/quintly-java-api/branch/master/graph/badge.svg?token=Tnad7lN8Cb)](https://codecov.io/gh/yuvalherziger/quintly-java-api)
[![Codeship Status for yuvalherziger/quintly-java-api](https://app.codeship.com/projects/5d4f67e0-b284-0135-f1e6-124eec6b3035/status?branch=master)](https://app.codeship.com/projects/258096)

A light-weight Java library for quintly's Social Media Analytics API. 
For more information about the API, see [quintly's API documentation](https://api.quintly.com/).

## Table of Contents

   * [What's quintly?](#whats-quintly)
   * [Getting started](#getting-started)
      * [Build](#build)
      * [Tests](#tests)
      * [Examples:](#examples)
         * [List-Profiles endpoint:](#list-profiles-endpoint)
         * [Custom Response Model:](#custom-response-model)
   * [What's next?](#whats-next)
   * [Contributing](#contributing)
   * [Copyright and license](#copyright-and-license)

## What's quintly?
[quintly](https://www.quintly.com) is a _Professional Social Media Analytics_ platform that allows you to track, benchmark and optimize your social media performance. One of the services that quintly offers is its API, which acts as the gateway to **all** of the available metrics through quintly's tool.
 
quintly allows you to track profiles on Facebook, Twitter, Instagram, YouTube, LinkedIn, Google+, Pinterest as well as RSS feeds. That includes both publicly available statistics and private-level statistics such as Facebook Insights, Facebook Ads, Instagram Insights, YouTube Analytics and Twitter Analytics.
 
## Getting started

### Build
To build the project from gradle wrapper, run:
```bash
~ ./gradlew build
```
Otherwise, from a [global gradle installation](https://gradle.org/install/):
```bash
~ gradle build
```

### Tests
```bash
~ ./gradlew test # or 'gradle test' with a global gradle installation
```

### Examples:

#### List-Profiles endpoint:
The **List-Profiles** endpoint is perhaps the simplest and most useful endpoint for a quick start with the quintly API.
The response of this endpoint is a list of all the social media profiles under your quintly account. The endpoint can also be narrowed down to a specific group ID within the account.
  
```java
import com.quintly.api.endpoint.ListProfiles;
import com.quintly.api.entity.Profile;
import com.quintly.api.exception.*;
import com.quintly.api.factory.ClientFactory;
import java.io.IOException;
import java.util.ArrayList;
 
class MyApp {
    
    public static void main(String[] args) throws IOException, IncompatibleGetterException, BadResponseException {
        
        ArrayList<Profile> profiles = ClientFactory.createClient()
                .executeGet(
                        new Credentials(1, "client-secret"),
                        new ListProfiles() // or 'new ListProfiles(10)' for a specific group ID
                ).getProfilesCollection().getData();
        
        // Dig in:
        for (Profile profile : profiles) {
            System.out.println(profile.getName());
            System.out.println(profile.getPlatformType());
            // ...
        }
    }
}
```

#### Custom Response Model:
The thing that makes the QQL endpoint of quintly's API different from its other endpoints is that the response's structure is a direct derivative of the metric or QQL query that's requested, thus it's not predictable. For that reason, the library does not implement a specific QQL response format.
The following QQL query is a legitimate query, where the `profileId`, `time` and `fans` data points are selected from the `Facebook` data source (but one could also apply column aliases and change the attribute names in the response):

```sql
SELECT profileId, time, fans FROM facebook
```

The response from the API would be along the following lines:

```json
{
    "success": true,
    "data": [{
            "profileId": 123,
            "time": "2017-11-29 00:00:00",
            "fans": 1947182
        },
        {
            "profileId": 123,
            "time": "2017-11-30 00:00:00",
            "fans": 1947310
        }
    ]
}
```

The library allows you to pass down your custom response model, to create a clear interface between the raw response and your application's models.
Let's implement the following two classes, the first one for a single data node and the second one for the upper level response:

```java
package com.domain.myapp;

public class MyCustomNode {
    private int profileId;
    private String time;
    private int fans;
    public int getProfileId()   { return profileId; }
    public String getTime()     { return time;      }
    public int getFans()        { return fans;      }
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
import com.quintly.api.endpoint.Qql;
import com.quintly.api.exception.BadResponseException;
import com.quintly.api.factory.ClientFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
 
class MyApp {
    
    public static void main(String[] args) throws IOException, BadResponseException {
        // create a list of the profiles we want to fetch from the api in the above metric:
        List<Integer> profileIds = new ArrayList<Integer>(Arrays.asList(123));
        // Submit the request:
        MyCustomResponseModel myCustomResponseModel = (MyCustomResponseModel) ClientFactory.createClient()
                .executeGet(
                        new Credentials(1, "client-secret"),
                        new Qql(
                                new Date(1506816000000L),
                                new Date(),
                                profileIds,
                                "SELECT profileId, time, fans FROM facebook"
                        )
                ).getData(new ModelParser<>(MyCustomResponseModel.class));
        // Dig in:
        for (MyCustomNode dataNode : myCustomResponseModel.getData()) {
            System.out.println(dataNode.getTime());
            System.out.println(dataNode.getFans());
        }
    }
}
```

## What's next?

- `config.yml.dist` and allow loading configurations from a resource
- Add examples package
- More exception types

## Contributing
Feel free to submit pull requests, issues and ideas 💡

## Copyright and license
MIT License

Copyright (c) 2018 Yuval Herziger, quintly GmbH

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
