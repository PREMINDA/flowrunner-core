package com.flowrunner.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProcessNode.class, name = "processNode"),
        @JsonSubTypes.Type(value = CalltoProcessNode.class, name = "calltoProcessNode"),
        @JsonSubTypes.Type(value = StartNode.class, name = "startNode"),
        @JsonSubTypes.Type(value = ActionNode.class, name = "actionNode")
})
public abstract class BaseNode {
    private String id;
    private String type;

    // Position might be in top-level JSON or inside 'position' object in array
    // We'll ignore the complicated UI position objects as per request,
    // but if needed we can add a helper class.
    // For now, focusing on structural data.

    private NodeData data;
}
