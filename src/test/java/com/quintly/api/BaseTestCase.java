package com.quintly.api;

import java.io.*;

import junit.framework.TestCase;

abstract public class BaseTestCase extends TestCase {

    protected String loadResourceAsString(String relativePath) throws IOException {
        String filePath = System.getProperty("user.dir") + relativePath;
        System.out.println(filePath);
        InputStream inputStream = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {
            sb.append(line).append("\n");
            line = reader.readLine();
        }

        return sb.toString();
    }

}
