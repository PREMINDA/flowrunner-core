package com.flowrunner.core.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowrunner.core.model.FlowDef;

import java.io.File;
import java.io.IOException;

public class JsonLoader {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static FlowDef loadProcess(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), FlowDef.class);
    }

    public static FlowDef loadProcess(File file) throws IOException {
        return mapper.readValue(file, FlowDef.class);
    }

    public static FlowDef loadProcessFromResource(String resourceName) throws IOException {
        java.io.InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new IOException("Resource not found: " + resourceName);
        }
        return mapper.readValue(is, FlowDef.class);
    }
}
