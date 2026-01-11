package com.flowrunner.core.engine;

import com.flowrunner.core.action.FlowAction;
import com.flowrunner.core.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlowEngine {

    private final ApplicationContext context;

    public FlowEngine(ApplicationContext context) {
        this.context = context;
    }

    public void run(CallProcessNode process) {
        System.out.println("Starting process execution: " + process.getName());

        if (process.getNodes() == null || process.getEdges() == null) {
            System.out.println("Empty process definition.");
            return;
        }

        // Find Start Node
        BaseNode currentNode = process.getNodes().values().stream()
                .filter(n -> n instanceof StartNode)
                .findFirst()
                .orElse(null);

        if (currentNode == null) {
            System.err.println("No StartNode found in process " + process.getId());
            return;
        }

        // Execution Loop
        while (currentNode != null) {
            System.out.println(" -> Visiting: " + currentNode.getId() + " (" + currentNode.getType() + ")");

            // Execute Action if applicable
            if (currentNode instanceof ActionNode) {
                executeAction((ActionNode) currentNode);
            }

            // Find next node via Edge
            // Since edges are mapped by EdgeID, we must iterate to find the one matching
            // current node as source
            String nextNodeId = null;
            for (Edge edge : process.getEdges().values()) {
                if (edge.getSource().equals(currentNode.getId())) {
                    nextNodeId = edge.getTarget();
                    break;
                }
            }

            if (nextNodeId != null) {
                currentNode = process.getNodes().get(nextNodeId);
            } else {
                System.out.println("End of flow (no outgoing edge from " + currentNode.getId() + ")");
                currentNode = null;
            }
        }
        System.out.println("Process execution finished.");
    }

    private void executeAction(ActionNode node) {
        String className = node.getJavaClassName();
        if (className == null || className.isEmpty()) {
            System.out.println("   [Skipping] No javaClassName defined for action " + node.getId());
            return;
        }

        try {
            Class<?> actionClass = Class.forName(className);
            if (FlowAction.class.isAssignableFrom(actionClass)) {
                FlowAction action = (FlowAction) context.getBean(actionClass);
                action.execute(node.getData());
            } else {
                System.err.println("   [Error] " + className + " must implement FlowAction");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("   [Error] Class not found: " + className);
        } catch (Exception e) {
            System.err.println("   [Error] executing action: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
