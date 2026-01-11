package com.example;

import com.flowrunner.core.action.FlowAction;
import com.flowrunner.core.model.NodeData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("com.example.DemoActionsecond")
@Scope("prototype")
public class DemoActionsecond implements FlowAction {
    @Override
    public void execute(NodeData data) {
        System.out.println("Executing DemoActionsecond with data: " + (data != null ? data.getLabel() : "null"));
    }
}
