package com.flowrunner.core.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowrunner.core.model.ProcessNode;

import java.io.File;
import java.io.IOException;

public class JsonLoader {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static ProcessNode loadProcess(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), ProcessNode.class);
    }

    public static ProcessNode loadProcess(File file) throws IOException {
        return mapper.readValue(file, ProcessNode.class);
    }

    public static ProcessNode loadProcessFromResource(String resourceName) throws IOException {
        java.io.InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new IOException("Resource not found: " + resourceName);
        }
        return mapper.readValue(is, ProcessNode.class);
    }
}
