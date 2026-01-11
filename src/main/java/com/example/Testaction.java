package com.example;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.flowrunner.core.action.FlowAction;
import com.flowrunner.core.model.NodeData;


@Component("com.example.Testaction")
@Scope("prototype")
public class Testaction implements FlowAction{

    @Override
     public void execute(NodeData data) {
        System.out.println("Executing Testaction with data: " + (data != null ? data.getLabel() : "null"));
    }
}



