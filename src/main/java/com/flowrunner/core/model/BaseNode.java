package com.flowrunner.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FlowDef.class, name = "processNode"),
        @JsonSubTypes.Type(value = CallFlow.class, name = "calltoProcessNode"),
        @JsonSubTypes.Type(value = StartNode.class, name = "startNode"),
        @JsonSubTypes.Type(value = ActionNode.class, name = "actionNode")
})
public abstract class BaseNode {
    private String id;
    private String type;
    private NodeData data;
}
