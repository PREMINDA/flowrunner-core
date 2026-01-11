package com.flowrunner.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Edge {
    private String id;
    private String source;
    private String sourceHandle;
    private String target;
    private String targetHandle;
}
