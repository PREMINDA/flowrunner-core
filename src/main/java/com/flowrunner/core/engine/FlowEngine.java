
package com.flowrunner.core.engine;

import com.flowrunner.core.action.FlowAction;
import com.flowrunner.core.model.*;
import com.flowrunner.core.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class FlowEngine {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ProcessService processService;

    // Run a process (ProcessNode is the container)
    public void run(ProcessNode process) {
        if (process == null)
            return;
        System.out.println("Starting process execution: " + process.getName());

        // 1. Find StartNode
        StartNode startNode = findStartNode(process);
        if (startNode == null) {
            System.err.println("No StartNode found!");
            return;
        }

        // 2. Traverse
        BaseNode currentNode = startNode;
        while (currentNode != null) {
            System.out.println(
                    " -> Visiting: " + currentNode.getId() + " (" + currentNode.getClass().getSimpleName() + ")");

            if (currentNode instanceof ActionNode) {
                executeAction((ActionNode) currentNode);
            } else if (currentNode instanceof CallProcessNode) {
                executeSubProcess((CallProcessNode) currentNode);
            }

            currentNode = findNextNode(currentNode, process);
        }
        System.out.println("Process execution finished.");
    }

    private void executeSubProcess(CallProcessNode callNode) {
        String subProcessId = callNode.getCallToProcess();
        if (subProcessId != null && !subProcessId.isEmpty()) {
            System.out.println("   [Sub-Process] Calling: " + subProcessId);
            try {
                ProcessNode subProcess = processService.getProcess(subProcessId);
                // Recursively run the sub-process using the same engine instance (stateless
                // traversal)
                // or we could spawn a new one. Reuse is fine here.
                this.run(subProcess);
                System.out.println("   [Sub-Process] Returned from: " + subProcessId);
            } catch (IOException e) {
                System.err.println("   [Error] Failed to load sub-process: " + e.getMessage());
            }
        }
    }

    private void executeAction(ActionNode node) {
        String className = node.getJavaClassName();
        if (className != null && !className.isEmpty()) {
            try {
                Class<?> clazz = Class.forName(className);
                if (FlowAction.class.isAssignableFrom(clazz)) {
                    FlowAction action = (FlowAction) context.getBean(clazz);

                    // NodeData was refactored to just label. FlowAction expects NodeData.
                    // We need to pass NodeData back or update FlowAction.
                    // Checking FlowAction interface: void execute(NodeData data);
                    // So we should pass node.getData().
                    action.execute(node.getData());
                }
            } catch (ClassNotFoundException e) {
                System.err.println("   [Error] Action class not found: " + className);
            } catch (Exception e) {
                System.err.println("   [Error] Executing action: " + e.getMessage());
            }
        }
    }

    private StartNode findStartNode(ProcessNode process) {
        if (process.getNodes() == null)
            return null;
        for (BaseNode node : process.getNodes().values()) {
            if (node instanceof StartNode) {
                return (StartNode) node;
            }
        }
        return null;
    }

    private BaseNode findNextNode(BaseNode current, ProcessNode process) {
        if (process.getEdges() == null)
            return null;

        // Find edge where source == current.getId()
        for (Edge edge : process.getEdges().values()) {
            if (edge.getSource().equals(current.getId())) {
                String targetId = edge.getTarget();
                return process.getNodes().get(targetId);
            }
        }
        return null; // End of flow
    }
}
