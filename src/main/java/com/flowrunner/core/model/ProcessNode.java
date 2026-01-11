package com.flowrunner.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessNode extends BaseNode {
    private String name;
    private Map<String, Object> blackboard; // generic map for blackboard
    private Map<String, BaseNode> nodes = new HashMap<>();
    private Map<String, Edge> edges = new HashMap<>();

    @JsonSetter("nodes")
    public void setNodesList(List<BaseNode> nodeList) {
        this.nodes = new HashMap<>();
        if (nodeList != null) {
            for (BaseNode node : nodeList) {
                this.nodes.put(node.getId(), node);
            }
        }
    }

    @JsonSetter("edges")
    public void setEdgesList(List<Edge> edgeList) {
        this.edges = new HashMap<>();
        if (edgeList != null) {
            for (Edge edge : edgeList) {
                this.edges.put(edge.getId(), edge);
            }
        }
    }
}
