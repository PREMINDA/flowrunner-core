package com.flowrunner.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallProcessNode extends BaseNode {
    private String name;
    private Map<String, Object> blackboard; // generic map for blackboard
    private List<BaseNode> nodes;
    private List<Edge> edges;
}
