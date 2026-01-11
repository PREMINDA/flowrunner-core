package com.flowrunner.core.action;

import com.flowrunner.core.model.NodeData;

public interface FlowAction {
    void execute(NodeData data);
}
