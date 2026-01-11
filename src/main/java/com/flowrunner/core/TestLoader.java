package com.flowrunner.core;

import com.flowrunner.config.AppConfig;
import com.flowrunner.core.action.FlowAction;
import com.flowrunner.core.model.ActionNode;
import com.flowrunner.core.model.BaseNode;
import com.flowrunner.core.model.CallProcessNode;
import com.flowrunner.core.util.JsonLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class TestLoader {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        try {
            String jsonPath = "asd.json";
            CallProcessNode process = JsonLoader.loadProcess(jsonPath);

            System.out.println("Successfully loaded process: " + process.getId());

            if (process.getNodes() != null) {
                for (BaseNode node : process.getNodes()) {
                    System.out.println(" - Visiting node: " + node.getId() + " (" + node.getType() + ")");

                    if (node instanceof ActionNode) {
                        String className = ((ActionNode) node).getJavaClassName();
                        if (className != null) {
                            System.out.println("   Resolution: " + className);
                            try {
                                Class<?> actionClass = Class.forName(className);
                                if (FlowAction.class.isAssignableFrom(actionClass)) {
                                    FlowAction action = (FlowAction) context.getBean(actionClass);
                                    action.execute(node.getData());
                                } else {
                                    System.err
                                            .println("   Error: Class " + className + " does not implement FlowAction");
                                }
                            } catch (ClassNotFoundException e) {
                                System.err.println("   Error: Class not found: " + className);
                            } catch (Exception e) {
                                System.err.println("   Error executing bean: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to load JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
