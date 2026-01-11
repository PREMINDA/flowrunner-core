package com.example;

import com.flowrunner.core.action.FlowAction;
import com.flowrunner.core.model.NodeData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("com.example.DemoActionlast")
@Scope("prototype")
public class DemoActionlast implements FlowAction {
    @Override
    public void execute(NodeData data) {
        System.out.println("Executing DemoActionlast with data: " + (data != null ? data.getLabel() : "null"));
    }
}
