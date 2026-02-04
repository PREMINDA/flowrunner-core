package com.flowrunner;

import com.flowrunner.config.AppConfig;
import com.flowrunner.core.engine.FlowEngine;
import com.flowrunner.core.model.FlowDef;
import com.flowrunner.core.service.FlowService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting FlowRunner Application...");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            FlowEngine engine = context.getBean(FlowEngine.class);
            FlowService processService = context.getBean(FlowService.class);

            try {
                String jsonPath = context.getEnvironment().getProperty("flowrunner.process.entry-path");
                if (jsonPath == null) {
                    System.err.println("Property flowrunner.process.entry-path not found!");
                    return;
                }
                System.out.println("Loading process: " + jsonPath);

                FlowDef process = processService.getProcess(jsonPath);
                engine.run(process);

                System.out.println("---------------------------------------------------------------------------------------------");

                engine.run(process);

            } catch (IOException e) {
                System.err.println("Failed to execute process: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}