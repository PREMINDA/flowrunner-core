package com.flowrunner;

import com.flowrunner.config.AppConfig;
import com.flowrunner.core.engine.FlowEngine;
import com.flowrunner.core.model.ProcessNode;
import com.flowrunner.core.service.ProcessService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting FlowRunner Application...");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            FlowEngine engine = context.getBean(FlowEngine.class);
            ProcessService processService = context.getBean(ProcessService.class);

            try {
                String jsonPath = "process/start.flowchartprocess.json";
                System.out.println("Loading process: " + jsonPath);

                ProcessNode process = processService.getProcess(jsonPath);
                engine.run(process);

            } catch (IOException e) {
                System.err.println("Failed to execute process: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}